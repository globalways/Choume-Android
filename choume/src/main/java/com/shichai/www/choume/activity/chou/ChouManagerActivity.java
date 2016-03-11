package com.shichai.www.choume.activity.chou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.mine.MySponsorActivity;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class ChouManagerActivity extends BaseActivity implements View.OnClickListener{

    private long projectId;
    private CfProject cfProject;

    private TextView tvAlreadyMoneyAmount, tvAlreadyPeopleAmount, tvAlreadyGoodsAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_manager);
        initActionBar();
        projectId = getIntent().getLongExtra(MySponsorActivity.PROJECT_ID, 0);
        dialog.show();
        getProject();
        setTitle("加载项目名称...");

        tvAlreadyGoodsAmount = (TextView) findViewById(R.id.tvAlreadyGoodsAmount);
        tvAlreadyMoneyAmount = (TextView) findViewById(R.id.tvAlreadyMoneyAmount);
        tvAlreadyPeopleAmount = (TextView) findViewById(R.id.tvAlreadyPeopleAmount);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_manager:
                Intent intent = new Intent(this,ChouMemberActivity.class);
                intent.putExtra(MySponsorActivity.PROJECT_ID, projectId);
                startActivity(intent);
                break;
            case R.id.tv_support_way:
                Intent intent1 = new Intent(this,ChouSupportWayActivity.class);
                intent1.putExtra(MySponsorActivity.PROJECT_ID, projectId);
                startActivity(intent1);
                break;
        }
    }


    private void loadDatasToView() {
        setTitle(cfProject.title);
        tvAlreadyMoneyAmount.setText("已筹集"+ Tool.fenToYuan(cfProject.alreadyMoneyAmount)+"元");
        tvAlreadyPeopleAmount.setText("已筹集人员"+ cfProject.alreadyPeopleAmount+"名");
        tvAlreadyGoodsAmount.setText("已筹集"+ cfProject.requiredGoodsName+ cfProject.alreadyGoodsAmount+"件");
    }

    private void getProject() {
        OutsouringCrowdfunding.GetCfProjectParam projectParam  = new OutsouringCrowdfunding.GetCfProjectParam();
        projectParam.projectId = projectId;
        CfProjectManager.getInstance().getCfProject(projectParam, new ManagerCallBack<OutsouringCrowdfunding.GetCfProjectResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetCfProjectResp result) {
                cfProject = result.project;
                loadDatasToView();
                dialog.dismiss();
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouManagerActivity.this);
                dialog.dismiss();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouManagerActivity.this, "获取项目详细失败", msg);
                dialog.dismiss();
            }
        });
    }


}
