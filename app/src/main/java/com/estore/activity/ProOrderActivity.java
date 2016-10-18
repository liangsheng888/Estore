package com.estore.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.activity.myappliction.MyApplication;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.MapSerializable;
import com.estore.pojo.InsertOrderBean;
import com.estore.pojo.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;
import java.util.Set;

/**
 * 订单
 */

public class ProOrderActivity extends AppCompatActivity {
    private TextView order_count_total_money;//订单总价格
    private TextView order_pnum;//订单商品数数量
    private TextView order_dizhi_phonenum;//手机号
    private TextView order_dizhi_detaildizhi;//详细地址
    private TextView order_prod_yunfei_money;//运费
    private TextView order_prod_fapiao_right;//发票
    private ListView order_scroll_listview;//订单中的商品列表
    private TextView order_total_money;//实际价格
    private Button order_goumai;//立即购买
    private Map<Product.Products, Integer> mapOrderInfo;
    private int number=0;//订单商品数数量
    private Double totalprice=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_order);
        getDataByIntent();
        initView();
        initData();
    }

    private void getDataByIntent() {
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        MapSerializable OrderInfo=(MapSerializable)bundle.getSerializable("OrderInfo");
        Log.i("ProdOrderActivity", "OrderInfo: "+OrderInfo.toString());
        mapOrderInfo=OrderInfo.getPro();
        for (Map.Entry<Product.Products, Integer> mapinfo:
        mapOrderInfo.entrySet()) {
            Product.Products pp=mapinfo.getKey();
            number=mapinfo.getValue();
            totalprice+=pp.estoreprice*number;

        }


    }

    private void initData() {
        order_count_total_money.setText(totalprice+"");
        order_pnum.setText("共"+number+"件商品    合计:");
        order_total_money.setText("￥"+totalprice);
        //立即购买
        order_goumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"orderInsertServlet");
                InsertOrderBean insertOrderBean=new InsertOrderBean();
                insertOrderBean.setUserId(((MyApplication)getApplication()).getUser().getUserId());
                insertOrderBean.setAddressId(1);//默认地址
                insertOrderBean.setDetails(mapOrderInfo);//所有的商品：添加的是key-value的
                insertOrderBean.setTotalPrice(totalprice);

                //对象转换成json数据
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                        .create();
                String orderInfo=gson.toJson(insertOrderBean);
                //get方法传参数
                requestParams.addQueryStringParameter("orderInfo",orderInfo );
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("ProdOrderActivity", "onSuccess: ");
                       /* AlertDialog.Builder builder=new AlertDialog.Builder(ProOrderActivity.this);
                        builder.setTitle("")*/
                        Intent intent=new Intent(ProOrderActivity.this,MyOrderActivity.class);
                        startActivity(intent);//跳转到我的订单

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });



            }
        });


            }




    private void initView() {
        order_count_total_money=(TextView) findViewById(R.id.order_count_total_money);
        order_pnum=(TextView)findViewById(R.id.order_count_heji);
        order_dizhi_phonenum=(TextView)findViewById(R.id.order_dizhi_phonenum);
        order_dizhi_detaildizhi=(TextView)findViewById(R.id.order_dizhi_detaildizhi);
        order_prod_yunfei_money=(TextView)findViewById(R.id.order_prod_yunfei_money);
        order_prod_fapiao_right=(TextView)findViewById(R.id.order_prod_fapiao_right);
        order_scroll_listview=(ListView) findViewById(R.id.order_scroll_listview);
        order_total_money=(TextView)findViewById(R.id.order_total_money);
        order_goumai=(Button)findViewById(R.id.order_goumai);
    }
}
