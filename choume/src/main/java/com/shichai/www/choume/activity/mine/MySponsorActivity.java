package com.shichai.www.choume.activity.mine;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;

/**
 * 我的发起
 * Created by HeJianjun on 2015/12/7.
 */
public class MySponsorActivity extends BaseActivity implements View.OnClickListener{

    private PullToRefreshListView listView;

    private MySponsorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        initActionBar();
        setTitle("我的发起");
        initViews();
    }

    private void initViews(){

        listView = (PullToRefreshListView) findViewById(R.id.listView);
        adapter = new MySponsorAdapter(this);
        listView.setAdapter(adapter);
        ArrayList<String> strings = new ArrayList<>();
        for (int i=0; i<10 ;i++){
            strings.add("XXSASD");
        }
        adapter.addDatas(strings);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
        }
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
