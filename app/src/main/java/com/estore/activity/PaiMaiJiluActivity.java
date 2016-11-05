package com.estore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.AuctListActivityBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bean.Product;

public class PaiMaiJiluActivity extends AppCompatActivity {
    AuctListActivityBean.Auct auct;
    List<Product> productlist = new ArrayList<Product>();
    private ListView tv_paimai_jilu_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pai_mai_jilu);
        Intent intent = getIntent();
        auct = (AuctListActivityBean.Auct) intent.getSerializableExtra("auctjilu");
        System.out.println("拍卖记录auct"+auct);
        tv_paimai_jilu_ = ((ListView) findViewById(R.id.lv_paimai_jilu));
        getPaiMaiJuluData();
    }
    private class ListAdapter extends BaseAdapter {
        private TextView tv_auct_time;
        private TextView tv_bidd_price;
        private TextView tv_paimai_name;
//        private TextView tv_nowprice;

        @Override
        public int getCount() {

            return productlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(PaiMaiJiluActivity.this, R.layout.paimai_jillu_item, null);
            tv_paimai_name = ((TextView) convertView.findViewById(R.id.tv_paimai_name));
            tv_bidd_price = ((TextView) convertView.findViewById(R.id.tv_bidd_price));
          TextView  tv_paimai_julu_order = ((TextView) convertView.findViewById(R.id.tv_paimai_julu_order));

            tv_auct_time = ((TextView) convertView.findViewById(R.id.tv_bid_time));
//             tv_nowprice = ((TextView) convertView.findViewById(R.id.tv_nowprice));
            Product product = productlist.get(position);
            System.out.println("product" + product + "======productlist" + productlist);
            tv_paimai_name.setText(product.getAuct_name());
            tv_bidd_price.setText(product.getAuct_bid_price() + "");
            String bidTime=new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(product.getAuct_end());
            tv_auct_time.setText(bidTime+ "");
            tv_paimai_julu_order.setText("第"+(position+1)+"条");
            return convertView;
        }
    }
    private void getPaiMaiJuluData() {
        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "getPaiMaiJiLuDataServlet");
        requestParams.addBodyParameter("auct_id", auct.auct_id);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("拍卖记录数据" + result);
                Gson gson = new Gson();

                List<Product> newlist = new ArrayList<Product>();
                Type type = new TypeToken<List<Product>>() {
                }.getType();
                newlist = gson.fromJson(result, type);
                productlist.clear();
                productlist.addAll(newlist);
                System.out.println("获得传过来的数据productlist"+productlist);
                System.out.println("productlist.size()" + productlist.size());
//                if (productlist.size() == 0) {
//                    tv_nowprice.setText(auct.auct_minprice + "￥");
//                } else {
//                    tv_nowprice.setText(productlist.get(0).getAuct_bid_price() + "￥");
//                }
//                tv_paimai_jilu.setText(productlist.size() + "条");
                ListAdapter listAdapter = new ListAdapter();
                tv_paimai_jilu_.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("拍卖记录错误" + ex.getMessage());
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
