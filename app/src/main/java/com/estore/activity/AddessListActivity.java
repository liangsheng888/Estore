package com.estore.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.estore.pojo.Address;

import java.util.ArrayList;
import java.util.List;

public class AddessListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_addess;
    final List<Address> addressList = new ArrayList<Address>();
    BaseAdapter adapter;
    private Object dataList;
    private Button btn_new_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addess_list);
        initView();
        initData();
        initEven();
    }

    private void initView() {
        btn_new_address = ((Button) findViewById(R.id.btn_new_address));
        lv_addess = ((ListView) findViewById(R.id.lv_address));
//        adapter = new BaseAdapter() {
//
//            @Override
//            public int getCount() {
//                return addressList.size();
//            }
//
//            @Override
//            public Object getItem(int i) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int i) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int i, View view, ViewGroup viewGroup) {
//                View view1 = View.inflate(getApplication(), R.layout.addess_item, null);
//                RadioButton rg_address_selector = ((RadioButton) view1.findViewById(R.id.rg_addess_selector));
//                TextView tv_auct_shouhuoren = ((TextView) view1.findViewById(R.id.tv_auct_shouhuoren));
//                TextView tv_tel = ((TextView) view1.findViewById(R.id.tv_tel));
//                TextView tv_bidding_addess = ((TextView) view1.findViewById(R.id.tv_bidding_addess));
//                Address address = addressList.get(i);
//                tv_auct_shouhuoren.setText(address.userId);
//                tv_tel.setText(address.cantactPhone);
//                tv_bidding_addess.setText(address.contactAddress);
//                return view1;
//            }
//        };
        lv_addess.setAdapter(adapter);
//        getDataList();
//        adapter.notifyDataSetChanged();
    }

    private void initData() {

    }

    private void initEven() {
        btn_new_address.setOnClickListener(this);
    }

//    public void getDataList() {
//        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "");
//        x.http().get(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_address:
                Intent intent = new Intent(getApplicationContext(), AddressSelector.class);
                Toast.makeText(AddessListActivity.this, "跳转到选择新地址界面", Toast.LENGTH_SHORT).show();
                System.out.println("跳转到选择新地址界面");
                startActivity(intent);

                break;
        }
    }
}
