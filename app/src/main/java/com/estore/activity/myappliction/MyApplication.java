package com.estore.activity.myappliction;

import android.app.Application;

import org.xutils.BuildConfig;
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
