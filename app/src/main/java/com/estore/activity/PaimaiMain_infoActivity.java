package com.estore.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.AuctListActivityBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import bean.Product;


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
    List<Product> productlist = new ArrayList<Product>();
    //        private MyBinder minder;
/*PaiMaiTiXingService paiMaiTiXingService;
    PaiMaiTiXingService.MyBinder myBinder;*/
    MyConn myConn;
    private ListView lv_paimai_jilu;
    private TextView tv_paimai_jilu;
    Integer paiMaiChangCiFlag;
    String sdftimestr;
    private Lock lock = new ReentrantLock();
    Long daojishi;
    private TextView HH;
    private TextView MM;
    private TextView ss;
    long timeMM = 60;
    long timeHH = 3;
    long timess = 60;
    private TextView tv_info_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_paimai_main_info);
        initView();
        intEven();

        Intent intent = getIntent();
        auct = (AuctListActivityBean.Auct) intent.getSerializableExtra("auct");
        paiMaiChangCiFlag = intent.getIntExtra("flag", 0);

        getPaiMaiJuluData();
//        String flag=intent.getIntExtra()
        getCollectData();
        System.out.println("(intent.getExtras()----------------------" + auct);
        imgurls = auct.auct_imgurl.split("=");//将拿到的图片路径分割成字符串数组

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        toolbar.setNavigationIcon(R.drawable.back);//设置导航栏图标
//        toolbar.setLogo(R.drawable.emoji_81);//设置app logo
//        toolbar.setTitle("拍卖");//设置主标题
//        toolbar.setSubtitle("正在拍卖物品");//设置子标题
//
//        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int menuItemId = item.getItemId();
//                if (menuItemId == R.id.action_item1) {
//                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_01", Toast.LENGTH_SHORT).show();
//
//                } else if (menuItemId == R.id.action_item2) {
//                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_02", Toast.LENGTH_SHORT).show();
//
//                } else if (menuItemId == R.id.action_item3) {
//                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_03", Toast.LENGTH_SHORT).show();
//
//                } else if (menuItemId == R.id.action_item4) {
//                    Toast.makeText(PaimaiMain_infoActivity.this, "R.string.item_04", Toast.LENGTH_SHORT).show();
//
//                }
//                return true;
//            }
//        });
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
//        tv_auct_username = ((TextView) findViewById(R.id.tv_auct_username));
        tv_auct_time = ((TextView) findViewById(R.id.tv_auct_time));

        tv_beginprice.setText(auct.auct_minprice + "¥");
        tv_auct_name.setText("标题：炫酷高端大气奢华有内涵的装逼神器" + auct.auct_name + "手机");
//        tv_auct_username.setText("拍卖人：" + auct.auct_id);
        //判断拍卖时间


        if (paiMaiChangCiFlag == 8) {
            sdftimestr = new SimpleDateFormat("yyyyMMdd" + "08" + "0000").format(new Date());


        } else if (paiMaiChangCiFlag == 12) {
            sdftimestr = new SimpleDateFormat("yyyyMMdd" + "12" + "0000").format(new Date());

        } else if (paiMaiChangCiFlag == 16) {
            sdftimestr = new SimpleDateFormat("yyyyMMdd" + "16" + "0000").format(new Date());

        } else if (paiMaiChangCiFlag == 20) {
            sdftimestr = new SimpleDateFormat("yyyyMMdd" + "20" + "0000").format(new Date());

        }
        Long nowtime = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        Long beginTime = Long.parseLong(sdftimestr);
        System.out.println("beginTime====" + beginTime + "nowtime==" + nowtime);
        Long a = null;
        Long b = null;
        try {
            a = new SimpleDateFormat("yyyyMMddHHmmss").parse(String.valueOf(nowtime)).getTime();
            b = new SimpleDateFormat("yyyyMMddHHmmss").parse(sdftimestr).getTime();
            System.out.println(a - b + "---------a-b" + a + "---b====" + b);
            daojishi = 4 * 60 * 60 * 1000 - (a - b);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (nowtime - beginTime > 0) {
            if (a - b > 1000 * 60 * 60 * 4) {
                tv_auct_time.setText("拍卖已结束");
                ss.setVisibility(View.GONE);
                MM.setVisibility(View.GONE);
                HH.setVisibility(View.GONE);
                btn_paimai_bidding.setClickable(false);
                return;
            }
            tv_auct_time.setText("剩余时间：");
            getTime();
        } else {
            tv_auct_time.setText("尚未开始敬请期待！");
            ss.setVisibility(View.GONE);
            MM.setVisibility(View.GONE);
            HH.setVisibility(View.GONE);
            btn_paimai_bidding.setClickable(false);
        }

    }

    public void getTime() {
        timeHH = daojishi / 1000 / 60 / 60;
        timeMM = daojishi / 1000 / 60 - timeHH * 60;
        timess = daojishi / 1000 - timeHH * 60 * 60 - timeMM * 60;
        ss.setText(":" + timess + "秒");
        MM.setText(":" + timeMM + "分钟");
        HH.setText("" + timeHH + "小时");
        runnable.run();

        System.out.println("倒计时" + daojishi + "timeHH   " + timeHH + "  timeMM  " + timeMM + "  timess  " + timess);
    }

    Handler handler = new Handler();
//    timeHH=daojishi/1000/60/60;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            lock.lock();
            timess--;
            System.out.println("");
            ss.setText(":" + timess + "秒");
            if (timess == 0) {
                timess = 60;
                lock.unlock();
                System.out.println("分钟倒计时线程开始");
                runnable1.run();
            }
            handler.postDelayed(this, 1000);
        }
    };
    Runnable runnable1 = new Runnable() {
        @Override
        public void run() {
            lock.lock();
            timeMM--;
            MM.setText(":" + timeMM + "分钟");
            if (timeMM == 0) {
                timeMM = 60;
                lock.unlock();
                System.out.println("xiaoshi 倒计时线程开始");
                runnable2.run();
            }
        }
    };
    Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
            lock.lock();
