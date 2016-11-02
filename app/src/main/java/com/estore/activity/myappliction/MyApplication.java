package com.estore.activity.myappliction;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import org.xutils.BuildConfig;
import org.xutils.x;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyApplication extends Application{

    private static Context content;

    @Override
    public void onCreate() {



        super.onCreate();


        //初始化xutils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);


        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName
                .equals(getCurProcessName(getApplicationContext()))
                || "io.rong.push"
                .equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }


    }


    public static Context getObjectContext() {

        return content;
    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


}
