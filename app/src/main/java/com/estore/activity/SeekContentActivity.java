package com.estore.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.estore.view.LoadListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class SeekContentActivity extends Activity implements LoadListView.ILoadListener  {
    private static final int HOME = 0;
    private LoadListView seekList;
    private List<Product.Products> product=new ArrayList<>();
    private MySeekAdapter adapter;
    private ImageView iv_seek_last;
    private String seekContent;
    private int  page=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seek);
        Intent intent=getIntent();
        seekContent=intent.getStringExtra("content");
        Log.e("@@@@","seekContent"+seekContent);
        seekList=(LoadListView)findViewById(R.id.list_seek);
        iv_seek_last = ((ImageView) findViewById(R.id.iv_seek_last));
        iv_seek_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(HOME,intent);
                finish();
            }
        });
        seekList.setInterface(this);
        getData();
        seekList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Product.Products pp= product.get(position);
                Intent intent=new Intent(SeekContentActivity.this,ProductInfoActivity.class);
                 Bundle bundle=new Bundle();
                 bundle.putSerializable("pp",pp);
                 intent.putExtras(bundle);
                 startActivity(intent);
            }
        });

    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        },3000);
        seekList.loadComplete();

    }
    //访问网络搜索
    public void  getData(){
        RequestParams rp=new RequestParams(HttpUrlUtils.HTTP_URL+"seekServlet");
        rp.addBodyParameter("page",page+"");
        rp.addBodyParameter("seekContent",seekContent);
        x.http().post(rp, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("@@@@","result"+result);
                page++;
                Gson gson=new Gson();
                List<Product.Products> productList=new ArrayList<>();

                productList= gson.fromJson(result,new TypeToken<List<Product.Products>>(){}.getType());
                Log.e("@@@@","productList"+productList.toString());
               // product.clear();
                product.addAll(productList);
                if(adapter==null){
                    adapter=new MySeekAdapter();
                }
                else {
                    adapter.notifyDataSetChanged();
                }
                seekList.setAdapter(adapter);
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }
    public class MySeekAdapter extends  BaseAdapter{
        private ImageView productPhoto;
        private TextView productDetail;
        private TextView productKind;
        private TextView cityAddress;
        private TextView schoolAddress;
        private TextView productPrice;
        private TextView productNum;

        @Override
        public int getCount() {
            return product.size();
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
            view=View.inflate(SeekContentActivity.this,R.layout.eh_item,null);
            productPhoto = ((ImageView) view.findViewById(R.id.iv_project_photo));
            productDetail = ((TextView) view.findViewById(R.id.tv_project_detail));
            productKind = ((TextView) view.findViewById(R.id.tv_product_kind));
            cityAddress = ((TextView) view.findViewById(R.id.tv_eh_cityaddress));
            schoolAddress = ((TextView) view.findViewById(R.id.tv_eh_schooladdress));
            productPrice = ((TextView) view.findViewById(R.id.tv_project_price));
            productNum = ((TextView) view.findViewById(R.id.tv_product_number));
           Product.Products list=product.get(i);
            String[] imgurl=list.imgurl.split("=");
            x.image().bind(productPhoto,HttpUrlUtils.HTTP_URL+imgurl[0]);
            productDetail.setText(list.description);
            productKind.setText(list.category);
            cityAddress.setText(list.proaddress);
            schoolAddress.setText(list.schoolname);
            productPrice.setText(list.estoreprice+"");
            productNum.setText("总共"+list.pnum+"件");
            return view;}
    }

}
