package com.estore.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/10/28.
 */

public class PaiMaiTiXingService extends Service {
    String str=null;

    public class MyBinder extends Binder{

//Long data=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime();
//
//        public void callMethodInService(){
//            methodInService();
//
//        }
//methodInService();

    }

    private void methodInService() {
        //自定义方法
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       str= (String) intent.getSerializableExtra("PaiMaiService");

        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
