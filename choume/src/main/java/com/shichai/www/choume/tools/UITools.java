package com.shichai.www.choume.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wyp on 15/12/24.
 */
public class UITools {

    public static void ToastMsg(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastServerError(Context context){
        Toast.makeText(context,"网络错误!请确认您的网络是正常的", Toast.LENGTH_SHORT).show();
    }
}
