package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estore.pojo.Address;

public class PaiMaiMain_bidding extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_bidding_addess;
    int addressSign = 1;
    private TextView tv_auct_shouhuo;
    private TextView tv_usertel;
    private TextView tv_address_info;
    Address address;
    public  final static int SIGN=4; //传值回调标志位
    private LinearLayout ll_auct_address;

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
        tv_auct_shouhuo = ((TextView) findViewById(R.id.tv_auct_shouhuo));
        tv_usertel = ((TextView) findViewById(R.id.tv_usertel));
        tv_address_info = ((TextView) findViewById(R.id.tv_address_info));
        ll_auct_address = ((LinearLayout) findViewById(R.id.ll_auct_address));

    }

    private void initEven() {
        tv_bidding_addess.setOnClickListener(this);
    }

    private void initData() {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        address= (Address) data.getSerializableExtra("addressSign");
        System.out.println("获得地址信息"+address);
        ll_auct_address.setVisibility(View.VISIBLE);

        tv_auct_shouhuo.setText(address.userId+"");
        tv_usertel.setText(address.cantactPhone);
        tv_address_info.setText(address.detailed_address);

    }


//    addressSign=intent.getIntExtra("addressSign",addressSign);

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bidding_addess:
                Intent intent = new Intent(PaiMaiMain_bidding.this, AddessListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("addressSign", addressSign);
                intent.putExtras(bundle);
                startActivityForResult(intent,SIGN);
                break;
        }
    }
}
