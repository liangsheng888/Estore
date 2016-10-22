package com.estore.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
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
    int addressSign = 0;//传输adress标记，1：竞价界面 2：购买页面 3：个人中心
    public final static int SIGNOK = 3; //传值回调标志位
    private CheckBox cb_address_moren;
    private Button btn_address_delete;
    private Button btn_address_edt;
    Address address;
    int EDTADDRESS = 0;//传输到编辑地址界面的标志位
    int ADDRESSEDT = 0;//编辑按钮跳转编辑页面时所用标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addess_list);
        initView();
        initData();
        initEven();
        Intent intent = getIntent();
        addressSign = intent.getIntExtra("addressSign", addressSign);
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
//                RadioButton rg_address_selector = ((RadioButton) view1.findViewById(R.id.rg_addess_selector));
                TextView tv_auct_shouhuoren = ((TextView) view1.findViewById(R.id.tv_auct_shouhuoren));
                TextView tv_tel = ((TextView) view1.findViewById(R.id.tv_tel));
                TextView tv_bidding_addess = ((TextView) view1.findViewById(R.id.tv_bidding_addess));
                //下面三个控件是item上得按钮
                cb_address_moren = ((CheckBox) findViewById(R.id.cb_address_moren));//是否设置默认
                btn_address_delete = ((Button)view1.findViewById(R.id.btn_address_delete));//删除
                btn_address_edt = ((Button) view1.findViewById(R.id.btn_address_edt));//编辑

                btn_address_edt.setOnClickListener(AddessListActivity.this);
                btn_address_edt.setTag(i);
//                cb_address_moren.setOnClickListener(AddessListActivity.this);
                btn_address_delete.setOnClickListener(AddessListActivity.this);
                btn_address_delete.setTag(i);

                Address address = addressList.get(i);
                tv_auct_shouhuoren.setText(address.cantactName + "");
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
//        cb_address_moren.setOnClickListener(this);
//        btn_address_delete.setOnClickListener(this);
        btn_new_address.setOnClickListener(this);
        lv_addess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                System.out.println("addressSign" + addressSign);
                address = addressList.get(i);
                System.out.println("address" + address);
                System.out.println(i);

                switch (addressSign) {
                    case 1://拍卖页面
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("addressSign", address);
                        intent.putExtras(bundle);
                        setResult(SIGNOK, intent);
//                        intent.setClass(getApplicationContext(), PaiMaiMain_bidding.class);
//                        startActivity(intent);
                        finish();
                        break;


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
                Bundle bundle = new Bundle();
                int position = (int) view.getTag();
                System.out.println(position);
                address = addressList.get(position);
                bundle.putSerializable("addressSign_add", address);
                intent.putExtra("addressedt", ADDRESSEDT);//传到添加页面的标志位
                intent.putExtras(bundle);
                Toast.makeText(AddessListActivity.this, "跳转到选择新地址界面", Toast.LENGTH_SHORT).show();
                System.out.println("跳转到选择新地址界面");
                startActivityForResult(intent, ADDRESSINFO);
                break;
            case R.id.btn_address_edt:
                intent = new Intent(AddessListActivity.this, EdtAddressActivity.class);
              bundle = new Bundle();
               position = (int) view.getTag();
                System.out.println(position);
                address = addressList.get(position);
                bundle.putSerializable("addressSign", address);
                intent.putExtra("addressedt", ADDRESSEDT);//传到编辑页面的标志位
                intent.putExtras(bundle);
                System.out.println("点击编辑携带数据填充" + address);
                startActivityForResult(intent, EDTADDRESS);
                break;
            case R.id.btn_address_delete:
                int position2 = (int) view.getTag();
                Toast.makeText(AddessListActivity.this, "点击了删除", Toast.LENGTH_SHORT).show();
                address = addressList.get(position2);
                System.out.println("点击删除里的address"+address);
                RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "deleteaddressservlet");
                requestParams.addBodyParameter("addressId", String.valueOf(address.getAddressId()));
                System.out.println("requestParams"+requestParams);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("点击删除地址" + result);
                        getDataList();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        System.out.println("点击删除错误"+ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;

        }
    }
}
