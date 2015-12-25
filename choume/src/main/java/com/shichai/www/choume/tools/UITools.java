package com.shichai.www.choume.tools;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.shichai.www.choume.activity.MainActivity;

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

    public static void jumpToMainActivity(Context context, boolean isLogin) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.ISLOGIN, isLogin);
        context.startActivity(intent);
    }
}
