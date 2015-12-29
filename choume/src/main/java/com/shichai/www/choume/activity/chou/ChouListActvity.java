package com.shichai.www.choume.activity.chou;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.FindCfProjectsParam;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.MainActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.common.Common;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.view.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by HeJianjun on 2015/12/25.
 */
public class ChouListActvity extends BaseActivity implements View.OnClickListener, PullToRefreshListView.OnLoadMoreListener, PullToRefreshListView.OnRefreshListener {
    private PullToRefreshListView listView;

    private static final int PAGESIZE = 10;
    private MySponsorAdapter adapter;
    private String type;
    private int category = -100;
    private int tag = -100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        type = getIntent().getStringExtra(Common.TYPE);
        category = getIntent().getIntExtra(MainActivity.PROJECT_CATEGORY, -100);
        tag = getIntent().getIntExtra(MainActivity.PROJECT_TAG, -100);

        initActionBar();
        if (!TextUtils.isEmpty(type)){
            setTitle(type);
        }
        initViews();

        findProjects(true);
    }

    private void initViews(){

        listView = (PullToRefreshListView) findViewById(R.id.listView);
        listView.setOnLoadListener(this);
        listView.setOnRefreshListener(this);
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

    /**
     * INVALID_CFC = 0;
        // 筹乐子
        HAPPY_CFC = 1;
        // 筹票子
        MONEY_CFC = 2;
        // 筹爱心
        LOVE_CFC = 3;
        // 校园合伙人-项目
        PROJECT_CFC = 4;
        // 校园合伙人-产品
        PRODUCT_CFC = 5;
     */
    private void findProjects(final boolean isReload){
        FindCfProjectsParam param = new FindCfProjectsParam();
        param.status = OutsouringCrowdfunding.PUBLISHED_CFPS;
        if (tag != -100) {
            param.tag = tag;
        }
        if (category != -100) {
            param.category = category;
        }
        param.page = adapter.getNext_page(isReload);
        param.size = PAGESIZE;
        CfProjectManager.getInstance().findCfProjects(param, new ManagerCallBack<OutsouringCrowdfunding.FindCfProjectsResp>() {
            @Override
            public void success(OutsouringCrowdfunding.FindCfProjectsResp result) {
                adapter.setData(isReload,Arrays.asList(result.projects));
                if (isReload) {
                    listView.onRefreshComplete();
                } else {
                    listView.onLoadMoreComplete();
                }
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouListActvity.this, "获取项目列表失败",msg);
                if (isReload) {
                    listView.onRefreshComplete();
                } else {
                    listView.onLoadMoreComplete();
                }
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouListActvity.this);
                if (isReload) {
                    listView.onRefreshComplete();
                } else {
                    listView.onLoadMoreComplete();
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        findProjects(false);
    }

    @Override
    public void onRefresh() {
        findProjects(true);
    }
}
/*

// 众筹标签，聚合分类
enum CfProjectTag {
    INVALID_CFPT = 0;
    // 一元秒筹，限时特筹
    LIMIT_TIME_CFPT = 1;
    // 世纪难题，周末去哪
    QUESTION_CFPT = 2;
    // 热门众筹，非筹不可
    HOT_CFPT = 3;
}



 */