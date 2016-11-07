package com.estore.activity;
/*
我的发布---发布的二货item
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
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.MyPublishActivityBean;
import com.estore.view.LoadListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class PublishEstoreDetialItemActivity extends AppCompatActivity{
    private TextView tv_erdetial_probegintime;
    private TextView tv_erdetial_represent;
    private TextView tv_erdetial_proname;
    private TextView tv_erdetial_proprice;
    private TextView tv_erdetial_procategory;
    private TextView tv_erdetial_pronumber;
    private ImageView iv_pubEstitem_return;
    private TextView tv_pubestorewhere;
    private TextView   tv_time;
    private TextView  tv_erdetial_postmoney;
    private TextView tv_pubestorewhere1;
    private String[] photourl;
    private int[] id={R.id.iv_quan1, R.id.iv_quan2,R.id.iv_quan3};
    private int prePosition=0;
    public String imgurl;//图片
    private  static final String TAG="ProductInfoActivity" ;
    private ViewPager iv_erdetial_propic;
    private Button modify;
    private Button deletepro;
    MyPublishActivityBean.ProImag pp;
    private BaseAdapter adapter;
    final List<MyPublishActivityBean.ProImag> prolist=new ArrayList<MyPublishActivityBean.ProImag>();
    MyPublishActivityBean.ProImag  bundlepro=new MyPublishActivityBean.ProImag();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        pp= (MyPublishActivityBean.ProImag) bundle.getSerializable("estoreimage");
        setContentView(R.layout.activity_publishestore_itemdetial);
        initView();
        initDate();
        initEvent();

    }




    private void initView() {
        //返回
        iv_pubEstitem_return= ((ImageView) findViewById(R.id.iv_pubEstitem_return));
        tv_erdetial_pronumber = ((TextView) findViewById(R.id.tv_erdetial_pronumber));//个数
        tv_erdetial_proprice = ((TextView) findViewById(R.id.tv_erdetial_proprice));//价格
        tv_erdetial_represent = ((TextView) findViewById(R.id.tv_erdetial_represent));//描述
        tv_pubestorewhere = ((TextView) findViewById(R.id.tv_pubestorewhere));//同城
        tv_erdetial_proname = ((TextView) findViewById(R.id.tv_erdetial_proname));//名字
        tv_time = ((TextView) findViewById(R.id.tv_ehtime));//时间
        tv_erdetial_postmoney = ((TextView) findViewById(R.id.tv_erdetial_postmoney));
        iv_erdetial_propic = ((ViewPager) findViewById(R.id.iv_erdetial_propic));
        modify = ((Button) findViewById(R.id.btn_modifypro));//修改
        deletepro = ((Button) findViewById(R.id.btn_deletepro));//删除
    }
    private void initDate() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
         bundlepro=(MyPublishActivityBean.ProImag)bundle.getSerializable("estoreimage");
        Log.e("estoreimage",bundlepro.toString());
        Log.e("estoreimage",bundlepro.toString());
        tv_erdetial_represent.setText(bundlepro.description);//描述
        tv_erdetial_proname.setText(bundlepro.name);//名字
        tv_erdetial_pronumber.setText(bundlepro.pnum+"");
        tv_erdetial_proprice.setText(bundlepro.estoreprice+"");//价格
        Log.i("youfei", bundlepro.getYoufei()+"");
        if(bundlepro.getYoufei()==0){
            tv_erdetial_postmoney.setText("包邮");//价格
        }else {
            tv_erdetial_postmoney.setText(bundlepro.youfei+"");
        }
        tv_time.setText(bundlepro.time+"");
//图片
        photourl=bundlepro.imgurl.split("=");
        Log.i(TAG,photourl.toString());
        iv_erdetial_propic.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=View.inflate(PublishEstoreDetialItemActivity.this,R.layout.vp_item,null);
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
        iv_erdetial_propic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        iv_pubEstitem_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        //修改
        modify.setOnClickListener(new View.OnClickListener() {
          //  private MyPublishActivityBean.ProImag list=new MyPublishActivityBean.ProImag();
            @Override
            public void onClick(View view) {
                //MyPublishActivityBean.ProImag list=prolist.get(position);
              //  Log.i("cc","list"+list);
                Intent intent=new Intent(PublishEstoreDetialItemActivity.this, ModifyMyAddProductActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("list",bundlepro);
                Log.e("@@@@@",bundlepro.toString()+"");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//删除
        deletepro.setOnClickListener(new View.OnClickListener() {
            //private MyPublishActivityBean.ProImag list=new MyPublishActivityBean.ProImag();
            @Override
            public void onClick(View view) {
                String url=HttpUrlUtils.HTTP_URL+"deleteMyAddProductServlet";
                int productId=bundlepro.id;
                Log.e("@@@@@",productId+"");
                RequestParams requestParams=new RequestParams(url);
                requestParams.addQueryStringParameter("productId",productId+"");
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(PublishEstoreDetialItemActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                       // Toast.makeText(PublishEstoreDetialItemActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


            }
        });
    }
}


