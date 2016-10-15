package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SystemInformActivity extends AppCompatActivity {
    private ImageView iv_informreturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_inform);
        //返回系统通知
        iv_informreturn = ((ImageView) findViewById(R.id.iv_informreturn));
        iv_informreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SystemInformActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
