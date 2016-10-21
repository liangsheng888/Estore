package com.estore.activity.myappliction;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.estore.httputils.HttpUrlUtils;
import com.estore.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {



        super.onCreate();


        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }


}
