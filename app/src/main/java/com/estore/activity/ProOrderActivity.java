package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.httputils.MapSerializable;
import com.estore.pojo.Product;

import java.util.Map;
import java.util.Set;

/**
 * 订单
 */

public class ProOrderActivity extends AppCompatActivity {
    private TextView order_count_total_money;//订单总价格
    private TextView order_pnum;//订单商品数数量
    private TextView order_dizhi_phonenum;//手机号
    private TextView order_dizhi_detaildizhi;//详细地址
    private TextView order_prod_yunfei_money;//运费
    private TextView order_prod_fapiao_right;//发票
    private ListView order_scroll_listview;//订单中的商品列表
    private TextView order_total_money;//实际价格
    private Button order_goumai;//立即购买
    private Map<Product.Products, Integer> mapOrderInfo;
    private int number=0;//订单商品数数量
    private Double totalprice=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_order);
        getDataByIntent();
        initView();
        initData();
    }

    private void getDataByIntent() {
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        MapSerializable OrderInfo=(MapSerializable)bundle.getSerializable("OrderInfo");
        mapOrderInfo=OrderInfo.getPro();
        for (Map.Entry<Product.Products, Integer> mapinfo:
        mapOrderInfo.entrySet()) {
            Product.Products pp=mapinfo.getKey();
            number=mapinfo.getValue();
            totalprice+=pp.estoreprice*number;

        }


    }

    private void initData() {
        order_count_total_money.setText(totalprice+"");
        order_pnum.setText("共"+number+"件商品    合计:");
        order_total_money.setText("￥"+totalprice);


    }
    private void initView() {
        order_count_total_money=(TextView) findViewById(R.id.order_count_total_money);
        order_pnum=(TextView)findViewById(R.id.order_count_heji);
        order_dizhi_phonenum=(TextView)findViewById(R.id.order_dizhi_phonenum);
        order_dizhi_detaildizhi=(TextView)findViewById(R.id.order_dizhi_detaildizhi);
        order_prod_yunfei_money=(TextView)findViewById(R.id.order_prod_yunfei_money);
        order_prod_fapiao_right=(TextView)findViewById(R.id.order_prod_fapiao_right);
        order_scroll_listview=(ListView) findViewById(R.id.order_scroll_listview);
        order_total_money=(TextView)findViewById(R.id.order_total_money);
        order_goumai=(Button)findViewById(R.id.order_goumai);
    }
}
