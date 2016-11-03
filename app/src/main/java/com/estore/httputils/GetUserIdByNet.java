package com.estore.httputils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/10/21.
 */
public class GetUserIdByNet {




    public static void getUserIdByNet(final Context context) {
        SharedPreferences sp;
        sp = context.getSharedPreferences("User", Context.MODE_APPEND);
        String username = sp.getString("username", null);
        if (username != null) {
            RequestParams rp = new RequestParams(HttpUrlUtils.HTTP_URL + "getUserInfoServlet");

            Log.e("GetUserInfoByNet", "username" + username.toString());
            rp.addBodyParameter("username", username);
            x.http().post(rp, new Callback.CacheCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    Log.e("GetUserInfoByNet", "result" + result);
                    String[] user=result.split("=");
                    int userId=Integer.parseInt(user[0]);
                    String nick=user[1];
                    SharedPreferencesUtils.saveUserId(context,userId);
                    SharedPreferencesUtils.saveUserNick(context,nick);

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public boolean onCache(String result) {
                    return false;
                }
            });
        }

    }
}