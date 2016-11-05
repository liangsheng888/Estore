package com.estore.activity;
/*
我的发布页面
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.estore.R;
import com.estore.activity.myappliction.MyApplication;
import com.estore.fragment.PublishAuctionFragment;
import com.estore.fragment.PublishEStoreFragment;
import com.estore.pojo.User;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup rg_grouppublish;
    private RadioButton rb_estorepublish;
    private RadioButton rb_auctionpublish;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment oldFragment;
    private Fragment newFragment;
    private  boolean isFirst=true;
    private ImageView iv_mypublishreturn;
    private ListView lv_publishlv;



    //activity生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        rb_estorepublish = ((RadioButton) findViewById(R.id.rb_estorepublish));
        rb_auctionpublish = ((RadioButton) findViewById(R.id.rb_auctionpublish));
//        lv_publishlv = ((ListView) findViewById(R.id.lv_publishlv));
        rb_auctionpublish.setOnClickListener(this);
        rb_estorepublish.setOnClickListener(this);
        //跳到fragment
        switchfragment(new PublishEStoreFragment());
        //发布的二货返回
        iv_mypublishreturn = ((ImageView) findViewById(R.id.iv_mypublish_return));
        iv_mypublishreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(PublishActivity.this, MainActivity.class);
                //startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        //将frament1填充到framelayout中
        switch (v.getId()){
            case R.id.rb_estorepublish:

                if(fragment1==null)
                    fragment1 = new PublishEStoreFragment();
                newFragment = fragment1;
                rb_estorepublish.setBackgroundColor(Color.RED);
                rb_auctionpublish.setBackgroundColor(Color.WHITE);
                break;
            case R.id.rb_auctionpublish:
                if(fragment2==null)
                    fragment2 = new PublishAuctionFragment();
                newFragment = fragment2;
                rb_auctionpublish.setBackgroundColor(Color.RED);
                rb_estorepublish.setBackgroundColor(Color.WHITE);

                break;
        }
        switchfragment(newFragment);
    }



    public void switchfragment(Fragment fragment) {
        // 设置一个默认值
        if(fragment == null){
            fragment1 = fragment = new PublishEStoreFragment();
            rb_estorepublish.setBackgroundColor(Color.RED);
        }
        FragmentManager fm = this.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(oldFragment!=null &&!oldFragment.isHidden() && oldFragment.isAdded() && oldFragment!=fragment ){
            ft.hide(oldFragment);
        }
        if(fragment!=null && fragment.isAdded() && fragment.isHidden()){
            ft.show(fragment);
        }
       else {
            if(!fragment.isAdded()) {
                ft.add(R.id.fl_publish, fragment);
                //fragment回退站点返回返回上一级而不是退出整个activity
                //ft.addToBackStack(null);
            }
        }
        oldFragment = fragment;
        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



}
