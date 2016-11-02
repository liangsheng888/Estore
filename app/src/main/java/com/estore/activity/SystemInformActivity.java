package com.estore.activity;
/*
系统通知页面
 */
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.estore.httputils.HttpUrlUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by mawuyang on 2016-10-28.
 */
public class SystemInformActivity extends Activity {
    private TextView tv_title;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_inform);

        tv_title = ((TextView) findViewById(R.id.tv_title));
        tv_content = ((TextView) findViewById(R.id.tv_content));
//        TextView tv = new TextView(this);
//        tv.setText("用户自定义打开的Activity");
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            tv_title.setText("Title : " + title);
            tv_content.setText("Content : " + content);
        }
//        addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }

}
