package com.estore.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.estore.activity.LoginActivity;
import com.estore.activity.LoginOther;
import com.estore.activity.MyAuctionActivity;
import com.estore.activity.MyCareActivity;
import com.estore.activity.MyFriendsActivity;
import com.estore.activity.MyIntercalateActivity;
import com.estore.activity.MyOrderActivity;
import com.estore.activity.MyWalletActivity;
import com.estore.activity.PublishActivity;
import com.estore.activity.R;
import com.estore.activity.RegisterActivity;
import com.estore.activity.SystemInformActivity;
import com.estore.activity.TakePhotosAndSelectActivity;

/**
 * 我的部分主页面
 */
public class MyHomePageFragment extends Fragment implements View.OnClickListener {
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
    private SharedPreferences sp;
    private String username;

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
        sp=getActivity().getSharedPreferences("User",getActivity().MODE_APPEND);
        username=sp.getString("username",null);


        rb_publish.setOnClickListener(this);
        //我的拍卖
        rb_auction.setOnClickListener(this);
        //我的好友
        rb_myfriends.setOnClickListener(this);
        //我的关注
        rb_mycare.setOnClickListener(this);
        //我的钱包
        rb_mywallet.setOnClickListener(this);
        //系统通知
        rb_mynotice.setOnClickListener(this);
        //设置
        iv_intercalate.setOnClickListener(this);

        //订单
        rb_myorder.setOnClickListener(this);
        //拍照
        iv_userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), TakePhotosAndSelectActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(username==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            final Dialog dialog=builder.create();
            View view=View.inflate(getActivity(),R.layout.login_user,null);
            ((TextView)view.findViewById(R.id.tv_login)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //登录
                     getActivity().finish();
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);




                }
            });
            ((TextView)view.findViewById(R.id.tv_register)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // dialog.dismiss();
                    Intent intent=new Intent(getActivity(),RegisterActivity.class);
                    startActivity(intent);

                    //注册

                }
            });

            builder.setView(view);
            builder.show();
            return;
        }
        switch (v.getId()){
            case R.id.rb_publish :
                Intent intent=new Intent(getActivity(),PublishActivity.class);
                startActivity(intent) ;
                break;
            case R.id.rb_auction :

                Intent intent2=new Intent(getActivity(),MyAuctionActivity.class);
                startActivity(intent2) ;
                break;
            case R.id.rb_myfriends :
                Intent intent3=new Intent(getActivity(), MyFriendsActivity.class);
                startActivity(intent3) ;
                break;
            case R.id.rb_mycare :
                Intent intent4=new Intent(getActivity(), MyCareActivity.class);
                startActivity(intent4) ;
                break;
            case R.id.rb_mywallet :
                Intent intent5=new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent5) ;
                break;
            case R.id.rb_mynotice :
                Intent intent6=new Intent(getActivity(), SystemInformActivity.class);
                startActivity(intent6) ;
                break;
            case R.id.rb_myorder :
                Intent intent7=new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent7) ;
                break;
            case R.id.iv_intercalate :
                Intent intent8=new Intent(getActivity(), MyIntercalateActivity.class);
                startActivity(intent8) ;
                break;

        }

    }
}
