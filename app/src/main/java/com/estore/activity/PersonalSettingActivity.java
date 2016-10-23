package com.estore.activity;
/*
个人资料设置页面
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.activity.myappliction.MyApplication;
import com.estore.httputils.GetUserIdByNet;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.User;
import com.estore.pojo.UserBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class PersonalSettingActivity extends AppCompatActivity  {
    private User user=new User();

    private ImageView iv_perreturn;
    private Dialog dialog = null;
    private TextView tv_nicknamecontent;
    private TextView tv_persexcontent;
    private TextView tv_phonenumber;
    private TextView tv_deliveryadress;
    private SharedPreferences sp;
    //弹框
    private RelativeLayout rl_photorl;
    private RelativeLayout rl_sexrl;
    private RelativeLayout rl_nickname;
    private RelativeLayout rl_phonenumrl;
    private RelativeLayout rl_adressrl;
    private RelativeLayout rl_save;
    private static final String TAG = "PersonalSettingActivity";
    String sex[] = {"男", "女"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        sp=getSharedPreferences("User",MODE_APPEND);
        user.setUserId(sp.getInt("userId",0));
        Log.i("PersonalSettingActivity","userId:"+user.getUserId());


        //请求数据
        getUserIfo();
        initView();
        initDate();
        initEvent();



    }
    private void initView() {
        //返回个人资料
        iv_perreturn = ((ImageView) findViewById(R.id.iv_perreturn));


        rl_photorl = ((RelativeLayout) findViewById(R.id.rl_photorl));
        rl_nickname = ((RelativeLayout) findViewById(R.id.rl_nicknamerl));
        rl_sexrl = ((RelativeLayout) findViewById(R.id.rl_sexrl));
        rl_phonenumrl = ((RelativeLayout) findViewById(R.id.rl_phonenumrl));
        rl_adressrl = ((RelativeLayout) findViewById(R.id.rl_adressrl));
        rl_save = ((RelativeLayout) findViewById(R.id.rl_saverl));
    }
    private void initEvent() {
        //返回
        iv_perreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_photorl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //修改昵称
        rl_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RelativeLayout modify_myNickname = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_name_activity, null);
                AlertDialog builder = new AlertDialog.Builder(PersonalSettingActivity.this).setView(modify_myNickname).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_name = ((EditText) modify_myNickname.findViewById(R.id.et_edit_name));
                        String name = et_name.getText().toString();
                        tv_nicknamecontent.setText(name);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        //设置性别
        rl_sexrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(PersonalSettingActivity.this);
                alert.setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                tv_persexcontent.setText("男");
                                break;
                            case 1:
                                tv_persexcontent.setText("女");
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
        //设置电话号码
        rl_phonenumrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RelativeLayout modify_myphone = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_telphone_activity, null);
                new AlertDialog.Builder(PersonalSettingActivity.this).setView(modify_myphone).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_edit_telnumber = ((EditText) modify_myphone.findViewById(R.id.et_edit_telnumber));
                        String tel =et_edit_telnumber.getText().toString();
                        tv_phonenumber.setText(tel);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        //地址
        rl_adressrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RelativeLayout modify_myadress = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_modify_my_daress_activity, null);
                new AlertDialog.Builder(PersonalSettingActivity.this).setView(modify_myadress).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_adress = ((EditText) modify_myadress.findViewById(R.id.et_edit_adress));
                        String adress = et_adress.getText().toString();
                        tv_deliveryadress.setText(adress);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        //保存
        //String nickname, String sex, String user_address, Integer id, String user_phone
        rl_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"save", Toast.LENGTH_SHORT).show();
                String nickname=tv_nicknamecontent.getText().toString();
                String  sex= tv_persexcontent.getText().toString();
                String  phonnmber=tv_phonenumber.getText().toString();
                String  uaeraddress=tv_deliveryadress.getText().toString();
                User userModify=new User(nickname,sex,uaeraddress,user.getUserId(),phonnmber);
                Gson gson=new Gson();
                String usergson=gson.toJson(userModify);
                RequestParams requestParams=new RequestParams(HttpUrlUtils.HTTP_URL+"/modifyuserservlet");
                requestParams.addQueryStringParameter("user",usergson);//用户id

                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i(TAG,"onClick"+result);
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
        });
    }
    private void initDate() {


    }

    public void getUserIfo() {
        String url=HttpUrlUtils.HTTP_URL+ "/findUserServlet?userid="+ user.getUserId();
        Log.i("PersonalSettingActivity",user.getUserId()+"userId");
        RequestParams repuestparams=new RequestParams(url);
        x.http().get(repuestparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("PersonalSettingActivity",result);
                Gson gson=new Gson();
                UserBean users = gson.fromJson(result, UserBean.class);
                Log.i("PersonalSettingActivity",users.toString());

                tv_nicknamecontent = ((TextView) findViewById(R.id.tv_nicknamecontent));//昵称
                tv_nicknamecontent.setText(users.getNickname());
                tv_persexcontent = ((TextView) findViewById(R.id.tv_persexcontent));//性别
                tv_persexcontent.setText(users.getSex().equals("1")?"男":"女");
                tv_phonenumber = ((TextView) findViewById(R.id.tv_phonenumber));//手机号

                tv_phonenumber.setText(users.getUser_phone());
                Log.i("PersonalSettingActivity",users.getUser_phone()+"---"+users.getUser_address());
                tv_deliveryadress = ((TextView) findViewById(R.id.tv_deliveryadress));//地址
                tv_deliveryadress.setText(users.getUser_address());
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
}