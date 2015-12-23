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


}
