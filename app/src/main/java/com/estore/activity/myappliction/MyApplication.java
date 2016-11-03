package com.estore.activity.myappliction;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.xutils.BuildConfig;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Created by Administrator on 2016/9/13.
 */
public class MyApplication extends Application{

    private static Context content;
    private Integer messageNum;

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

        //新消息处理
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(final Message message, int i) {
                //false 走融云默认方法  true走自己设置的方法

                if (message != null) {//app是否运行在后台 不在发消息推送广播

                    //未读消息数量
                    RongIMClient.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            messageNum = integer;
                            Log.i("cc", "---IMMessageNum：" + integer);

                            //app后台运行 发送广播
                            Intent intent = new Intent();
                            intent.putExtra("SendId", message.getSenderUserId());//消息发送者
                            intent.putExtra("MsgType", message.getConversationType() + "");
                            intent.putExtra("MsgNum", messageNum + "");
                            intent.setAction("com.yu.chatdemo.receiver.ChatBoardcaseReceiver");
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MyApplication.this.getApplicationContext().sendBroadcast(intent);

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            Log.d("cc", "---IMMessageNumError：" + errorCode);
                        }
                    });

                }

                return true;
            }
        });
    }
//        SharedPreferences sp1=getSharedPreferences("user",MODE_APPEND);
//        String token=sp1.getString("token","");
//        Log.i("cc", "onCreate: "+token);
//        RongIM.connect(token, new RongIMClient.ConnectCallback() {
//            @Override
//            public void onTokenIncorrect() {
//
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                Log.i("cc", "——onSuccess—-" + s);
//
////                startActivity(new Intent(ProductInfoActivity.this,MyFriendsActivity.class));
//
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.i("cc","--onError--"+errorCode);
//
//            }
//        });
//
//
//    }


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
