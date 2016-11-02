package com.estore.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.estore.R;
import com.estore.activity.LoginActivity;
import com.estore.activity.LoginOther;
import com.estore.activity.MyAuctionActivity;
import com.estore.activity.MyCareActivity;
import com.estore.activity.MyFriendsActivity;
import com.estore.activity.MyIntercalateActivity;
import com.estore.activity.MyOrderActivity;
import com.estore.activity.MyWalletActivity;
import com.estore.activity.PublishActivity;

import com.estore.activity.RegisterActivity;
import com.estore.activity.SystemInformActivity;
import com.estore.activity.TakePhotosAndSelectActivity;
import com.estore.httputils.GetUserIdByNet;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.SharedPreferencesUtils;
import com.estore.httputils.xUtilsImageUtils;
import com.estore.pojo.MyPublishActivityBean;
import com.estore.pojo.UserBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * 我的部分主页面
 */
public class MyHomePageFragment extends Fragment implements View.OnClickListener {
    //private RadioButton rb_but5;
    private Button rb_publish;
    private Button rb_auction;
    private RadioButton rb_myfriends;
    //private ImageView rb_mycare;
    private RadioButton rb_mywallet;
    private RadioButton rb_mynotice;
    private Button rb_myorder;
    private RadioButton iv_intercalate;
    private SharedPreferences sp;
    private String username;
    private TextView tv_myNickname;
    UserBean user=new UserBean();
    private ImageView iv_userphoto;
    private BaseAdapter adapter;
    private ImageView iv_denglu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_myhomepage,null);
        sp=getActivity().getSharedPreferences("User",getActivity().MODE_APPEND);



        tv_myNickname = ((TextView) view.findViewById(R.id.tv_myNickname));
        //我的页面跳转
        iv_intercalate= ((RadioButton)view.findViewById(R.id.iv_intercalate));
        rb_publish = ((Button) view.findViewById(R.id.rb_publish));
        rb_auction = ((Button) view.findViewById(R.id.rb_auction));
        rb_myfriends = ((RadioButton) view.findViewById(R.id.rb_myfriends));
        //rb_mycare = ((ImageView) view.findViewById(R.id.rb_mycare));
        rb_mywallet = ((RadioButton) view.findViewById(R.id.rb_mywallet));
        rb_mynotice = ((RadioButton) view.findViewById(R.id.rb_mynotice));
        rb_myorder = ((Button) view.findViewById(R.id.rb_myorder));
        iv_denglu = ((ImageView) view.findViewById(R.id.iv_denglu));
        //  xUtilsImageUtils.display( iv_denglu,HttpUrlUtils.HTTP_URL +user.getUserPhoto(),true);
        tv_myNickname = ((TextView) view.findViewById(R.id.tv_myNickname));
        getUserinfo();

        return  view;

    }

    //请求网络
    private void getUserinfo() {
        String url = HttpUrlUtils.HTTP_URL + "/findUserServlet?userId="+sp.getInt("userId",0);
        //Log.i("MyHomePageFageTragment","url"+user.getUserId());
        RequestParams requestParams=new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("MyHomePageFragment","onSuccess"+result);
                Gson gson=new Gson();
                user=gson.fromJson(result,UserBean.class);
                Log.i("MyHomePageFragment","user"+user);
                xUtilsImageUtils.display( iv_denglu,HttpUrlUtils.HTTP_URL +user.getUserPhoto(),true);

                tv_myNickname.setText(user.getNickname()+"");

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

    @Override
    public void onStart() {
        getUserinfo();

        super.onStart();
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
        //rb_mycare.setOnClickListener(this);
        //我的钱包
        rb_mywallet.setOnClickListener(this);
        //系统通知
        rb_mynotice.setOnClickListener(this);
        //设置
        iv_intercalate.setOnClickListener(this);

        //订单
        rb_myorder.setOnClickListener(this);
        //昵称
        tv_myNickname.setOnClickListener(this);
        //头像
        iv_denglu.setOnClickListener(this);

        //getUserinfo();

    }

    @Override
    public void onClick(View v) {
        if (username == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final Dialog dialog = builder.create();
            View view = View.inflate(getActivity(), R.layout.login_user, null);
            ((TextView) view.findViewById(R.id.tv_login)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //登录
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);


                }
            });
            ((TextView) view.findViewById(R.id.tv_register)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // dialog.dismiss();
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);

                    //注册

                }
            });

            builder.setView(view);
            builder.show();
            return;
        }
        GetUserIdByNet.getUserIdByNet(getActivity());
        switch (v.getId()) {
            case R.id.rb_publish:
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                startActivity(intent);
                break;
            case R.id.rb_auction:

                Intent intent2 = new Intent(getActivity(), MyAuctionActivity.class);
                startActivity(intent2);
                break;
            case R.id.rb_myfriends:
                Intent intent3 = new Intent(getActivity(), MyFriendsActivity.class);
                startActivity(intent3);
                break;
//            case R.id.rb_mycare:
//                Intent intent4 = new Intent(getActivity(), MyCareActivity.class);
//                startActivity(intent4);
//                break;
            case R.id.rb_mywallet:
                Intent intent5 = new Intent(getActivity(), MyWalletActivity.class);
                startActivity(intent5);
                break;
            case R.id.rb_mynotice:
                Intent intent6 = new Intent(getActivity(), SystemInformActivity.class);
                startActivity(intent6);
                break;
            case R.id.rb_myorder:
                Intent intent7 = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(intent7);
                break;
            case R.id.iv_intercalate:
                Intent intent8 = new Intent(getActivity(), MyIntercalateActivity.class);
                startActivity(intent8);
                break;
            case R.id.iv_denglu:
                if (username == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    final Dialog dialog = builder.create();
                    View view = View.inflate(getActivity(), R.layout.login_user, null);
                    ((TextView) view.findViewById(R.id.tv_login)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //登录
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    ((TextView) view.findViewById(R.id.tv_register)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // dialog.dismiss();
                            Intent intent = new Intent(getActivity(), RegisterActivity.class);
                            startActivity(intent);
                        }
                    });

                }

        }
    }

}
