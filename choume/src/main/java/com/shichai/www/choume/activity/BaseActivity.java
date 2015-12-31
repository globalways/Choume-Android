package com.shichai.www.choume.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.globalways.choume.R;
import com.shichai.www.choume.view.MyDialog;

/**
 * Created by HeJianjun on 2015/12/7.
 */
public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected ActionBar actionBar;
    protected View actionbarLayout;
    protected ImageButton bt_add;
    protected Button bt_right;
    protected ProgressDialog progressDialog;
    protected MyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this,ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在提交数据...");
        progressDialog.setCancelable(true);

        dialog = new MyDialog(this,R.style.Custom_Progress);
        dialog.setCancelable(true);
    }

    protected void initActionBar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.mipmap.ico_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionbarLayout = LayoutInflater.from(this).inflate(
                    R.layout.actionbar_main_layout, null);
            actionBar.setCustomView(actionbarLayout);
            actionBar.setDisplayShowCustomEnabled(true);
            bt_add = (ImageButton) actionbarLayout.findViewById(R.id.bt_add);
            bt_right = (Button) actionbarLayout.findViewById(R.id.bt_right);
        }

    }

    protected void setTitle(String title){
        if ( actionbarLayout.findViewById(R.id.textView) != null){
            ((TextView)actionbarLayout.findViewById(R.id.textView)).setTextColor(getResources().getColor(R.color.white));
            ((TextView)actionbarLayout.findViewById(R.id.textView)).setText(title);
        }
    }

    protected void setRightButton(String title) {
        bt_right.setVisibility(View.VISIBLE);
        bt_right.setText(title);
    }


}
