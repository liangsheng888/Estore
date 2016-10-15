package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;



public class MyIntercalateActivity extends AppCompatActivity {
    private ImageView iv_intercalatereturn;
    private RelativeLayout rl_perset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_intercalate);

        //返回我的设置
        iv_intercalatereturn= ((ImageView) findViewById(R.id.iv_intercalatereturn));
        iv_intercalatereturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyIntercalateActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //跳到个人资料
        rl_perset = ((RelativeLayout) findViewById(R.id.rl_perset));
        rl_perset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyIntercalateActivity.this,PersonalSettingActivity.class);
                startActivity(intent);
            }
        });

    }
}
