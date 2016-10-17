package com.estore.activity;
/*
我的关注页面
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
public class MyCareActivity extends AppCompatActivity {
    private ImageView iv_carereturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_care);
        //返回我的关注
        iv_carereturn = ((ImageView) findViewById(R.id.iv_carereturn));
        iv_carereturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MyCareActivity.this,MainActivity.class);
                //startActivity(intent);
              finish();
            }
        });
    }
}
