package com.estore.activity;
/*
我的钱包
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.estore.R;

public class MyWalletActivity extends AppCompatActivity {
    private ImageView iv_friendsreturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
    //返回我的钱包
        iv_friendsreturn = ((ImageView) findViewById(R.id.iv_friendsreturn));
        iv_friendsreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MyWalletActivity.this,MainActivity.class);
                //startActivity(intent);
                finish();
            }
        });
    }
}
