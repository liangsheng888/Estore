package com.estore.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;
import com.estore.view.LoadListView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/30.
 */
public class WatchActivity extends Activity implements LoadListView.ILoadListener {

    private static final int HOME =5 ;
    private LoadListView lv_jingpin;
    private MyAdapter adapter = new MyAdapter();
    List<ImageView> images = null;
    private String[] imgurls;
    private GridView gv_jingpin;
    private int page = 1;
    private int orderFlag;
    private ArrayList<Product.Products> proList = new ArrayList<>();
    private ImageView iv_watch_fanhui;
    private int duration=1000;
    private Animation push_left_in,push_right_in;
    private Animation slide_top_to_bottom,slide_bottom_to_top;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        iv_watch_fanhui = (ImageView) findViewById(R.id.iv_watch_fanhui);
        iv_watch_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                intent.putExtra("direct",0);
                setResult(HOME,intent);
                finish();

            }
        });
        Intent intent = getIntent();
        orderFlag = intent.getIntExtra("orderFlag", -1);
        lv_jingpin = (LoadListView) findViewById(R.id.lv_computer);
        lv_jingpin.setInterface(WatchActivity.this);
        lv_jingpin.setLayoutAnimation(getAnimationController());
        getData();


        lv_jingpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product.Products pp = proList.get(position);
                Intent intent = new Intent(getApplicationContext(), ProductInfoActivity.class);
                // intent.putExtra("pp",pp);
                Bundle bundle = new Bundle();
                bundle.putSerializable("pp", pp);
                intent.putExtras(bundle);
                Log.e("FragmentHome", pp.toString());
                startActivity(intent);
            }
        });

    }
    /*public ListViewAdapter(ArrayList<Product.Products> list) {
        this.list = list;
        push_left_in=AnimationUtils.loadAnimation(context, R.anim.push_left_in);
        push_right_in=AnimationUtils.loadAnimation(context, R.anim.push_right_in);
       *//* slide_top_to_bottom= AnimationUtils.loadAnimation(context, R.anim.slide_top_to_bottom);
        slide_bottom_to_top=AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_top);*//*
    }*/
    protected LayoutAnimationController getAnimationController() {
        int duration=300;
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(duration);
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }
    @Override
    public void onLoad() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //获取更多数据

                getData();
//                //更新listview显示；
//                showListView(apk_list);
//                //通知listview加载完毕
                lv_jingpin.loadComplete();
            }
        }, 2000);

    }


    public class MyAdapter extends BaseAdapter {
        private ImageView iv;

        @Override
        public int getCount() {
            return proList.size();
        }

        @Override
        public Object getItem(int position) {
            return proList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(WatchActivity.this, R.layout.eh_item, null);
          /*  if (position % 2 == 0) {
                push_left_in.setDuration(duration);
                convertView.setAnimation(push_left_in);
            } else {
                push_right_in.setDuration(duration);
                convertView.setAnimation(push_right_in);
            }*/
                ImageView productPhoto = ((ImageView) view.findViewById(R.id.iv_project_photo));
                TextView productDetail = ((TextView) view.findViewById(R.id.tv_project_detail));
                TextView productKind = ((TextView) view.findViewById(R.id.tv_product_kind));
                TextView cityAddress = ((TextView) view.findViewById(R.id.tv_eh_cityaddress));
                TextView schoolAddress = ((TextView) view.findViewById(R.id.tv_eh_schooladdress));
                TextView productPrice = ((TextView) view.findViewById(R.id.tv_project_price));
                TextView productNum = ((TextView) view.findViewById(R.id.tv_product_number));
                Product.Products list = proList.get(position);
                String[] imgurl = list.imgurl.split("=");
                x.image().bind(productPhoto, HttpUrlUtils.HTTP_URL + imgurl[0]);
                productDetail.setText(list.description);
                productKind.setText(list.category);
                cityAddress.setText(list.proaddress);
                schoolAddress.setText(list.schoolname);
                productPrice.setText(list.estoreprice + "");
                productNum.setText("总共" + list.pnum + "件");
                return view;}


        }


    /*public class Adapter extends BaseAdapter {
        private String[] imgurls;

        public Adapter(String[] imgurls) {
            this.imgurls = imgurls;
        }


        @Override
        public int getCount() {
            return imgurls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.layout_fra_pro_item, null);
            }
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_pro);
            x.image().bind(iv, HttpUrlUtils.HTTP_URL + imgurls[position]);
            return convertView;
        }

    }*/

    public void getData() {
        String url = HttpUrlUtils.HTTP_URL + "getComputerServlet?page=" + page;
        RequestParams requestParams2 = new RequestParams(url);
        requestParams2.addQueryStringParameter("orderFlag", orderFlag + "");
        x.http().get(requestParams2, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                page++;

                Gson gson = new Gson();
                Product pro = gson.fromJson(result, Product.class);
                proList.clear();
                proList.addAll(pro.list);
                if (adapter == null) {
                    adapter = new MyAdapter();
                } else {
                    adapter.notifyDataSetChanged();
                }
                lv_jingpin.setAdapter(adapter);

                Log.i("cc", proList + "");


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
