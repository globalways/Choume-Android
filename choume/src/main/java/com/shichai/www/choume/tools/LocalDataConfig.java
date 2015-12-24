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
    public final static String IS_FIRST_RUN = "isFirstRun";
    public final static String IS_ALL = "isAll";
    public final static String IS_MEITUAN = "isMeituan";
    public final static String IS_NUOMI = "isNuomi";
    public final static String IS_WOWO = "isWowo";
    public final static String IS_DIANPING = "isDianping";

    public final static String IS_DALIY_TASK = "isDaliyTask";
    public final static String IS_CHECK_LAW = "isCheckLaw";
    public final static String IS_UPDATE_ACCOUNT_ONCE = "isUpdateAccountOnce";//升级版本之后，要用的一次刷新页面的参数

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





    public static void setIsUpdateAccountOnce(Context context, boolean isFirstRun){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_UPDATE_ACCOUNT_ONCE, isFirstRun);
        editor.apply();
    }

    public static boolean isUpdateAccountOnce(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_UPDATE_ACCOUNT_ONCE, false);
    }

    public static void setIsFirstRun(Context context, boolean isFirstRun){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_FIRST_RUN, isFirstRun);
        editor.apply();
    }

    public static boolean isFirstRun(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_FIRST_RUN, true);
    }

    public static void setIsAll(Context context, boolean isAll){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_ALL, isAll);
        editor.apply();
    }

    public static boolean isAll(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_ALL, false);
    }

    public static void setIsMeituan(Context context, boolean isMeituan){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_MEITUAN, isMeituan);
        editor.apply();
        checkAllOfStatus(context);
    }

    public static boolean isMeituan(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_MEITUAN, false);
    }

    public static void setIsWowo(Context context, boolean isWowo){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_WOWO, isWowo);
        editor.apply();
    }

    public static boolean isWowo(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_WOWO, false);
    }
    public static void setIsNuomi(Context context, boolean isNuomi){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_NUOMI, isNuomi);
        editor.apply();
        checkAllOfStatus(context);
    }

    public static boolean isNuomi(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_NUOMI, false);
    }
    public static void setIsDianping(Context context, boolean isDianping){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_DIANPING, isDianping);
        editor.apply();
        checkAllOfStatus(context);
    }

    public static boolean isDianping(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_DIANPING, false);
    }

    public static void setIsDaliyTask(Context context, boolean isDaliyTask){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_DALIY_TASK, isDaliyTask);
        editor.apply();
    }

    public static boolean isDaliyTask(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_DALIY_TASK, false);
    }

    public static void setIsCheckLaw(Context context, boolean isCheckLaw){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_CHECK_LAW, isCheckLaw);
        editor.apply();
    }

    public static boolean isCheckLaw(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_CHECK_LAW, false);
    }

    /**
     * 检查是否更新过美团以前的数据
     * @para context
     * @param isUpdateData
     */
    public static void setIsDataUpdatedMeituan(Context context,boolean isUpdateData){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("key", isUpdateData);
        editor.apply();
    }
    public static boolean isDataUpdatedMeituan(Context context){
        SharedPreferences pref = context.getSharedPreferences(
                CONFIGS_FILE_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean("key", false);
    }

    public static void checkAllOfStatus(Context context){
        int count = 0;
        if (LocalDataConfig.isNuomi(context)){
            count ++;
        }
        if (LocalDataConfig.isMeituan(context)){
            count ++;
        }
        if (LocalDataConfig.isDianping(context)){
            count ++;
        }
        if (count > 1){
            LocalDataConfig.setIsAll(context, true);
        }else {
            LocalDataConfig.setIsAll(context, false);
        }
    }
}
