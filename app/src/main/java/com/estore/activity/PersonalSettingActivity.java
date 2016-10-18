package com.estore.activity;
/*
个人资料设置页面
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

//个人资料设置
public class PersonalSettingActivity extends AppCompatActivity {

    private ImageView iv_perreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        //返回个人资料
        iv_perreturn = ((ImageView) findViewById(R.id.iv_perreturn));
        iv_perreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(PersonalSettingActivity.this, MyIntercalateActivity.class);
                //startActivity(intent);
                finish();
            }
        });

    }
}
