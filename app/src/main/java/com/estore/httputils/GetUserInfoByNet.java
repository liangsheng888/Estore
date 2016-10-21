package com.estore.httputils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.estore.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Administrator on 2016/10/21.
 */
public class GetUserInfoByNet {
    private Integer userId=0;
    public GetUserInfoByNet(){}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getUserInfoByNet(Context context) {

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
                    Log.e("GetUserInfoByNet", "result" + result.toString());
                    userId=Integer.parseInt(result);





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
        return userId;}
}