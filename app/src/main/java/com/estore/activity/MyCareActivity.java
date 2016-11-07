package com.estore.activity;
/*
我的关注页面
 */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.CollectionInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyCareActivity extends AppCompatActivity {
    private ImageView iv_carereturn;
    private int userId;
    List<CollectionInfo> careInfoList=new ArrayList<>();
    List<CollectionInfo> careInfos=new ArrayList<>();
    private BaseAdapter myAdapter;
    private ListView lv_acutionitem;
    CollectionInfo careInfo=new CollectionInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_care);
        SharedPreferences sp=getSharedPreferences("User",MODE_APPEND);
        userId=sp.getInt("userId",0);
        getAllCareInfo();
        //返回我的关注
        iv_carereturn = ((ImageView) findViewById(R.id.iv_carereturn));
        lv_acutionitem = ((ListView) findViewById(R.id.lv_acutionitem));
        iv_carereturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    public class MyAdapter extends BaseAdapter{

        private ImageView cart_item_prod_img;
        private TextView cart_item_prod_decription;
        private TextView cart_item_prod_brgin;
        private TextView cart_item_prod_end;
        private TextView cart_item_prod_minprice;
        private TextView cart_item_prod_time;

        @Override
        public int getCount() {
            return careInfoList.size();
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
            view=View.inflate(getApplicationContext(),R.layout.collection_item,null);
            cart_item_prod_img = ((ImageView) view.findViewById(R.id.cart_item_prod_img));
            cart_item_prod_decription = ((TextView) view.findViewById(R.id.cart_item_prod_decription));
            cart_item_prod_brgin = ((TextView) view.findViewById(R.id.cart_item_prod_brgin));
            cart_item_prod_end = ((TextView) view.findViewById(R.id.cart_item_prod_end));
            cart_item_prod_minprice = ((TextView) view.findViewById(R.id.cart_item_prod_minprice));
            cart_item_prod_time = ((TextView) view.findViewById(R.id.cart_item_prod_time));
            CollectionInfo careInfo=careInfoList.get(i);

            String[] img=careInfo.getImgurl().split("=");
            x.image().bind(cart_item_prod_img,HttpUrlUtils.HTTP_URL+img[0]);
            cart_item_prod_decription.setText(careInfo.getDecription());
            cart_item_prod_brgin.setText("开始时间："+careInfo.getBeginTime());
            cart_item_prod_end.setText("结束时间："+careInfo.getEndTime());
            cart_item_prod_minprice.setText("最低价格："+careInfo.getMinPrice()+"¥");
            cart_item_prod_time.setText("历时"+careInfo.getTime()+"分钟");
            return view;
        }
    }

    private void getAllCareInfo() {
        String url= HttpUrlUtils.HTTP_URL+"getAllCareInfoServlet";
        Log.i("cc",url);
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("userId",userId+"");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Log.i("cc", "getAllCareInfoServlet==onSuccess: "+result);
                careInfos= gson.fromJson(result, new TypeToken<List<CollectionInfo>>(){}.getType());
                Log.i("cc", "careInfo=======onSuccess: "+careInfos);
                careInfoList.addAll(careInfos);
                if(myAdapter==null){
                    myAdapter=new MyAdapter();
                }else{
                    myAdapter.notifyDataSetChanged();
                }

                lv_acutionitem.setAdapter(myAdapter);
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