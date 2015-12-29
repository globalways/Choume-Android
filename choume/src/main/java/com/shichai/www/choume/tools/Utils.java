package com.shichai.www.choume.tools;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by helang on 2015/12/26.
 */
public class Utils {

    public static boolean isNumber( String string){
        return  string.matches("[0-9]+");
    }

    public static int dp2px(Context context,int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
