package com.shichai.www.choume.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.globalways.choume.R;

/**
 * Created by HeJianjun on 2016/1/25.
 */
public class ReplyDialog extends Dialog {

    private EditText editText;

    public ReplyDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_reply);
        init();
    }

    public ReplyDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_reply);
        init();
    }

    protected ReplyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.dialog_reply);
        init();
    }

    public void init(){
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editText = (EditText) findViewById(R.id.editText);

    }
}
