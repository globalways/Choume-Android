package com.shichai.www.choume.activity.mine;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyJoinActivity extends BaseActivity implements View.OnClickListener{

    private PullToRefreshListView listView;

    private MySponsorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        initActionBar();
        setTitle("我的参与");
        initViews();
    }

    private void initViews(){

        listView = (PullToRefreshListView) findViewById(R.id.listView);
        adapter = new MySponsorAdapter(this);
        listView.setAdapter(adapter);
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
