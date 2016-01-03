package com.shichai.www.choume.activity.mine;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyCollectionActivity extends BaseActivity implements View.OnClickListener{

    private PullToRefreshListView listView;
    private ArrayList<OutsouringCrowdfunding.CfProject> projects;
    private MySponsorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        initActionBar();
        setTitle("我的收藏");
        initViews();
        loadProjects();
    }

    private void initViews(){

        listView = (PullToRefreshListView) findViewById(R.id.listView);
        adapter = new MySponsorAdapter(this, MySponsorAdapter.STAR);
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

    private void loadProjects(){
        if (MyApplication.getCfUser().collectedProjects.length != 0){
            projects = new ArrayList<OutsouringCrowdfunding.CfProject>(Arrays.asList(MyApplication.getCfUser().collectedProjects));
            adapter.setData(true, projects);
        }
    }


}
