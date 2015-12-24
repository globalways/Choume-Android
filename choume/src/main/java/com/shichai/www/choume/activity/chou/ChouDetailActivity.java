package com.shichai.www.choume.activity.chou;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MyMessageAdapter;
import com.shichai.www.choume.view.ListViewForScrollView;

import java.util.ArrayList;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class ChouDetailActivity extends BaseActivity implements View.OnClickListener{
    private ListViewForScrollView listView;
    private MyMessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou);
        ActionBar();
        initViews();
    }

    private void ActionBar(){

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.mipmap.ico_back);
        ab.setDisplayHomeAsUpEnabled(true);

        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar_main_layout, null);
        ((TextView)actionbarLayout.findViewById(R.id.textView)).setTextColor(getResources().getColor(R.color.white));
        ((TextView)actionbarLayout.findViewById(R.id.textView)).setText("项目介绍");
        ab.setCustomView(actionbarLayout);
        ab.setDisplayShowCustomEnabled(true);

    }

    private void initViews(){
        listView = (ListViewForScrollView) findViewById(R.id.listView);
        adapter = new MyMessageAdapter(this);
        listView.setAdapter(adapter);
        ArrayList<String> strings = new ArrayList<>();
        for (int i=0; i<10 ;i++){
            strings.add("XXSASD");
        }
        adapter.addDatas(strings);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
