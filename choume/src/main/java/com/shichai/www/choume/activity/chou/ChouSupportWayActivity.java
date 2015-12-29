package com.shichai.www.choume.activity.chou;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.mine.MySponsorActivity;
import com.shichai.www.choume.adapter.SupportWayAdapter;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.tools.UITools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class ChouSupportWayActivity extends BaseActivity {

    private long projectId;
    private OutsouringCrowdfunding.CfProject cfProject;

    private ListView listView;
    private SupportWayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_support_way);
        initActionBar();
        projectId = getIntent().getLongExtra(MySponsorActivity.PROJECT_ID, 0);
        setTitle("支持方式");
        initViews();
        getProject();
    }

    private void initViews(){
        listView = (ListView) findViewById(R.id.listView);
        adapter = new SupportWayAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getProject() {
        OutsouringCrowdfunding.GetCfProjectParam projectParam  = new OutsouringCrowdfunding.GetCfProjectParam();
        projectParam.projectId = projectId;
        CfProjectManager.getInstance().getCfProject(projectParam, new ManagerCallBack<OutsouringCrowdfunding.GetCfProjectResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetCfProjectResp result) {
                cfProject = result.project;
                adapter.addDatas(cfProject.rewards);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouSupportWayActivity.this);
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouSupportWayActivity.this, "获取项目详细失败", msg);
            }
        });
    }
}
