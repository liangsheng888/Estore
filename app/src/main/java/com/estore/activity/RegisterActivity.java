package com.estore.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estore.R;
import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity{

    private EditText etNickName;
    private EditText etRegEmail;
    private ImageView ivBackRegister;
    private TextView tvLoginRegister;
    private EditText etPassword;
    private EditText etOncePassword;
    private TextView RegisterBtn;
    private String userId;
    private String userName;
    User user;
    //    List<User> users=new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        etNickName = ((EditText) findViewById(R.id.et_nickname));
        etRegEmail = ((EditText) findViewById(R.id.et_regemail));
        //ivBackRegister = ((ImageView) findViewById(R.id.iv_back_register));
        tvLoginRegister = ((TextView) findViewById(R.id.tv_login_register));
        etPassword = ((EditText) findViewById(R.id.et_password));
        etOncePassword = ((EditText) findViewById(R.id.et_oncepassword));
        RegisterBtn = ((TextView) findViewById(R.id.register_button));

        tvLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

      /*  ivBackRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
*/

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName=etRegEmail.getText().toString();
                if((etPassword.getText().toString()).equals(etOncePassword.getText().toString()) && isEmail(userName)){
                    RequestParams params=new RequestParams(HttpUrlUtils.HTTP_URL+"register");
                    String nick=null;
                    String userPwd= etPassword.getText().toString();
                    try {
                        nick = URLEncoder.encode(etNickName.getText().toString(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                    params.addBodyParameter("nickName",nick);
                    params.addBodyParameter("email",userName);
                    params.addBodyParameter("password",userPwd);


                    final String finalNick = nick;
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            userId=result;
                            Log.i("cc","register=========="+userId);
                            SharedPreferences sp=getSharedPreferences("User",MODE_APPEND);
                            sp.edit().putInt("userId",Integer.parseInt(userId)).commit();
                            sp.edit().putString("nick",finalNick).commit();
                            Log.i("cc", "onSuccess: "+result);
//                            HttpUtil.getToken(getApplicationContext(),userId, finalNick);
                            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);

                            startActivity(intent);
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(getApplicationContext(),"网络异常",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });


                }else {
                    if (!(etPassword.getText().toString()).equals(etOncePassword.getText().toString())) {

                        Toast.makeText(RegisterActivity.this, "两次密码不相同！请重新输入", Toast.LENGTH_SHORT).show();
                        etPassword.setText("");
                        etOncePassword.setText("");
                    }else if(!isEmail(userName)){
                        Toast.makeText(RegisterActivity.this, "邮箱格式不正确，请重新输入", Toast.LENGTH_SHORT).show();
                        etRegEmail.setText("");
                    }
                }
            }
        });
    }

    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if (m.matches()) {
            return true;
        }else {
            return false;
        }
    }

}
