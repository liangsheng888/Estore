package com.estore.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.Product;

import org.xutils.x;

/***
 * 商品详情页
 */
public class ProductInfoActivity extends AppCompatActivity {
    private  static final String TAG="ProductInfoActivity" ;
    private ImageView iv_project_detail_back;
    private ViewPager vp_show_photo;
    private TextView  tv_product_detail;
    private TextView  tv_project_detail_price;
    private Button    btn_touch_seller;
    private CheckBox  cb_guanzhu;
    private Button    btn_addcart;
    private Button    btn_buy_now;
    private String[] photourl;
    private Product.Products pp;
    private int prePosition=0;
    private int[] id={R.id.iv_quan1,R.id.iv_quan2,R.id.iv_quan3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        initView();
        initData();


    }

    private void initData() {
        pp= getProDetailInfo();//
        photourl=pp.imgurl.split("=");//
        Log.e(TAG,photourl[0]);
        tv_project_detail_price.setText(pp.estoreprice+"");
        tv_product_detail.setText(pp.description);
        vp_show_photo.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=View.inflate(ProductInfoActivity.this,R.layout.vp_item,null);
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
        vp_show_photo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    private void initView() {
        iv_project_detail_back=(ImageView) this.findViewById(R.id.iv_project_detail_back);
        vp_show_photo=(ViewPager) this.findViewById(R.id.vp_show_photo);
        tv_product_detail=(TextView) this.findViewById(R.id.tv_product_detail);
        tv_project_detail_price=(TextView) this.findViewById(R.id.tv_project_detail_price);
        btn_touch_seller=(Button) this.findViewById(R.id.btn_touch_seller);
        cb_guanzhu=(CheckBox) this.findViewById(R.id.cb_guanzhu);
        btn_addcart=(Button) this.findViewById(R.id.btn_addcart);
        btn_buy_now=(Button) this.findViewById(R.id.btn_buy_now);
    }

    //接收上个界面传递的商品信息
    public  Product.Products getProDetailInfo() {
        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();
        Product.Products pp=(Product.Products)bundle.getSerializable("pp");
        Log.e(TAG,pp.toString());
        return pp;
    }
}
