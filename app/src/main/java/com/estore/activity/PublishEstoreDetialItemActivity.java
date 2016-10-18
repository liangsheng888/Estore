package com.estore.activity;
/*
我的发布---发布的二货item
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.MyPublishActivityBean;

import org.xutils.x;


public class PublishEstoreDetialItemActivity extends AppCompatActivity {
    private TextView tv_erdetial_probegintime;
    private TextView tv_erdetial_represent;
    private TextView tv_erdetial_proname;
    private TextView tv_erdetial_proprice;
    private TextView tv_erdetial_procategory;
    private TextView tv_erdetial_pronumber;
    private ImageView iv_erdetial_propic;
    private ImageView iv_pubEstitem_return;
    private TextView tv_pubestorewhere;
    private TextView tv_pubestorewhere1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishestore_itemdetial);
        //返回
        iv_pubEstitem_return= ((ImageView) findViewById(R.id.iv_pubEstitem_return));
        iv_pubEstitem_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        tv_erdetial_procategory = ((TextView) findViewById(R.id.tv_erdetial_procategory));//类型
        tv_erdetial_pronumber = ((TextView) findViewById(R.id.tv_erdetial_pronumber));//个数
        tv_pubestorewhere1 = ((TextView) findViewById(R.id.tv_pubestorewhere1));//高校
        tv_erdetial_proprice = ((TextView) findViewById(R.id.tv_erdetial_proprice));//价格
        tv_erdetial_represent = ((TextView) findViewById(R.id.tv_erdetial_represent));//描述

        tv_pubestorewhere = ((TextView) findViewById(R.id.tv_pubestorewhere));//同城


        tv_erdetial_proname = ((TextView) findViewById(R.id.tv_erdetial_proname));//名字
        iv_erdetial_propic = ((ImageView) findViewById(R.id.iv_erdetial_propic));//图片



        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        MyPublishActivityBean.ProImag  bundlepro=(MyPublishActivityBean.ProImag)bundle.getSerializable("estoreimage");
        x.image().bind(iv_erdetial_propic, HttpUrlUtils.HTTP_URL +bundlepro.imgurl);
        Log.e("estoreimage",bundlepro.toString());
        tv_erdetial_represent.setText(bundlepro.description);//描述
        tv_erdetial_proname.setText(bundlepro.name);//名字
        tv_erdetial_procategory.setText("("+bundlepro.category+")");
        tv_erdetial_proprice.setText(bundlepro.estoreprice+"");//价格
        tv_erdetial_pronumber.setText(bundlepro.pnum+"");//个数

        //判断同城高校
        // Toast.makeText(getActivity(),pro.heighschool+"",Toast.LENGTH_LONG).show();
        if (bundlepro.prowhere==1) {//高校
            tv_pubestorewhere1.setText(bundlepro.samecity);
        }
        if(bundlepro.prowhere==0) {//同城高校
            tv_pubestorewhere.setText(bundlepro.samecity);
            tv_pubestorewhere1.setText(bundlepro.heighschool);

        }




    }
}


