package com.estore.activity;
/*
我的发布---发布的拍卖item
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.ListMyAuctionActivityBean;
import com.estore.pojo.MyPublishActivityBean;

import org.xutils.x;


public class PublishAuctionDetialItemActivity extends AppCompatActivity {
    private TextView tv_pubauctdetial_probegintime;
    private TextView tv_pubauctdetial_proendtime;
    private TextView tv_pubauctdetial_represent;
    private TextView tv_pubauctdetial_proname;
    private TextView tv_pubauctdetial_proprice;
    private TextView tv_pubauctdetial_pronumber;
    private ViewPager iv_pubauctdetial_propic;
    private ImageView iv_pubauctdetial_return;
    //图片
    private String[] photourl;
    private int[] id={R.id.iv_quan1, R.id.iv_quan2,R.id.iv_quan3};
    private int prePosition=0;
    public String imgurl;//图片
    private  static final String TAG="ProductInfoActivity" ;
    private Button modify;
    private Button deletepro;
    MyPublishActivityBean.ProImag pp;
    private BaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishauction_itemdetial);
        initView();
        initDate();
        initEvent();
    }

    private void initView() {
        //返回
        iv_pubauctdetial_return = ((ImageView) findViewById(R.id.iv_pubauctdetial_return));
        tv_pubauctdetial_represent = ((TextView) findViewById(R.id.tv_pubauctdetial_represent));
        iv_pubauctdetial_propic = ((ViewPager) findViewById(R.id.iv_pubauctdetial_propic));//描述
        tv_pubauctdetial_proname = ((TextView) findViewById(R.id.tv_pubauctdetial_proname));//名字
        tv_pubauctdetial_proprice = ((TextView) findViewById(R.id.tv_pubauctdetial_proprice));//价格
        tv_pubauctdetial_pronumber = ((TextView) findViewById(R.id.tv_pubauctdetial_pronumber));//个数
        tv_pubauctdetial_probegintime = ((TextView) findViewById(R.id.tv_pubauctdetial_probegintime));//开始时间

    }
    private void  initDate() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ListMyAuctionActivityBean.ProImag  bundlepro=(ListMyAuctionActivityBean.ProImag)bundle.getSerializable("pubimag");
        // x.image().bind(iv_pubauctdetial_propic, HttpUrlUtils.HTTP_URL +bundlepro.auct_imgurl);
        Log.e("pubimag",bundlepro.toString());
        tv_pubauctdetial_represent.setText(bundlepro.auct_description);//描述
        tv_pubauctdetial_proname.setText(bundlepro.auct_name);//名字
        tv_pubauctdetial_proprice.setText(bundlepro.auct_minprice+"");//价格
        tv_pubauctdetial_pronumber.setText(bundlepro.auct_pnum+"");//个数
        tv_pubauctdetial_probegintime.setText(bundlepro.auct_begin+"");//开始时间
//图片
        photourl=bundlepro.auct_imgurl.split("=");
        Log.i(TAG,photourl.toString());

        iv_pubauctdetial_propic.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=View.inflate(PublishAuctionDetialItemActivity.this,R.layout.vp_item,null);
                ImageView  iv_vp= ((ImageView) view.findViewById(R.id.iv_vp));
                x.image().bind(iv_vp, HttpUrlUtils.HTTP_URL+photourl[position]);
                container.addView(view);//容易忘记
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(((View) object));
            }

            @Override
            public int getCount() {
                return photourl.length;
            }
            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
        });
        iv_pubauctdetial_propic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                ((ImageView) findViewById(id[position])).setImageResource(R.drawable.point_red);
                ((ImageView) findViewById(id[prePosition])).setImageResource(R.drawable.point_gray);
                prePosition=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void  initEvent() {
        iv_pubauctdetial_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}


