package com.estore.activity;
/*
我的关注页面
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class MyCareActivity extends AppCompatActivity {
    private ImageView iv_carereturn;
    private int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_care);
        SharedPreferences sp=getSharedPreferences("User",MODE_APPEND);
        userId=sp.getInt("userId",0);
        getAllCareInfo();
        //返回我的关注
        iv_carereturn = ((ImageView) findViewById(R.id.iv_carereturn));
        iv_carereturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });



    }

    private void getAllCareInfo() {
        String url= HttpUrlUtils.HTTP_URL+"getAllCareInfoServlet";
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("userId",userId+"");

        x.http().post(params, new Callback.CommonCallback<Object>() {
            @Override
            public void onSuccess(Object result) {

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
        });
    }
}
