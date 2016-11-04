package com.estore.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.AuctListActivityBean;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PaiMAIbidding_bidActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView HH;
    private TextView MM;
    private TextView ss;
    Integer timeMM = 60;
    Integer timeHH = 3;
    Integer timess = 60;
    Integer nowPrice=0;
    private String[] imgurls;
    private String[] jiaArray = new String[]{"1", "2", "5", "10",
            "20", "50", "100", "1000"};

    private Lock lock = new ReentrantLock();
    AuctListActivityBean.Auct auct;
    ArrayAdapter<String> sp_jiajiaAdaper = null;
    private RelativeLayout rl_jiajia;
    private TextView jiajia;
    private ViewPager vp_auct_bidding1;
    private TextView textView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pai_maibidding_bid);
        Intent intent = getIntent();
        auct = (AuctListActivityBean.Auct) intent.getSerializableExtra("auct_bid");
        imgurls = auct.auct_imgurl.split("=");//将拿到的图片路径分割成字符串数组
        System.out.println("imgurls"+imgurls);
        ininView();
        ininDate();
        ininEven();
        runnable.run();

        System.out.println("加价页面接受传值auct"+auct);


    }


    private void ininDate() {
     MyPageAdapter pageAdapter = new MyPageAdapter();
        vp_auct_bidding1.setAdapter(pageAdapter);
    }

    private void ininEven() {
        rl_jiajia.setOnClickListener(this);
        textView4.setOnClickListener(this);
    }

    private void ininView() {
        textView4 = ((TextView) findViewById(R.id.textView4));
        HH = ((TextView) findViewById(R.id.txttime_HH));
        MM = ((TextView) findViewById(R.id.txttime_MM));
        ss = ((TextView) findViewById(R.id.txttime_ss));
        jiajia = ((TextView) findViewById(R.id.tv_jiajia2));
        rl_jiajia = ((RelativeLayout) findViewById(R.id.rl_jiajia));
        vp_auct_bidding1 = ((ViewPager) findViewById(R.id.vp_auct_bidding1));

    }

    Handler handler = new Handler();

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
            timeHH--;
            HH.setText("" + timeHH + "小时");
            lock.unlock();
            System.out.println("maio 倒计时线程开始");
            runnable1.run();
            return;

        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_jiajia:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请选择加价");

                //builder.setMessage("你确定？");
                builder.setIcon(R.drawable.emoji_81);
                builder.setSingleChoiceItems(jiaArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        jiajia.setText(jiaArray[position]+"￥");
                        nowPrice= Integer.valueOf(jiaArray[position])+Integer.valueOf(auct.auct_minprice);
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.textView4:
                sendData();
                break;
        }
    }

    private void sendData() {
        String userId= String.valueOf(getSharedPreferences("User",MODE_APPEND).getInt("userId",-1));
        RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"insertBiddingData");
        requestParams.addBodyParameter("auct_id",auct.auct_id);
        System.out.println("auct_id"+auct.auct_id);
        requestParams.addBodyParameter("userId",userId);
        requestParams.addBodyParameter("nowPrice",nowPrice+"");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(PaiMAIbidding_bidActivity.this, "加价成功", Toast.LENGTH_SHORT).show();
               getBinddingData();
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

    private void getBinddingData() {//加价成功后刷新页面

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
