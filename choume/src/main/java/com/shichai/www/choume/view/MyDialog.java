package com.shichai.www.choume.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.globalways.choume.R;


/**
 * Created by HeJianjun on 2015/5/21.
 */
public class MyDialog extends Dialog{

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_layout);
    }

}
