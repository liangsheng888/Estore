package com.estore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.pojo.Address;
import com.estore.pojo.AuctListActivityBean;

public class PaiMaiMain_bidding extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_bidding_addess;
    int addressSign = 1;
    private TextView tv_auct_shouhuo;
    private TextView tv_usertel;
    private TextView tv_address_info;
    Address address;
    public final static int SIGN = 4; //传值回调标志位
    private LinearLayout ll_auct_address;
    private Button btn_up_baozheng;
    AuctListActivityBean.Auct auct;
    Integer paiMaiChangCiFlag;
    private TextView tv_info_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pai_mai_main_bidding);
        initView();
        initData();
        initEven();
        Intent intent = getIntent();
        auct = (AuctListActivityBean.Auct) intent.getSerializableExtra("auct");
        paiMaiChangCiFlag = intent.getIntExtra("paiMaiChangCiFlag", 0);
        System.out.println("保证金页面的auct" + auct);
    }

    private void initView() {
        tv_bidding_addess = ((TextView) findViewById(R.id.tv_bidding_addess));//选择地址
        tv_auct_shouhuo = ((TextView) findViewById(R.id.tv_auct_bid_shouhuo));
        tv_usertel = ((TextView) findViewById(R.id.tv_usertel));
        tv_address_info = ((TextView) findViewById(R.id.tv_address_info));
        ll_auct_address = ((LinearLayout) findViewById(R.id.ll_auct_address));
        btn_up_baozheng = ((Button) findViewById(R.id.btn_up_baozheng));
        tv_info_back = ((TextView) findViewById(R.id.tv_info_back));

    }

    private void initEven() {
        tv_bidding_addess.setOnClickListener(this);
        btn_up_baozheng.setOnClickListener(this);
        tv_info_back.setOnClickListener(this);
    }

    private void initData() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        address = (Address) data.getSerializableExtra("addressSign");
        System.out.println("获得地址信息" + address);
        ll_auct_address.setVisibility(View.VISIBLE);
        System.out.println(address.cantactName);
        tv_auct_shouhuo.setText(address.cantactName + "");
        tv_usertel.setText(address.cantactPhone);
        tv_address_info.setText(address.detailed_address);

    }


    //    addressSign=intent.getIntExtra("addressSign",addressSign);
    public void onBackPressed() {
//        Log.d(TAG, "onBackPressed()");
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), PaimaiMain_infoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bidding_addess:
                Intent intent = new Intent(PaiMaiMain_bidding.this, AddessListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("addressSign", addressSign);
                intent.putExtras(bundle);
                startActivityForResult(intent, SIGN);
                break;
            case R.id.tv_info_back:
                intent = new Intent(getApplicationContext(), PaimaiMain_infoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_up_baozheng:
                String userName=tv_auct_shouhuo.getText().toString().trim();
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(this, "请选择地址信息", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    intent = new Intent(getApplicationContext(), PaiMAIbidding_bidActivity.class);
                    bundle = new Bundle();
                    bundle.putSerializable("auct_bid", auct);
                    System.out.println("向加价界面传值" + auct);
                    bundle.putInt("paiMaiChangCiFlag", paiMaiChangCiFlag);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }
}
