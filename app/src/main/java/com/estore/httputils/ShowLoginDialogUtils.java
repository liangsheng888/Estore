package com.estore.httputils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estore.activity.LoginOther;
import com.estore.R;
import com.estore.activity.RegisterActivity;

/**
 * Created by Administrator on 2016/10/22.
 * 关于 弹出登录提示的对话框
 */
public class ShowLoginDialogUtils {
    public static void showDialogLogin(final Context context){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    final Dialog dialog=builder.create();
                    dialog.setTitle("亲！你没有登录账号，请登录？");
                    View view=  LayoutInflater.from(context).inflate(R.layout.login_user,null);

                    //dialog.addContentView(view, new ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
                   dialog.show();
                   dialog.getWindow().setContentView(view);
                  ((TextView)view.findViewById(R.id.tv_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //登录
                   dialog.dismiss();

                Intent intent=new Intent(context,LoginOther.class);
                context.startActivity(intent);



            }
        });
        ((TextView)view.findViewById(R.id.tv_register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //注册
                Intent intent=new Intent(context,RegisterActivity.class);
                context.startActivity(intent);


            }
        });

    }
}
