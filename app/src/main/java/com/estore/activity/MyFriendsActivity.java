package com.estore.activity;
/*
我的好友页面
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.estore.R;

public class MyFriendsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

//        SharedPreferences sp1=getSharedPreferences("user",MODE_APPEND);
//        String token=sp1.getString("token","");
//        Log.i("cc", "onCreate: "+token);
//        RongIM.connect(token, new RongIMClient.ConnectCallback() {
//            @Override
//            public void onTokenIncorrect() {
//
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                Log.i("cc", "——onSuccess—-" + s);
//
////                startActivity(new Intent(ProductInfoActivity.this,MyFriendsActivity.class));
//
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.i("cc","--onError--"+errorCode);
//
//            }
//        });

    }
}
