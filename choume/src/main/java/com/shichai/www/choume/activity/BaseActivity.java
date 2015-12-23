package com.shichai.www.choume.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.outsouring.crowdfunding.R;

/**
 * Created by HeJianjun on 2015/12/7.
 */
public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    protected ActionBar actionBar;
    protected View actionbarLayout;
    protected void initActionBar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.mipmap.ico_arrow_left);
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionbarLayout = LayoutInflater.from(this).inflate(
                    R.layout.actionbar_main_layout, null);
            actionBar.setCustomView(actionbarLayout);
            actionBar.setDisplayShowCustomEnabled(true);
        }

    }

    protected void setTitle(String title){
        if ( actionbarLayout.findViewById(R.id.textView) != null){
            ((TextView)actionbarLayout.findViewById(R.id.textView)).setTextColor(getResources().getColor(R.color.white));
            ((TextView)actionbarLayout.findViewById(R.id.textView)).setText(title);
        }
    }

    protected void setActionBarLayout(){
        android.app.ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowHomeEnabled( false );
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(R.layout.actionbar_layout, null);
            android.app.ActionBar.LayoutParams layout = new android.app.ActionBar.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
            actionBar.setCustomView(v,layout);
        }else {

        }
    }

    protected void transparent(){
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }


}
