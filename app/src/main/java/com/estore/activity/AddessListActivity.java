package com.estore.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Address;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddessListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_addess;
    List<Address> addressList = new ArrayList<Address>();
    ArrayList<Address> list = new ArrayList<Address>();
    BaseAdapter adapter;
    private Object dataList;
    private Button btn_new_address;
    Integer ADDRESSINFO = 1;
    int addressSign=0;//传输adress标记，1：竞价界面 2：购买页面 3：个人中心
    public  final static int SIGNOK=3; //传值回调标志位
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addess_list);
        initView();
        initData();
        initEven();
        Intent intent=getIntent();
        addressSign=intent.getIntExtra("addressSign",addressSign);
    }

    private void initView() {
        btn_new_address = ((Button) findViewById(R.id.btn_new_address));
        lv_addess = ((ListView) findViewById(R.id.lv_address));
        adapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return addressList.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View view1 = View.inflate(getApplication(), R.layout.addess_item, null);
                RadioButton rg_address_selector = ((RadioButton) view1.findViewById(R.id.rg_addess_selector));
                TextView tv_auct_shouhuoren = ((TextView) view1.findViewById(R.id.tv_auct_shouhuoren));
                TextView tv_tel = ((TextView) view1.findViewById(R.id.tv_tel));
                TextView tv_bidding_addess = ((TextView) view1.findViewById(R.id.tv_bidding_addess));
                Address address = addressList.get(i);
                tv_auct_shouhuoren.setText(address.userId + "");
                tv_tel.setText(address.cantactPhone);
                tv_bidding_addess.setText(address.detailed_address);
                return view1;
            }
        };
        lv_addess.setAdapter(adapter);
        getDataList();

    }

    private void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = data.getExtras().getString("result");
        System.out.println("result________________________-" + result);
        getDataList();
    }

    private void initEven() {
        btn_new_address.setOnClickListener(this);
        lv_addess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                System.out.println("addressSign"+addressSign);
                Address address=addressList.get(i);
                System.out.println("address"+address);
                System.out.println(i);

                switch (addressSign){
                    case 1:
                      Intent  intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("addressSign", address);
                        intent.putExtras(bundle);
                        setResult(SIGNOK,intent);
//                        intent.setClass(getApplicationContext(), PaiMaiMain_bidding.class);
//                        startActivity(intent);
                        finish();
                }

            }
        });
    }

    public void getDataList() {
        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "addaddress");
        System.out.println("address-requestParams----------------" + requestParams);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("result---------------" + result);
                System.out.println("成功");

                Gson gson = new Gson();

//                Type type=new TypeToken<List<Address>>(){}.getType();
//
//                List<Address> newlist = new ArrayList<Address>();
//                System.out.println("2正确");
//                newlist=gson.fromJson(result,type);
                List<Address> newlist = new ArrayList<Address>();
                Type type = new TypeToken<List<Address>>() {
                }.getType();
                newlist = gson.fromJson(result, type);
                addressList.clear();
                addressList.addAll(newlist);
                System.out.println("newlist-----------" + addressList);
//                addressList.addAll(stuList1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("ex----------------" + ex.getMessage() + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_address:
                Intent intent = new Intent(getApplicationContext(), AddressSelector.class);
                Toast.makeText(AddessListActivity.this, "跳转到选择新地址界面", Toast.LENGTH_SHORT).show();
                System.out.println("跳转到选择新地址界面");
                startActivityForResult(intent, ADDRESSINFO);

                break;
        }
    }
}
