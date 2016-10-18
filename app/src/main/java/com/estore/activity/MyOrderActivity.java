package com.estore.activity;
/*
我的订单页面
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.estore.fragment.AllOrdersFragment;
import com.estore.fragment.WaitingDeliverFragment;
import com.estore.fragment.WaitingEvaluateFragment;
import com.estore.fragment.WaitingPayMoneyFragment;
import com.estore.fragment.WaitingReceiveGoodsFragment;

public class MyOrderActivity extends AppCompatActivity {

    private RadioButton rb_allorder;
    private RadioButton rb_delivergoods;
    private RadioButton rb_paymoney;
    private RadioButton rb_waitingevaluate;
    private RadioButton rb_receivegoods;
    private RadioGroup rg_grouporder;
    private ImageView iv_orderreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        //跳转到Fragment
        rb_allorder = ((RadioButton) findViewById(R.id.rb_allorder));
        rb_delivergoods = ((RadioButton) findViewById(R.id.rb_delivergoods));
        rb_paymoney = ((RadioButton) findViewById(R.id.rb_paymoney));
        rb_waitingevaluate = ((RadioButton) findViewById(R.id.rb_waitingevaluate));
        rb_receivegoods = ((RadioButton) findViewById(R.id.rb_receivegoods));
        //默认显示全部
        switchFragment(new AllOrdersFragment());
         getmyorders();
        //返回我的订单
        iv_orderreturn = ((ImageView) findViewById(R.id.iv_orderreturn));
        iv_orderreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(MyOrderActivity.this,MainActivity.class);
                //startActivity(intent);
                finish();
            }
        });
    }
    //跳转到Fragment
    private void getmyorders() {
        rg_grouporder = ((RadioGroup) findViewById(R.id.rg_grouporder));
        rg_grouporder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Fragment fragment = null;
                switch (checkedId) {
                    case R.id.rb_allorder:
                        fragment = new AllOrdersFragment();
                        break;
                    case R.id.rb_receivegoods:
                        fragment = new WaitingReceiveGoodsFragment();
                        break;
                    case R.id.rb_delivergoods:
                        fragment = new WaitingDeliverFragment();
                        break;
                    case R.id.rb_waitingevaluate:
                        fragment = new WaitingEvaluateFragment();
                        break;
                    case R.id.rb_paymoney:
                        fragment = new WaitingPayMoneyFragment();
                        break;
                }
                switchFragment(fragment);
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        this.getSupportFragmentManager().beginTransaction().replace(R.id.fl_orders, fragment).commit();
    }
    //end
}