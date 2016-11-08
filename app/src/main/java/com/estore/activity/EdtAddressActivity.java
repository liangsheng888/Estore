package com.estore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Address;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class EdtAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner provinceSpinner = null;  //省级（省、直辖市）
    private Spinner citySpinner = null;     //地级市
    private Spinner countySpinner = null;    //县级（区、县、县级市）
    ArrayAdapter<String> provinceAdapter = null;  //省级适配器
    ArrayAdapter<String> cityAdapter = null;    //地级适配器
    ArrayAdapter<String> countyAdapter = null;    //县级适配器
    static int provincePosition = 3;
    List<Address> addressList = new ArrayList<Address>();
    String provincestr;
    String citystr;
    String countystr;
    String userName;
    String userTel;
    String addressInfo;
    //省级选项值
    private String[] province = new String[]{"北京", "上海", "天津", "广东"};//,"重庆","黑龙江","江苏","山东","浙江","香港","澳门"};
    //地级选项值
    private String[][] city = new String[][]
            {
                    {"东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区",
                            "房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县",
                            "延庆县"},
                    {"长宁区", "静安区", "普陀区", "闸北区", "虹口区"},
                    {"和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区",
                            "东丽区"},
                    {"广州", "深圳", "韶关" // ,"珠海","汕头","佛山","湛江","肇庆","江门","茂名","惠州","梅州",
                            // "汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮"
                    }
            };

    //县级选项值
    private String[][][] county = new String[][][]
            {
                    {   //北京
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"},
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //上海
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //天津
                            {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}, {"无"}
                    },
                    {    //广东
                            {"海珠区", "荔湾区", "越秀区", "白云区", "萝岗区", "天河区", "黄埔区", "花都区", "从化市", "增城市", "番禺区", "南沙区"}, //广州
                            {"宝安区", "福田区", "龙岗区", "罗湖区", "南山区", "盐田区"}, //深圳
                            {"武江区", "浈江区", "曲江区", "乐昌市", "南雄市", "始兴县", "仁化县", "翁源县", "新丰县", "乳源县"}  //韶关
                    }
            };
    Address address;
    private Button btn_address_ok;
    private EditText et_address_tel;
    private EditText et_address_name;
    private EditText et_address_info;
    private CheckBox rb_address;
  int  ADDRESSEDT;//编辑传过来的标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selector);
        setSpinner();
        initView();
//        initSetData() ;
        initEven();

        getdata();
        Intent intent = getIntent();
        address = (Address) intent.getSerializableExtra("addressSign");
        System.out.println("编辑页面address"+address);
//         ADDRESSEDT= (int) intent.getSerializableExtra("addressedt");
//        System.out.println("编辑页面ADDRESSEDT"+ADDRESSEDT);
        et_address_name.setText(address.cantactName+"");
        et_address_tel.setText(address.cantactPhone);
        et_address_info.setText(address.detailed_address);
    }

    public void onBackPressed() {
//        Log.d(TAG, "onBackPressed()");
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AddessListActivity.class);
        startActivity(intent);
    }


    private void getdata() {
        provincestr = provinceSpinner.getSelectedItem().toString();
        citystr = citySpinner.getSelectedItem().toString();
        countystr = countySpinner.getSelectedItem().toString();
        userName = et_address_name.getText().toString();
        userTel = et_address_tel.getText().toString();
        addressInfo = et_address_info.getText().toString();
//       addressList.add()
    }

    private void initView() {
        btn_address_ok = ((Button) findViewById(R.id.btn_address_ok));
        et_address_name = ((EditText) findViewById(R.id.et_address_name));
        et_address_tel = ((EditText) findViewById(R.id.et_address_tel));
        et_address_info = ((EditText) findViewById(R.id.et_address_info));
        rb_address = ((CheckBox) findViewById(R.id.rb_address));
    }

    private void initEven() {
        btn_address_ok.setOnClickListener(this);
    }

    /*
     * 设置下拉框
     */
    private void setSpinner() {
        provinceSpinner = (Spinner) findViewById(R.id.spin_province);
        citySpinner = (Spinner) findViewById(R.id.spin_city);
        countySpinner = (Spinner) findViewById(R.id.spin_county);

        //绑定适配器和值
        provinceAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.simple_spinner_item, android.R.id.text1, province);
        provinceSpinner.setAdapter(provinceAdapter);

        provinceSpinner.setSelection(3, true);  //设置默认选中项，此处为默认选中第4个值

        cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.simple_spinner_item, city[3]);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setSelection(0, true);  //默认选中第0个

        countyAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.simple_spinner_item, county[3][0]);
        countySpinner.setAdapter(countyAdapter);
        countySpinner.setSelection(0, true);


        //省级下拉框监听
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // 表示选项被改变的时候触发此方法，主要实现办法：动态改变地级适配器的绑定值
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {

                TextView tv = (TextView) view;
//                tv.setTextColor(getResources().getColor(R.color.black));    //设置颜色
//                tv.setTextColor(Color.BLACK);
//                tv.setTextSize(18.0f);    //设置大小

//                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中

                //position为当前省级选中的值的序号

                //将地级适配器的值改变为city[position]中的值
                cityAdapter = new ArrayAdapter<String>(
                        getApplicationContext(),  R.layout.simple_spinner_item, city[position]);
                // 设置二级下拉列表的选项内容适配器
                citySpinner.setAdapter(cityAdapter);
                provincePosition = position;    //记录当前省级序号，留给下面修改县级适配器时用
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        //地级下拉监听
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long arg3) {
                countyAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.simple_spinner_item, county[provincePosition][position]);
                countySpinner.setAdapter(countyAdapter);

                TextView tv = (TextView) view;
//                tv.setTextColor(getResources().getColor(R.color.black));    //设置颜色

//                tv.setTextSize(18.0f);    //设置大小

//                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        //市级监听
        countySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
//                tv.setTextColor(getResources().getColor(R.color.black));    //设置颜色
//                tv.setTextColor(Color.BLACK);
//                tv.setTextSize(18.0f);    //设置大小

//                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_address_ok:
                sendmessg();


        }
    }

    private void sendmessg() {
        Integer moren = 0;
        String province = provinceSpinner.getSelectedItem().toString();
        String city = citySpinner.getSelectedItem().toString();
        String county = countySpinner.getSelectedItem().toString();

        String userName = et_address_name.getText().toString().trim();
        String userTel = et_address_tel.getText().toString().trim();
        String detailedaddress = et_address_info.getText().toString().trim();
        Boolean ismoren = rb_address.isChecked();
        if (ismoren) {
            moren = 1;
        }

        final RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "modifyaddressservlet");
        requestParams.addBodyParameter("addressId", String.valueOf(address.getAddressId()));
        requestParams.addBodyParameter("userName", userName);
        requestParams.addBodyParameter("userId", address.getUserId()+"");
        requestParams.addBodyParameter("cantactPhone", userTel);
        requestParams.addBodyParameter("contactAddress", province + city + county);
        requestParams.addBodyParameter("isDefault", String.valueOf(moren));
        requestParams.addBodyParameter("detailed_address", detailedaddress);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("addressSelectorresult" + result);

                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", "插入成功");
                //设置返回数据
                EdtAddressActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                EdtAddressActivity.this.finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("ex" + ex.getMessage() + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
