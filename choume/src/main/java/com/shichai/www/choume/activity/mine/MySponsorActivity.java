package com.shichai.www.choume.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.chou.ChouManagerActivity;
import com.shichai.www.choume.activity.sponsor.SponsorActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 我的发起
 * Created by HeJianjun on 2015/12/7.
 */
public class MySponsorActivity extends BaseActivity implements View.OnClickListener, PullToRefreshListView.OnRefreshListener, PullToRefreshListView.OnLoadMoreListener, MySponsorAdapter.OnConfigListener {

    private PullToRefreshListView listView;
    private ArrayList<CfProject> projects;
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
        bt_add.setVisibility(View.VISIBLE);
        bt_add.setOnClickListener(this);

        listView = (PullToRefreshListView) findViewById(R.id.listView);
        listView.setOnRefreshListener(this);
        listView.setOnLoadListener(this);
        adapter = new MySponsorAdapter(this, MySponsorAdapter.CONFIG);
        adapter.setOnConfigListener(this);
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
            case R.id.bt_add:
                startActivity(new Intent(this, SponsorActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadProjects(){
        if (MyApplication.getCfUser().fundProjects.length != 0){
            projects = new ArrayList<CfProject>(Arrays.asList(MyApplication.getCfUser().fundProjects));
            adapter.setData(true, projects);
        }


    }

    @Override
    public void onRefresh() {
        loadProjects();
        listView.onRefreshComplete();
    }

    @Override
    public void onLoadMore() {
        loadProjects();
        listView.onLoadMoreComplete();
    }

    @Override
    public void onConfig(long projectId) {
        startActivity(new Intent(this, ChouManagerActivity.class));

    }
}
