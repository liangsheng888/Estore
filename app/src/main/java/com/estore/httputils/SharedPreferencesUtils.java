package com.estore.httputils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/20.
 */
public class SharedPreferencesUtils {
    public static boolean saveUserInfo(Context context, String username, String password){
         SharedPreferences sp=context.getSharedPreferences("data",Context.MODE_APPEND);
         SharedPreferences.Editor ed=sp.edit();
         ed.putString("username",username);
         ed.putString("psd",password);
         ed.commit();
         return true;
        }
    public static boolean saveUserInfoOther(Context context, String username, String password){
        SharedPreferences sp=context.getSharedPreferences("User",Context.MODE_APPEND);
        SharedPreferences.Editor ed=sp.edit();
        ed.putString("username",username);
        ed.putString("psd",password);
        ed.commit();
        return true;
    }
    }


