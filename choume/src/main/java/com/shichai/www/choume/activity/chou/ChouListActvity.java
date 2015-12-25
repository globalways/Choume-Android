package com.shichai.www.choume.activity.chou;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.common.Common;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by HeJianjun on 2015/12/25.
 */
public class ChouListActvity extends BaseActivity implements View.OnClickListener{
    private PullToRefreshListView listView;

    private MySponsorAdapter adapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        type = getIntent().getStringExtra(Common.TYPE);

        initActionBar();
        if (!TextUtils.isEmpty(type)){
            setTitle(type);
        }
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
