package com.estore.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.fragment.MyHomePageFragment;
import com.estore.httputils.HttpUrlUtils;
import com.estore.httputils.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_psd;
    private RelativeLayout btn_login;
    private TextView tv_register;
    private ProgressBar pb_login_state;
    private TextView tv_login;
    private RequestParams params;
    private CheckBox rb_rememberPsd;

    private final static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initView();
        initData();


        //tv_register = (TextView) findViewById(R.id.tv_register);
        //注册
//        tv_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
        //登录

    }

    private void initData() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final String username = et_username.getText().toString().trim();
                final String psd = et_psd.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "账户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(psd)) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = HttpUrlUtils.HTTP_URL+"loginchecked?email=" + username + "&password=" + psd + "";
                Log.e(TAG, url);

                params = new RequestParams(url);
                pb_login_state.setVisibility(View.VISIBLE);
                tv_login.setVisibility(View.VISIBLE);
                x.http().get(params, new Callback.CacheCallback<String>() {

                    @Override
                    public void onSuccess(String result) {
                        pb_login_state.setVisibility(View.GONE);
                        tv_login.setVisibility(View.GONE);

                        if ("false".equals(result)) {

                            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            if(rb_rememberPsd.isChecked()){//记住密码
                                SharedPreferencesUtils.saveUserInfo(LoginActivity.this,username,psd);
                            }
                            SharedPreferencesUtils.saveUserInfoOther(LoginActivity.this,username,psd);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("direct",5);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        pb_login_state.setVisibility(View.GONE);
                        tv_login.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this,"网络超时，请重新登录",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public boolean onCache(String result) {
                        return false;
                    }
                });
            }
        });

    }

    private void initView() {
        et_username = ((EditText) findViewById(R.id.et_username));
        et_psd = ((EditText) findViewById(R.id.et_psd));
        SharedPreferences sp=getSharedPreferences("data", Context.MODE_APPEND);
        String username= sp.getString("username",null);
        String psd=sp.getString("psd",null);
        if(TextUtils.isEmpty(username)&&TextUtils.isEmpty(psd)){
            et_username.setText(username);
            et_psd.setText(psd);
        }

        btn_login =(RelativeLayout ) findViewById(R.id.rl_butlogin);
        pb_login_state=(ProgressBar)this.findViewById(R.id.pb_login_state);
        tv_login=(TextView)this.findViewById(R.id.tv_login);
        rb_rememberPsd=(CheckBox) this.findViewById(R.id.rb_rememberPsd);

    }
}
