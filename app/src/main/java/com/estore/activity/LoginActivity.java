package com.estore.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.httputils.HttpUrlUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_psd;
    private Button btn_login;
    private TextView tv_register;
    private ProgressBar pb_login_state;
    private TextView tv_login;
    private RequestParams params;
    private final static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

                String username = et_username.getText().toString().trim();
                String psd = et_psd.getText().toString().trim();
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

                        if ("用户名或密码错误".equals(result)) {

                            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("json",result);
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
        btn_login = (Button) findViewById(R.id.btn_login);
        pb_login_state=(ProgressBar)this.findViewById(R.id.pb_login_state);
        tv_login=(TextView)this.findViewById(R.id.tv_login);
    }
}
