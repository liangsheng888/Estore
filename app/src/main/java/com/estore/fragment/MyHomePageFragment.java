package com.estore.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.estore.activity.MyAuctionActivity;
import com.estore.activity.MyCareActivity;
import com.estore.activity.MyFriendsActivity;
import com.estore.activity.MyIntercalateActivity;
import com.estore.activity.MyOrderActivity;
import com.estore.activity.MyWalletActivity;
import com.estore.activity.PublishActivity;
import com.estore.activity.R;
import com.estore.activity.SystemInformActivity;
import com.estore.activity.TakePhotosAndSelectActivity;

/**
 * Created by Administrator on 2016/10/1 0001.
 */
public class MyHomePageFragment extends Fragment {
    //private RadioButton rb_but5;
    private RadioButton rb_publish;
    private RadioButton rb_auction;
    private RadioButton rb_myfriends;
    private RadioButton rb_mycare;
    private RadioButton rb_mywallet;
    private RadioButton rb_mynotice;
    private RadioButton rb_myorder;
    private ImageView iv_intercalate;
    private ImageView iv_userphoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_myhomepage,null);
        //我的页面跳转
        iv_intercalate= ((ImageView)view.findViewById(R.id.iv_intercalate));
        //rb_but5 = ((RadioButton) view.findViewById(R.id.rb_but5));
        rb_publish = ((RadioButton) view.findViewById(R.id.rb_publish));
        rb_auction = ((RadioButton) view.findViewById(R.id.rb_auction));
        rb_myfriends = ((RadioButton) view.findViewById(R.id.rb_myfriends));
        rb_mycare = ((RadioButton) view.findViewById(R.id.rb_mycare));
        rb_mywallet = ((RadioButton) view.findViewById(R.id.rb_mywallet));
        rb_mynotice = ((RadioButton) view.findViewById(R.id.rb_mynotice));
        rb_myorder = ((RadioButton) view.findViewById(R.id.rb_myorder));
        //拍照
        iv_userphoto = ((ImageView) view.findViewById(R.id.iv_userphoto));
        return  view;


    }
    //页面跳转
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //我的发布
        rb_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PublishActivity.class);
                startActivity(intent) ;
            }
        });
        //我的拍卖
        rb_auction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MyAuctionActivity.class);
                startActivity(intent) ;
            }
        });
        //我的好友
        rb_myfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyFriendsActivity.class);
                startActivity(intent) ;
            }
        });
        //我的关注
        rb_mycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyCareActivity.class);
                startActivity(intent) ;
            }
        });
        //我的钱包
        rb_mywallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent) ;
            }
        });
        //系统通知
        rb_mynotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SystemInformActivity.class);
                startActivity(intent) ;
            }
        });
        //设置
        iv_intercalate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyIntercalateActivity.class);
                startActivity(intent) ;
            }
        });
        //订单
        rb_myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent) ;
            }
        });
        //拍照
        iv_userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TakePhotosAndSelectActivity.class);
                startActivity(intent);
            }
        });


    }
}
