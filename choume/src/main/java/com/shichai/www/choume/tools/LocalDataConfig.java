package com.shichai.www.choume.tools;

import android.content.Context;
import android.content.SharedPreferences;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;


/**
 * 本地数据配置
 * Created by HeJianjun on 2015/8/26.
 */
public class LocalDataConfig {
    public final static String CONFIGS_FILE_NAME = "settings";

    public final static String CMTOKEN     = "token";
    public final static String CMACCOUNT   = "tel";
    public final static String CMPASSWORD  = "password";


    public static void setToken(Context context, String token){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(CMTOKEN, token);
        editor.apply();
    }

    public static void setTel(Context context, String tel){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(CMACCOUNT, tel);
        editor.apply();
    }

    public static void setPwd(Context context, String pwd){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(CMPASSWORD, pwd);
        editor.apply();
    }

    public static String getToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(CMTOKEN, "");
    }
    public static String getTel(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(CMACCOUNT, "");
    }

    public static String getTPwd(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getString(CMPASSWORD, "");
    }



}
