package com.estore.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.GoodsOrderState;
import com.estore.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;
import me.iwf.photopicker.widget.MultiPickResultView;

public class EnvaluteActivity extends AppCompatActivity {
    private List<File> imageFileLists = new ArrayList<File>();
    private EditText et_pinglun;//评价内容
    private CheckBox cb_xing1;
    private CheckBox cb_xing2;
    private CheckBox cb_xing3;
    private CheckBox cb_xing4;
    private CheckBox cb_xing5;
    private CheckBox cb_xing6;
    private CheckBox cb_xing7;
    private CheckBox cb_xing8;
    private CheckBox cb_xing9;
    MultiPickResultView ev_recycler_view;
    int evt_honest = 0;//星级
    private SharedPreferences sp;
    private User user=new User();
    private int orderId=0;
    private int productId=0;
    private int position;
    public static final int REMARK = 6;    //已评价

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envalute);
        sp=getSharedPreferences("User",MODE_APPEND);
        ;
        user.setUserId(sp.getInt("userId",-1));
          Intent intent=getIntent();
         orderId=  intent.getIntExtra("orderId",-1);
        productId=  intent.getIntExtra("productId",-1);
        position=intent.getIntExtra("position",-1);

        et_pinglun = (EditText) findViewById(R.id.et_evlaute);
        ev_recycler_view = (MultiPickResultView) findViewById(R.id.ev_recycler_view);
        ev_recycler_view.init(this, MultiPickResultView.ACTION_SELECT, null);
        // TextView tv_evt_photo=(TextView)view.findViewById(R.id.tv_evt_photo);
        TextView fabiao = (TextView) findViewById(R.id.tv_fabiao);
        cb_xing1 = ((CheckBox) findViewById(R.id.xing_gray));
        cb_xing2 = ((CheckBox) findViewById(R.id.xing_gray2));
        cb_xing3 = ((CheckBox) findViewById(R.id.xing_gray3));
        cb_xing4 = ((CheckBox) findViewById(R.id.xing_gray4));
        cb_xing5 = ((CheckBox) findViewById(R.id.xing_gray5));
        cb_xing6 = ((CheckBox) findViewById(R.id.xing_gray6));
        cb_xing7 = ((CheckBox) findViewById(R.id.xing_gray7));
        cb_xing8 = ((CheckBox) findViewById(R.id.xing_gray8));
        fabiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cb_xing1.isChecked()) {
                    evt_honest++;
                }
                if (cb_xing2.isChecked()) {
                    evt_honest++;
                }
                if (cb_xing3.isChecked()) {
                    evt_honest++;
                }
                if (cb_xing4.isChecked()) {
                    evt_honest++;
                }
                if (cb_xing5.isChecked()) {
                    evt_honest++;
                }
                if (cb_xing6.isChecked()) {
                    evt_honest++;
                }
                if (cb_xing7.isChecked()) {
                    evt_honest++;
                }
                if (cb_xing8.isChecked()) {
                    evt_honest++;
                }
                uploadEnvalue(orderId,productId);

            }
        });
    }

    private void uploadEnvalue(Integer goodsOrderId, int product_id) {
        RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "envaluteServlet");
        if(TextUtils.isEmpty(et_pinglun.getText().toString())){
            Toast.makeText(EnvaluteActivity.this, "请输入你要评价的内容", Toast.LENGTH_LONG).show();
            return ;

        }
        Log.i("WaitingEvaluateFragment", "评论 ");
        rp.addBodyParameter("user_id", sp.getInt("userId", 0) + "");
        rp.addBodyParameter("product_id", product_id + "");
        rp.addBodyParameter("order_id", goodsOrderId + "");
        rp.addBodyParameter("evt_msg", et_pinglun.getText().toString());
        rp.addBodyParameter("evt_honest", evt_honest + "");

        //params.setMultipart(true);
        for (int i = 0; i < imageFileLists.size(); i++) {
            rp.addBodyParameter("file" + i, imageFileLists.get(i));
            Log.e("file", imageFileLists.get(i) + "size" + imageFileLists.size());
        }

        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("WaitingEvaluateFragment", result);
                if ("true".equals(result)) {
                    Toast.makeText(EnvaluteActivity.this, "评价成功", Toast.LENGTH_LONG).show();
                    changeState(orderId, REMARK, "已评论", position);
                     finish();
                   /* Intent intent=new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);*/
                }else{
                    Toast.makeText(EnvaluteActivity.this, "评价失败", Toast.LENGTH_LONG).show();
            }


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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ev_recycler_view.onActivityResult(requestCode, resultCode, data);
        PhotoPickUtils.onActivityResult(requestCode, resultCode, data, new PhotoPickUtils.PickHandler() {
            @Override
            public void onPickSuccess(ArrayList<String> photos) {//已经预先做了null或size为0的判断
                for (String str :
                        photos) {
                    imageFileLists.add(new File(str));

                }
            }

            @Override
            public void onPreviewBack(ArrayList<String> photos) {

            }

            @Override
            public void onPickFail(String error) {
                Toast.makeText(EnvaluteActivity.this, error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPickCancle() {
                Toast.makeText(EnvaluteActivity.this, "取消选择", Toast.LENGTH_LONG).show();

            }

        });
    }
}
