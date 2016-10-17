package com.estore.activity;
/*
我的发布---发布的拍卖item
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.ListMyAuctionActivityBean;

import org.xutils.x;


public class PublishAuctionDetialItemActivity extends AppCompatActivity {
    private TextView tv_pubauctdetial_probegintime;
    private TextView tv_pubauctdetial_proendtime;
    private TextView tv_pubauctdetial_represent;
    private TextView tv_pubauctdetial_proname;
    private TextView tv_pubauctdetial_proprice;
    private TextView tv_pubauctdetial_pronumber;
    private ImageView iv_pubauctdetial_propic;
    private ImageView iv_pubauctdetial_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishauction_itemdetial);
        //返回
        iv_pubauctdetial_return = ((ImageView) findViewById(R.id.iv_pubauctdetial_return));
        iv_pubauctdetial_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_pubauctdetial_represent = ((TextView) findViewById(R.id.tv_pubauctdetial_represent));
        iv_pubauctdetial_propic = ((ImageView) findViewById(R.id.iv_pubauctdetial_propic));//描述
        tv_pubauctdetial_proname = ((TextView) findViewById(R.id.tv_pubauctdetial_proname));//名字
        tv_pubauctdetial_proprice = ((TextView) findViewById(R.id.tv_pubauctdetial_proprice));//价格
        tv_pubauctdetial_pronumber = ((TextView) findViewById(R.id.tv_pubauctdetial_pronumber));//个数
        tv_pubauctdetial_probegintime = ((TextView) findViewById(R.id.tv_pubauctdetial_probegintime));//开始时间
        tv_pubauctdetial_proendtime = ((TextView) findViewById(R.id.tv_pubauctdetial_proendtime));//结束时间
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ListMyAuctionActivityBean.ProImag  bundlepro=(ListMyAuctionActivityBean.ProImag)bundle.getSerializable("pubimag");
        x.image().bind(iv_pubauctdetial_propic, HttpUrlUtils.HTTP_URL +bundlepro.auct_imgurl);
        Log.e("pubimag",bundlepro.toString());
        tv_pubauctdetial_represent.setText(bundlepro.auct_description);//描述
        tv_pubauctdetial_proname.setText(bundlepro.auct_name);//名字
        tv_pubauctdetial_proprice.setText(bundlepro.auct_minprice+"");//价格
        tv_pubauctdetial_pronumber.setText(bundlepro.auct_pnum+"");//个数
        tv_pubauctdetial_probegintime.setText(bundlepro.auct_begin+"---");//开始时间
        tv_pubauctdetial_proendtime.setText(bundlepro.auct_end+"");//结束时间


    }
}


