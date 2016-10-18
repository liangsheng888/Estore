package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.ListMyAuctionActivityBean;

import org.xutils.x;
public class AuctionDetialItemActivity extends AppCompatActivity {

    private ImageView iv_audetial_propic;
    private TextView tv_audetial_represent;
    private TextView tv_audetial_proname;
    private TextView tv_audetial_proprice;
    private TextView tv_audetial_pronumber;
    private TextView tv_audetial_proendtime;
    private ImageView iv_audetial_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detialitem_auction);
        //返回
        iv_audetial_return = ((ImageView) findViewById(R.id.iv_audetial_return));
        iv_audetial_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent =new Intent(AuctionDetialItemActivity.this, MyAuctionActivity.class);
                //startActivity(intent);
                finish();

            }
        });
        iv_audetial_propic = ((ImageView) findViewById(R.id.iv_audetial_propic));
        tv_audetial_represent = ((TextView) findViewById(R.id.tv_audetial_represent));
        tv_audetial_proname = ((TextView) findViewById(R.id.tv_audetial_proname));
        tv_audetial_proprice = ((TextView) findViewById(R.id.tv_audetial_proprice));
        tv_audetial_pronumber = ((TextView) findViewById(R.id.tv_audetial_pronumber));
        tv_audetial_proendtime = ((TextView) findViewById(R.id.tv_audetial_proendtime));
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        ListMyAuctionActivityBean.ProImag  bundlepro=(ListMyAuctionActivityBean.ProImag)bundle.getSerializable("auctiondetial");
        x.image().bind(iv_audetial_propic, HttpUrlUtils.HTTP_URL+bundlepro.auct_imgurl);
        Log.e("SellActivity",bundlepro.toString());
        tv_audetial_represent.setText(bundlepro.auct_description);
        tv_audetial_proname.setText(bundlepro.auct_name);
        tv_audetial_proprice.setText(bundlepro.auct_minprice+"");
        tv_audetial_pronumber.setText(bundlepro.auct_pnum+"");
        tv_audetial_proendtime.setText(bundlepro.auct_end+"");


    }
}
