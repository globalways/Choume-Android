package com.shichai.www.choume.activity.mine;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.proto.nano.Common;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.MySponsorAdapter;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.HttpStatus;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.UITools;
import com.shichai.www.choume.view.PullToRefreshListView;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyCollectionActivity extends BaseActivity implements View.OnClickListener,MySponsorAdapter.OnConfigListener{

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
        adapter.setOnConfigListener(this);
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

    @Override
    public void onConfig(long projectId, int position) {

    }

    @Override
    public void onCollect(long projectId, boolean willCollet, final int index) {
        if (willCollet) {
            OutsouringCrowdfunding.UserCollectProjectParam param = new OutsouringCrowdfunding.UserCollectProjectParam();
            param.projectId = projectId;
            param.token = LocalDataConfig.getToken(this);
            CfUserManager.getInstance().userCollectProject(param, new ManagerCallBack<Common.Response>() {
                @Override
                public void success(Common.Response result) {
                    UITools.toastMsg(MyCollectionActivity.this, "收藏成功");
                }

                @Override
                public void warning(int code, String msg) {
                    UITools.warning(MyCollectionActivity.this, "收藏失败", HttpStatus.codeOf(code).desc);
                }

                @Override
                public void error(Exception e) {
                    UITools.toastServerError(MyCollectionActivity.this);
                }
            });
        } else {
            OutsouringCrowdfunding.UserUnCollectProjectParam param = new OutsouringCrowdfunding.UserUnCollectProjectParam();
            param.projectId = projectId;
            param.token = LocalDataConfig.getToken(this);
            CfUserManager.getInstance().userUnCollectProject(param, new ManagerCallBack<Common.Response>() {
                @Override
                public void success(Common.Response result) {
                    UITools.toastMsg(MyCollectionActivity.this, "取消收藏成功");
                    MyApplication.getCfUser().collectedProjects = ArrayUtils.remove(MyApplication.getCfUser().collectedProjects, index);
                    adapter.getCfProjects().remove(index);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void warning(int code, String msg) {
                    UITools.warning(MyCollectionActivity.this, "取消收藏失败", HttpStatus.codeOf(code).desc);
                }

                @Override
                public void error(Exception e) {
                    UITools.toastServerError(MyCollectionActivity.this);
                }
            });
        }
    }
}
