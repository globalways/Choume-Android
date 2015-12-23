package com.shichai.www.choume.activity.mine;

import android.os.Bundle;
import android.view.MenuItem;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MyMessageAdapter;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyMessageActivity extends BaseActivity {

    private PullToRefreshListView listView;
    private MyMessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        initActionBar();
        setTitle("消息");
        initViews();
    }
    private void initViews(){
        listView = (PullToRefreshListView) findViewById(R.id.listView);
        adapter = new MyMessageAdapter(this);
        listView.setAdapter(adapter);
        ArrayList<String> strings = new ArrayList<>();
        for (int i=0; i<10 ;i++){
            strings.add("XXSASD");
        }
        adapter.addDatas(strings);
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
