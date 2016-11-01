package com.estore;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estore.activity.MyFriendsActivity;

/**
 * Created by Administrator on 2016/11/1.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//        intent.putExtra("SendId", message.getSenderUserId());//消息发送者
//        intent.putExtra("MsgType", message.getConversationType() + "");
//        intent.putExtra("MsgNum",messageNum+"");

        String SendId= intent.getStringExtra("SendId");
        String MsgType= intent.getStringExtra("MsgType");
        String MsgNum=intent.getStringExtra("MsgNum");
        Log.i("cc","收到通知");

        NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);

        // notification.setSmallIcon(R.drawable.logo);
        notification.setContentTitle("标题");
        notification.setContentText("内容");
        notification.setAutoCancel(true);	    //点击自动消息
        notification.setDefaults(Notification.DEFAULT_ALL);	        //铃声,振动,呼吸灯
        Intent intent2 = new Intent(context, MyFriendsActivity.class);    //点击通知进入的界面
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setContentIntent(contentIntent);
        manager.notify(0, notification.build());

    }



    }
