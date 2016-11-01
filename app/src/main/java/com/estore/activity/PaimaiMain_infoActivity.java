package com.estore.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.Service.PaiMaiTiXingService;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.AuctListActivityBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PaimaiMain_infoActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp_zuct_info_ad;
    private ListView lv_auct_name_liuyan;
    private TextView tv_beginprice;
    private TextView tv_auct_name;
    private TextView tv_auct_username;
    private TextView tv_auct_time;
    private CheckBox btn_paimai_bidding;
    private String[] imgurls;
    private CheckBox btn_paimai_shoucang;
    Boolean shoucangFlag = false;
    AuctListActivityBean.Auct auct;
    private CheckBox btn_paimai_tixing;
//        private MyBinder minder;
PaiMaiTiXingService paiMaiTiXingService;
    PaiMaiTiXingService.MyBinder myBinder;
    MyConn myConn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_paimai_main_info);
        initView();
        intEven();

        Intent intent = getIntent();
        auct = (AuctListActivityBean.Auct) intent.getSerializableExtra("auct");

//        String flag=intent.getIntExtra()
        getCollectData();
        System.out.println("(intent.getExtras()----------------------" + auct);
        imgurls = auct.auct_imgurl.split("=");//将拿到的图片路径分割成字符串数组

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.back);//设置导航栏图标
        toolbar.setLogo(R.drawable.emoji_81);//设置app logo
        toolbar.setTitle("拍卖");//设置主标题
        toolbar.setSubtitle("正在拍卖物品");//设置子标题

        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_item1) {
                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_01", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item2) {
                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_02", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item3) {
                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_03", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_item4) {
                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_04", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
        vp_zuct_info_ad = ((ViewPager) findViewById(R.id.vp_zuct_info_ad));

//        List<Integer> imgsrc = new ArrayList<Integer>();
//        imgsrc.add(0, R.drawable.add1);
//        imgsrc.add(1, R.drawable.add1);
//        imgsrc.add(2, R.drawable.add);
//        imgsrc.add(3, R.drawable.add);
//        for (int i=0; i<imgurls.length;i++){
//            imgsrc= imgsrc.get(i);
//        }
        MyPageAdapter pageAdapter = new MyPageAdapter();
        vp_zuct_info_ad.setAdapter(pageAdapter);

        vp_zuct_info_ad.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
//        lv_auct_name_liuyan = ((ListView) findViewById(R.id.lv_auct_name_liuyan));

        //获得从拍卖listview得到的数据

        tv_beginprice = ((TextView) findViewById(R.id.tv_beginprice));
        tv_auct_name = ((TextView) findViewById(R.id.tv_auct_name));
        tv_auct_username = ((TextView) findViewById(R.id.tv_auct_username));
        tv_auct_time = ((TextView) findViewById(R.id.tv_auct_time));

        tv_beginprice.setText(auct.auct_minprice + "¥");
        tv_auct_name.setText("标题：炫酷高端大气奢华有内涵的装逼神器" + auct.auct_name + "手机");
        tv_auct_username.setText("拍卖人：" + auct.auct_id);
        //判断拍卖时间
        String j = (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        Long a = Long.parseLong(j);
        System.out.println(j + "--------------时间--------------------" + a);
        String yy = auct.auct_begin.substring(0, 4);
        String month = auct.auct_begin.substring(5, 7);
        String day = auct.auct_begin.substring(8, 10);
        String hh = auct.auct_begin.substring(11, 13);
        String ss = auct.auct_begin.substring(14, 16);
        String mm = auct.auct_begin.substring(17, 19);
        String b = yy + month + day + hh + ss + mm;
        Long bb = Long.parseLong(b);


        if (bb - a < 0) {
            tv_auct_time.setText("未开始敬请期待");
        } else {
//            tv_auct_time.setText(dd + ":" + hh + ":" + ":" + ss);
            tv_auct_time.setText("正在进行中");
        }

    }

    private void intEven() {
        btn_paimai_bidding.setOnClickListener(this);
        btn_paimai_shoucang.setOnClickListener(this);
        btn_paimai_tixing.setOnClickListener(this);
    }

    private void initView() {
        btn_paimai_bidding = ((CheckBox) findViewById(R.id.btn_paimai_bidding));
        btn_paimai_shoucang = ((CheckBox) findViewById(R.id.btn_paimai_shoucang));
        btn_paimai_tixing = ((CheckBox) findViewById(R.id.btn_paimai_tixing));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_paimai_bidding:
                Intent intent = new Intent(PaimaiMain_infoActivity.this, PaiMaiMain_bidding.class);
                startActivity(intent);
                break;
            case R.id.btn_paimai_shoucang:
                if (btn_paimai_shoucang.isChecked()) {
                    shoucangFlag = true;
                } else {
                    shoucangFlag = false;
                }
                sendFlagForShoucang();
                break;
            case R.id.btn_paimai_tixing:
                if (btn_paimai_tixing.isChecked()) {
                    myConn=new MyConn();
                    intent = new Intent(this,PaiMaiTiXingService.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("PaiMaiService",auct.auct_begin);
                    intent.putExtras(bundle);
//                    startService(intent);

                    RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"tuisong");
                    x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {

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
                    bindService(intent,myConn,BIND_AUTO_CREATE);

                } else {

                }

                break;
        }
    }

    public class  MyConn implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder= (PaiMaiTiXingService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
    public class sedPaiMaiBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private void sendFlagForShoucang() {
        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "paiMaishoucang");
        requestParams.addBodyParameter("shoucangFlag", String.valueOf(shoucangFlag));
        requestParams.addBodyParameter("auct_id", auct.auct_id);
        requestParams.addBodyParameter("user_id", auct.user_id);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("true")) {
                    Toast.makeText(PaimaiMain_infoActivity.this, "收藏成功" + result, Toast.LENGTH_SHORT).show();
                    System.out.println("收藏成功" + result);
                } else {
                    Toast.makeText(PaimaiMain_infoActivity.this, "取消收藏成功" + result, Toast.LENGTH_SHORT).show();
                    System.out.println("取消收藏成功" + result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("收藏错误" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public void getCollectData() {

        RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "getShouCangData");
        requestParams.addBodyParameter("userId", auct.user_id);
        requestParams.addBodyParameter("auctId", auct.auct_id);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(String result) {
                if (result.equals(true)) {
                    btn_paimai_shoucang.setChecked(true);
//                    btn_paimai_shoucang.setTextColor(getColor(R.color.red));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("查询商品是否被收藏" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private class MyPageAdapter extends PagerAdapter {

//        List<Integer> imgsrc;
//
//        public MyPageAdapter(List<Integer> imgsrc) {
//            this.imgsrc = imgsrc;
//        }

        @Override
        public int getCount() {
            return imgurls.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);   //要删掉

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getApplicationContext(), R.layout.auct_info_daa_item, null);
            ImageView iv_vp_item = ((ImageView) view.findViewById(R.id.iv_auct_info_add_item));
            x.image().bind(iv_vp_item, HttpUrlUtils.HTTP_URL + imgurls[position]);
//            iv_vp_item.setImageResource(imgsrc.get(position));
            container.addView(view);//肯定要的

            return view;
        }
    }


}

