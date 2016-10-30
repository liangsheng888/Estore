package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
/*
我的设置页面
 */


public class MyIntercalateActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView iv_intercalatereturn;
    private RelativeLayout rl_perset;
    private Button setreback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_intercalate);
        initView();
        initDate();
        initEvent();





    }
    private void initView() {
        //返回我的设置
        iv_intercalatereturn= ((ImageView) findViewById(R.id.iv_intercalatereturn));
        //跳到个人资料
        rl_perset = ((RelativeLayout) findViewById(R.id.rl_perset));
        setreback = ((Button) findViewById(R.id.rb_tuichuset));
    }
    private void initDate() {

    }


    private void initEvent() {
        //返回我的设置
        iv_intercalatereturn.setOnClickListener(this);
        //跳到个人资料
        rl_perset.setOnClickListener(this);
        setreback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_intercalatereturn:
                finish();
            case R.id.rl_perset:
                Intent intent=new Intent(getApplicationContext(),PersonalSettingActivity.class);
                startActivity(intent);
            case R.id.rb_tuichuset:
                finish();
        }
    }
}
