package com.estore.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import c.b.BP;
import c.b.PListener;
import c.b.QListener;

/**
 * Created by Administrator on 2016/11/3.
 */
public class PayActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private static final int UNRECEIVE =4 ;
    // 此为测试Appid,请将Appid改成你自己的Bmob AppId
    String APPID = "74f36a266148fe0cd8c772bc37b0c329";
    // 此为支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;

    TextView name, price, body;
    EditText order;
    Button go;
    RadioGroup type;
    TextView tv;

    ProgressDialog dialog;
    String name_="";
    String description="";
    Double price_=0.01;
    int product_id=0;
    int pronum=0;
    private int orderId=0;
    int position=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        // 必须先初始化
        BP.init(this, APPID);
        Intent intent=getIntent();
        String name_=   intent.getStringExtra("proName");
        String description_=  intent.getStringExtra("description");
        Double price_= intent.getDoubleExtra("price",0.0);
        product_id=intent.getIntExtra("product_id",0);
        pronum=intent.getIntExtra("pronum",0);
        orderId=intent.getIntExtra("orderId",0);
        position=intent.getIntExtra("position",-1);


        // 初始化BmobPay对象,可以在支付时再初始化

        name=(TextView) findViewById(R.id.name);
        //name.setText(name_)

        price=(TextView)findViewById(R.id.price);
        price.setText("￥"+price_);
        body= (TextView) findViewById(R.id.body);body.setText(description_);
        order = (EditText) findViewById(R.id.order);
        go = (Button) findViewById(R.id.go);
        type = (RadioGroup) findViewById(R.id.type);
        tv = (TextView) findViewById(R.id.tv);

        type.setOnCheckedChangeListener(this);
        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (type.getCheckedRadioButtonId() == R.id.alipay) // 当选择的是支付宝支付时
                    pay(true);
                else if (type.getCheckedRadioButtonId() == R.id.wxpay) // 调用插件用微信支付
                    pay(false);
                else if (type.getCheckedRadioButtonId() == R.id.query) // 选择查询时
                    query();
            }
        });

        View exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                BP.ForceFree();
            }
        });
        exit.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                BP.ForceExit();
                return true;
            }
        });

        int pluginVersion = BP.getPluginVersion();
        if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件, 否则就是支付插件的版本低于官方最新版
            Toast.makeText(
                    PayActivity.this,
                    pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                            : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)", Toast.LENGTH_LONG).show();
            installBmobPayPlugin("bp.db");
        }
    }

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay
     *            支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
        showDialog("正在获取订单...");
        final String name = name_;

        BP.pay(name, description,price_, alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(PayActivity.this, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(PayActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
                tv.append(name + "'s pay status is success\n\n");
                subProduct(product_id,pronum);
                changeState(orderId,UNRECEIVE,"待收货",position);
                hideDialog();
                finish();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                order.setText(orderId);
                tv.append(name + "'s orderid is " + orderId + "\n\n");
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            PayActivity.this,
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_LONG).show();
                    installBmobPayPlugin("bp.db");
                } else {
                    Toast.makeText( PayActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
                tv.append(name + "'s pay status is fail, error code is \n"
                        + code + " ,reason is " + reason + "\n\n");
                hideDialog();
            }
        });
    }

    // 执行订单查询
    void query() {
        showDialog("正在查询订单...");
        final String orderId = getOrder();

        BP.query(orderId, new QListener() {

            @Override
            public void succeed(String status) {
                Toast.makeText(PayActivity.this, "查询成功!该订单状态为 : " + status,
                        Toast.LENGTH_SHORT).show();
                tv.append("pay status of" + orderId + " is " + status + "\n\n");
                hideDialog();

            }

            @Override
            public void fail(int code, String reason) {
                Toast.makeText(PayActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                tv.append("query order fail, error code is " + code
                        + " ,reason is \n" + reason + "\n\n");
                hideDialog();
            }
        });
    }

    // 以下仅为控件操作，可以略过
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.alipay:
                // 以下仅为控件操作，可以略过
                //((TextView) findViewById(R.id.name)).setVisibility(View.VISIBLE);
               // ((TextView) findViewById(R.id.price)).setVisibility(View.VISIBLE);
                //body.setVisibility(View.VISIBLE);
               // order.setVisibility(View.GONE);
                go.setText("支付宝支付");
                break;
            case R.id.wxpay:
                // 以下仅为控件操作，可以略过
               // name.setVisibility(View.VISIBLE);
               // price.setVisibility(View.VISIBLE);
              //  body.setVisibility(View.VISIBLE);
              //  order.setVisibility(View.GONE);
                go.setText("微信支付");
                break;
            case R.id.query:
                // 以下仅为控件操作，可以略过
                name.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
                body.setVisibility(View.GONE);
                order.setVisibility(View.VISIBLE);
                go.setText("订单查询");
                break;

            default:
                break;
        }
    }

    // 支付订单号(查询时必填)
    String getOrder() {
        return this.order.getText().toString();
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void subProduct(int productId, int goodsNum) {
        RequestParams rp=new RequestParams(HttpUrlUtils.HTTP_URL+"subProductServlet");
        rp.addBodyParameter("productId",productId+"");
        rp.addBodyParameter("goodsNum",goodsNum+"");
        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("WaitingPayMoneyFragment", "更新商品数量成功 ");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }
    //更新订单状态，更新界面
    public void changeState(int orderId, final int newStateId, final String newStateName, final int position) {

        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "orderUpdateServlet");
        requestParams.addBodyParameter("orderId", orderId + "");
        requestParams.addBodyParameter("newStateId", newStateId + "");


        //更新订单，更新界面
        x.http().post(requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Log.i("WaitingDeliverFragment", "更新界面onSuccess: " + result);
                //更新界面
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("WaitingDeliverFragment", "更新界面fail: ");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
