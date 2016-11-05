package com.estore.activity;

import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/***
 * 引导页
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager vp;
    private ProgressBar pb_login;
    private TextView btn_tiaozhuan;
    SharedPreferences sp=sp=getSharedPreferences("User",MODE_APPEND);;

    private List<Integer> pictureLists=new ArrayList<Integer>();
    private int[] id={R.id.iv_flash1,R.id.iv_flash2,R.id.iv_flash3, R.id.iv_flash4};
    private int prePosition=0;//向导页默认位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(sp.getInt("isFirst",0)!=0){
            Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
        setContentView(R.layout.activity_image_pager);


        initView();
        ininData();
    }


    private void ininData() {
        pictureLists.add(0,R.drawable.start_i1);
        pictureLists.add(1,R.drawable.start_i2);
        pictureLists.add(2,R.drawable.start_i3);
        pictureLists.add(3,R.drawable.start_i4);
        //  String url = HttpUrlUtils.HTTP_URL+"getAllProducts?page=1";
/*
        Log.e("ViewPagerActivity",HttpUrlUtils.HTTP_URL+"getAllProducts?page=1");
         final  RequestParams  params = new RequestParams(url);*/
        ((TextView) findViewById(R.id.tv_liji)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putInt("isFirst",1).commit();
                Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_tiaozhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pb_login.setVisibility(View.VISIBLE);
                sp.edit().putInt("isFirst",1).commit();
                Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
                startActivity(intent);
               /* x.http().get(params, new Callback.CacheCallback<String>() {

                    @Override
                    public void onSuccess(String result) {

                            Intent intent = new Intent(ViewPagerActivity.this, MainActivity.class);
                            intent.putExtra("json",result);
                            startActivity(intent);
                            pb_login.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                        Toast.makeText(ViewPagerActivity.this,"网络超时，请重新登录",Toast.LENGTH_SHORT).show();
                        pb_login.setVisibility(View.GONE);
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
                });*/
            }
        });



        //设置ViewPager的切换事件

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                ((ImageView) findViewById(id[position])).setImageResource(R.drawable.point_red);
                ((ImageView) findViewById(id[prePosition])).setImageResource(R.drawable.point_gray);
                prePosition=position;
                ((TextView) findViewById(R.id.btn_tiaoguo)).setVisibility(View.VISIBLE);
                if(position==id.length-1){
                    //如果是最后一页，将按钮设置显示

                  ((TextView) findViewById(R.id.tv_liji)).setVisibility(View.VISIBLE);
                }
                else{
                    //((TextView) findViewById(R.id.btn_tiaoguo)).setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //设置适配器
        vp.setAdapter(new MyPagerAdapter(pictureLists));
    }
    private void initView(){
        vp = ((ViewPager) this.findViewById(R.id.vp));
        btn_tiaozhuan= ((TextView) findViewById(R.id.btn_tiaoguo));
        //pb_login=(ProgressBar)findViewById(R.id.pb_login);
    }

    public class MyPagerAdapter extends PagerAdapter{
        private List<Integer> pictureLists;
        public MyPagerAdapter(List<Integer> pictureLists){
            this.pictureLists=pictureLists;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(((View) object));
            //super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=View.inflate(ViewPagerActivity.this,R.layout.vp_item,null);
            ImageView  iv_vp= ((ImageView) view.findViewById(R.id.iv_vp));
            iv_vp.setImageResource(pictureLists.get(position));
            container.addView(view);//容易忘记
            //return super.instantiateItem(container, position);
            return view;
        }

        @Override
        public int getCount() {
            return pictureLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

    }
}
