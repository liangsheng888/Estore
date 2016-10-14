package com.estore.activity.myappliction;

import android.app.Application;

import com.estore.pojo.User;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyApplication extends Application{
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        user=new User();
        user.setUserId(1);
        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
