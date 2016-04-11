package com.shichai.www.choume.tools;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.shichai.www.choume.activity.MainActivity;
import com.shichai.www.choume.network.HttpStatus;

/**
 * Created by wyp on 15/12/24.
 */
public class UITools {

    public static void toastMsg(Context context, String msg){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastServerError(Context context){
        Toast.makeText(context,"连接服务器失败!", Toast.LENGTH_SHORT).show();
    }

    public static void warning(Context context,String friendlymsg, String msg){
        Toast.makeText(context,friendlymsg + "("+msg+")", Toast.LENGTH_SHORT).show();
    }

    public static void warningLong(Context context, String friendlymsg, String msg) {
        Toast.makeText(context,friendlymsg + "("+msg+")", Toast.LENGTH_LONG).show();
    }

    public static void warning(Context context,String friendlymsg, String msg, int code){
        String finalMsg = friendlymsg + "("+msg+")";
        if (HttpStatus.codeOf(code) != HttpStatus.UNKNOWN){
            finalMsg = HttpStatus.codeOf(code).desc;
        }
        Toast.makeText(context,finalMsg, Toast.LENGTH_SHORT).show();
    }

    public static void jumpToMainActivity(Context context, boolean isLogin) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ISLOGIN, isLogin);
        context.startActivity(intent);
    }
}
