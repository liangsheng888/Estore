package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.google.gson.Gson;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";
    private ListView lv_cart;
    private TextView tv_check_money;
    private TextView btn_jiesuan;
    private BaseAdapter adapter;
    final ArrayList<Product.Products> projectList=new ArrayList<Product.Products>();
    private ImageView iv_project_cart_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        iv_project_cart_back = ((ImageView) findViewById(R.id.iv_project_cart_back));
        lv_cart = ((ListView) findViewById(R.id.lv_cart));
        tv_check_money = ((TextView) findViewById(R.id.tv_check_money));
        btn_jiesuan = ((TextView) findViewById(R.id.btn_jiesuan));

        iv_project_cart_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,ProductInfoActivity.class);
                startActivity(intent);
            }
        });



        lv_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), ProductInfoActivity.class);
                startActivity(intent);
            }
        });

        adapter = new BaseAdapter() {
            private ImageView iv_project_photo;
            private TextView tv_project_description;

            @Override
            public int getCount() {
                return projectList.size();
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
                View view = View.inflate(getApplicationContext(), R.layout.eh_item, null);
                iv_project_photo = ((ImageView) view.findViewById(R.id.iv_project_photo));
                TextView tv_project_name = ((TextView) view.findViewById(R.id.tv_project_name));
                TextView tv_project_price = ((TextView) view.findViewById(R.id.tv_project_price));
                tv_project_description = ((TextView) view.findViewById(R.id.tv_project_description));
                Product.Products list = projectList.get(position);
                tv_project_name.setText(list.name);
                tv_project_price.setText(list.estoreprice + "");
                tv_project_description.setText(list.description);
                x.image().bind(iv_project_photo, HttpUrlUtils.HTTP_URL+list.imgurl);
                return view;
            }

        };
        lv_cart.setAdapter(adapter);

    }
    public void getSameCityList(){
        RequestParams params=new RequestParams(HttpUrlUtils.HTTP_URL+"/getAllProducts");

        Log.i(TAG,HttpUrlUtils.HTTP_URL+"imgurl");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //Log.i(TAG,result+"==========");
                Gson gson=new Gson();
                Product project=gson.fromJson(result,Product.class);
                projectList.addAll(project.list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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

