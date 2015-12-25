package com.shichai.www.choume.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
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
    protected ImageButton bt_add;
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
        }

    }

    protected void setTitle(String title){
        if ( actionbarLayout.findViewById(R.id.textView) != null){
            ((TextView)actionbarLayout.findViewById(R.id.textView)).setTextColor(getResources().getColor(R.color.white));
            ((TextView)actionbarLayout.findViewById(R.id.textView)).setText(title);
        }
    }


}
