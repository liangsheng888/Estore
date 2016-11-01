package com.estore.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.User;

import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity{

    private EditText etNickName;
    private EditText etRegEmail;
    private ImageView ivBackRegister;
    private TextView tvLoginRegister;
    private EditText etPassword;
    private EditText etOncePassword;
    private TextView RegisterBtn;
    User user;
//    List<User> users=new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        etNickName = ((EditText) findViewById(R.id.et_nickname));
        etRegEmail = ((EditText) findViewById(R.id.et_regemail));
        ivBackRegister = ((ImageView) findViewById(R.id.iv_back_register));
        tvLoginRegister = ((TextView) findViewById(R.id.tv_login_register));
        etPassword = ((EditText) findViewById(R.id.et_password));
        etOncePassword = ((EditText) findViewById(R.id.et_oncepassword));
        RegisterBtn = ((TextView) findViewById(R.id.register_button));
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((etPassword.getText().toString()).equals(etOncePassword.getText().toString())){
                    RequestParams params=new RequestParams(HttpUrlUtils.HTTP_URL+"");
                    String userName=etRegEmail.getText().toString();
                    String nick=etNickName.getText().toString();
                    String userPwd=etPassword.getText().toString();
//                    user=new User(userName,userPwd,nick);
//                    users.add(user);
                    params.addQueryStringParameter("nickName",nick);
                    params.addQueryStringParameter("email",userName);
                    params.addQueryStringParameter("password",userPwd);

                }else{
                    Toast.makeText(RegisterActivity.this,"两次密码不相同！请重新输入",Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etOncePassword.setText("");
                }

            }
        });
    }

}
