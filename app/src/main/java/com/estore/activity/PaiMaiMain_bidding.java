package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PaiMaiMain_bidding extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_bidding_addess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pai_mai_main_bidding);
        initView();
        initData();
        initEven();
    }
    private void initView() {
        tv_bidding_addess = ((TextView) findViewById(R.id.tv_bidding_addess));//选择地址
    }
    private void initEven() {
        tv_bidding_addess.setOnClickListener(this);
    }

    private void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_bidding_addess:
                Intent intent=new Intent(PaiMaiMain_bidding.this,AddessListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