//            timeHH=daojishi%=(1000/60/60);
            if (timeHH != 0) {
                timeHH--;
            }
            HH.setText("" + timeHH + "小时");
            lock.unlock();
            System.out.println("maio 倒计时线程开始");
            runnable1.run();
            return;
        }

    };


//        String j = (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//        Long a = Long.parseLong(j);

//        String yy = auct.auct_begin.substring(0, 4);
//        String month = auct.auct_begin.substring(5, 7);
//        String day = auct.auct_begin.substring(8, 10);
//        String hh = auct.auct_begin.substring(11, 13);
//        String ss = auct.auct_begin.substring(14, 16);
//        String mm = auct.auct_begin.substring(17, 19);
//        System.out.println(auct.auct_begin + "==" + yy + "--" + month + "--" + day + "--" + hh + "--" + ss + "--" + mm);
//        String b = yy + month + day + hh + ss + mm;
//        Long bb = Long.parseLong(b);
//        Long nowtime = Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//        Long nextTime = nowtime - bb;
//        System.out.println("时间bb---" + bb + "----a---" + a);
//        if (bb - a < 0) {
//            tv_auct_time.setText("未开始敬请期待");
//        } else {
////            tv_auct_time.setText(dd + ":" + hh + ":" + ":" + ss);
//            tv_auct_time.setText("正在进行中");
//        }


    private void intEven() {
        btn_paimai_bidding.setOnClickListener(this);
        btn_paimai_shoucang.setOnClickListener(this);
        btn_paimai_tixing.setOnClickListener(this);
        tv_paimai_jilu.setOnClickListener(this);
        tv_info_back.setOnClickListener(this);
    }

    private void initView() {
        btn_paimai_bidding = ((CheckBox) findViewById(R.id.btn_paimai_bidding));
        btn_paimai_shoucang = ((CheckBox) findViewById(R.id.btn_paimai_shoucang));
        btn_paimai_tixing = ((CheckBox) findViewById(R.id.btn_paimai_tixing));
        lv_paimai_jilu = ((ListView) findViewById(R.id.lv_paimai_jilu));
        tv_paimai_jilu = ((TextView) findViewById(R.id.tv_paimai_jilu));
        HH = ((TextView) findViewById(R.id.txttime_HH));
        MM = ((TextView) findViewById(R.id.txttime_MM));
        ss = ((TextView) findViewById(R.id.txttime_ss));
        tv_info_back = ((TextView) findViewById(R.id.tv_info_back));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_paimai_bidding:
                Intent intent = new Intent(PaimaiMain_infoActivity.this, PaiMaiMain_bidding.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("auct", auct);
                bundle.putInt("paiMaiChangCiFlag", paiMaiChangCiFlag);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_paimai_jilu:
                intent = new Intent(getApplicationContext(), PaiMaiJiluActivity.class);
                bundle = new Bundle();
                bundle.putSerializable("auctjilu", auct);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_info_back:
                intent = new Intent(getApplicationContext(),  PaimaiMainActivity.class);
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
                    myConn = new MyConn();
//                    intent = new Intent(this,PaiMaiTiXingService.class);
//                    bundle=new Bundle();
//                    bundle.putSerializable("PaiMaiService",auct.auct_begin);
//                    intent.putExtras(bundle);
////                    startService(intent);*/

                    RequestParams requestParams = new RequestParams(HttpUrlUtils.HTTP_URL + "tuisong");
                    x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            System.out.println("提醒成功" + result);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            System.out.println("提醒错误：" + ex.getMessage());
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });
//                    bindService(intent,myConn,BIND_AUTO_CREATE);

                } else {

                }

                break;
        }
    }


    public class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           /* myBinder= (PaiMaiTiXingService.MyBinder) service;*/
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

    private class ListAdapter extends BaseAdapter {
        private TextView tv_auct_time;
        private TextView tv_bidd_price;
        private TextView tv_paimai_name;
        private TextView tv_paimai_julu_order;
//        private TextView tv_nowprice;

        @Override
        public int getCount() {

            return productlist.size() >= 3 ? 3 : productlist.size();
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
            convertView = View.inflate(PaimaiMain_infoActivity.this, R.layout.paimai_jillu_item, null);
            tv_paimai_name = ((TextView) convertView.findViewById(R.id.tv_paimai_name));
            tv_bidd_price = ((TextView) convertView.findViewById(R.id.tv_bidd_price));
            tv_auct_time = ((TextView) convertView.findViewById(R.id.tv_bid_time));
            tv_paimai_julu_order = ((TextView) convertView.findViewById(R.id.tv_paimai_julu_order));
//             tv_nowprice = ((TextView) convertView.findViewById(R.id.tv_nowprice));
            Product product = productlist.get(position);
            System.out.println("product" + product + "======productlist" + productlist);
            tv_paimai_name.setText(product.getAuct_name());
            tv_bidd_price.setText(product.getAuct_bid_price() + "");
            String bidTime = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(product.getAuct_end());
            tv_auct_time.setText(bidTime + "");

            tv_paimai_julu_order.setText("第" + (position + 1) + "名");

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
                System.out.println("获得传过来的数据productlist" + productlist);
                System.out.println("productlist.size()" + productlist.size());
                tv_paimai_jilu.setText(productlist.size() + "条");
                ListAdapter listAdapter = new ListAdapter();
                lv_paimai_jilu.setAdapter(listAdapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
      Intent  intent = new Intent(getApplicationContext(),  PaimaiMainActivity.class);
        startActivity(intent);
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

