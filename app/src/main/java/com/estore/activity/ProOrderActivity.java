package com.estore.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.activity.myappliction.MyApplication;
import com.estore.httputils.GetUserIdByNet;

import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.MapSerializable;
import com.estore.pojo.Address;
import com.estore.pojo.Cart;
import com.estore.pojo.InsertOrderBean;
import com.estore.pojo.Product;
import com.estore.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 订单
 */

public class ProOrderActivity extends AppCompatActivity {
    private static final int ADDRESS_CHOICE = 5;//选择地址
    private TextView order_count_total_money;//订单总价格
    private TextView order_pnum;//订单商品数数量
    private TextView order_dizhi_phonenum;//手机号
    private TextView order_dizhi_detaildizhi;//详细地址
    private TextView order_prod_yunfei;//运费
    private TextView order_prod_fapiao_right;//发票
    private ListView order_scroll_listview;//订单中的商品列表
    private TextView order_total_money;//实际价格
    private RelativeLayout order_dizhi;//默认地址
    private Button order_goumai;//立即购买
    private ImageView iv_dingdan_fanhui;
    Address address = new Address();

    private Map<Product.Products, Integer> mapOrderInfo=new HashMap<>();
    private List<Product.Products> proLists = new ArrayList<>();
    private List<Integer> cartIdLists = new ArrayList<>();//
    private List<Integer> num = new ArrayList<>();
    private int number = 0;//订单商品数数量
    private Double totalprice = 0.0;
    private SharedPreferences sp;
    Product.Products pp = new Product.Products();
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prod_order);
        sp = getSharedPreferences("User", MODE_APPEND);
        user.setUserId(sp.getInt("userId", 0));
        getDataByIntent();
        initView();
        initData();
    }

    private void getDataByIntent() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        MapSerializable OrderInfo = (MapSerializable) bundle.getSerializable("OrderInfo");
        cartIdLists = (List<Integer>) bundle.getSerializable("cartIdLists");
        Log.i("ProdOrderActivity", "OrderInfo: " + OrderInfo.toString());
        mapOrderInfo = OrderInfo.getPro();
        for (Map.Entry<Product.Products, Integer> mapinfo :
                mapOrderInfo.entrySet()) {
            pp = mapinfo.getKey();
            number = mapinfo.getValue();
            totalprice += pp.estoreprice * number;
            proLists.add(pp);
            num.add(number);

        }


    }

    private void initData() {
        //返回
        iv_dingdan_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //默认地址
        order_dizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProOrderActivity.this, AddessListActivity.class);
                intent.putExtra("addressSign", 3);
                startActivityForResult(intent, ADDRESS_CHOICE);
            }
        });
        order_dizhi_phonenum.setText(address.cantactPhone);//联系人电话
        order_dizhi_detaildizhi.setText(address.detailed_address);//联系人地址
        //  order_prod_yunfei.setText(pp.youfei+"");
        order_count_total_money.setText(totalprice + "");
        order_pnum.setText("共" + number + "件商品    合计:");
        order_total_money.setText("￥" + totalprice);
        order_scroll_listview.setAdapter(new MyOrderAdapter());
        //立即购买
        order_goumai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address == null || address.cantactPhone == null) {
                    Toast.makeText(ProOrderActivity.this, "请选择收货地址", Toast.LENGTH_LONG).show();
                    return;
                }
                RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "orderInsertServlet");
                InsertOrderBean insertOrderBean = new InsertOrderBean();
                insertOrderBean.setUserId(user.getUserId());
                insertOrderBean.setAddressId(1);//默认地址
                insertOrderBean.setDetails(mapOrderInfo);//所有的商品：添加的是key-value的
                insertOrderBean.setTotalPrice(totalprice);
                //对象转换成json数据
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                        .create();
                String orderInfo = gson.toJson(insertOrderBean);
                //get方法传参数
                requestParams.addQueryStringParameter("orderInfo", orderInfo);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("ProdOrderActivity", "onSuccess: ");
                        Log.i("ProdOrderActivity", "onSuccess: "+cartIdLists.size() );
                       /* AlertDialog.Builder builder=new AlertDialog.Builder(ProOrderActivity.this);
                        builder.setTitle("")*/
                        if (cartIdLists.size() > 0) {
                            deleteCart(cartIdLists);//清空购物车
                        }
                        Intent intent = new Intent(ProOrderActivity.this, MyOrderActivity.class);
                        startActivity(intent);//跳转到我的订单


                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(Callback.CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


            }
        });


    }

    private void deleteCart(final List<Integer> cartIdLists) {

        Log.i("@@@@@", "gggggggggg" + cartIdLists.size());
        //Integer cartId = cartlist.get(position).getCartId();
        // Log.i("@@@@@", "=============" + cartId + "");
        String url = HttpUrlUtils.HTTP_URL + "deleteSelectCartServlet";
        RequestParams requestParams = new RequestParams(url);
        Gson gson = new Gson();
        String cartLists = gson.toJson(cartIdLists);
        requestParams.addQueryStringParameter("cartLists", cartLists + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("@@@@@", "删除成功");
                //Toast.makeText(ProOrderActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //   Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    private void initView() {
        order_count_total_money = (TextView) findViewById(R.id.order_count_total_money);
        order_pnum = (TextView) findViewById(R.id.order_count_heji);
        order_dizhi_phonenum = (TextView) findViewById(R.id.order_dizhi_phonenum);
        order_dizhi_detaildizhi = (TextView) findViewById(R.id.order_dizhi_detaildizhi);
        // order_prod_yunfei_money=(TextView)findViewById(R.id.order_prod_yunfei_money);
        order_prod_fapiao_right = (TextView) findViewById(R.id.order_prod_fapiao_right);
        order_scroll_listview = (ListView) findViewById(R.id.order_scroll_listview);
        order_total_money = (TextView) findViewById(R.id.order_total_money);
        order_goumai = (Button) findViewById(R.id.order_goumai);
        order_dizhi = ((RelativeLayout) findViewById(R.id.order_dizhi));
        // order_prod_yunfei=(TextView)findViewById(R.id.order_prod_yunfei_money);
        iv_dingdan_fanhui = (ImageView) findViewById(R.id.iv_dingdan_fanhui);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        address = (Address) data.getExtras().getSerializable("addressSign");
        order_dizhi_phonenum.setText(address.cantactPhone);//联系人电话
        order_dizhi_detaildizhi.setText(address.detailed_address);//联系人地址
    }

    public class MyOrderAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return proLists.size();
        }

        @Override
        public Object getItem(int position) {
            return proLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(ProOrderActivity.this, R.layout.layout_order_pro_item, null);
            ImageView iv_pro_photo = (ImageView) view.findViewById(R.id.iv_pro_photo);
            TextView tv_order_desc = (TextView) view.findViewById(R.id.tv_order_desc);
            TextView tv_order_price = (TextView) view.findViewById(R.id.tv_order_price);
            TextView tv_order_youfei = (TextView) view.findViewById(R.id.tv_order_youfei);
            TextView tv_order_number = (TextView) view.findViewById(R.id.tv_order_number);
            Product.Products pp = proLists.get(position);

            Log.i("ProdOrderActivity", "pp: " + pp.toString());
            String[] imgs = pp.imgurl.split("=");
            x.image().bind(iv_pro_photo, HttpUrlUtils.HTTP_URL + imgs[0]);
            tv_order_desc.setText(pp.name + pp.description);
            tv_order_price.setText("￥" + pp.estoreprice);
            tv_order_youfei.setText("邮费￥" + pp.youfei);
            tv_order_number.setText(num.get(position) + "件");
            return view;
        }
    }
}
