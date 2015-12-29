package com.shichai.www.choume.activity.chou;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.NewCfProjectInvestParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetCfProjectParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.ProjectDetailAdapter;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.tools.*;
import com.shichai.www.choume.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class ChouDetailActivity extends BaseActivity implements View.OnClickListener, ProjectDetailAdapter.InvestListener{

    public static final String PROJECT_ID = "project_id";
    private ListViewForScrollView listView;
    private ProjectDetailAdapter adapter;
    private View headerView;
    private View tv_reply,tv_comment,tv_supporter;
    private Context context = this;

    private TextView tvTitle;
    //项目信息
    private TextView tvProjectName, tvProjectDesc, tvProgressPercent, tvAlreadyMoneyAmount, tvAlreadyGoodsAmount,tvRemainDays;
    private ImageView ivProjectCfuserAvatar, ivProjectDetailHeader;
    private ProgressBar progressBar;

    private PicassoImageLoader imageLoader = new PicassoImageLoader(this);

    private CfProject currentProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou);
        ActionBar();
        initViews();

        long projectId = getIntent().getLongExtra(PROJECT_ID, -1);
        if (projectId != -1) {
            loadProject(projectId);
        }else {
            UITools.toastMsg(this, "获取项目信息错误");
        }
    }

    private void ActionBar(){

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.mipmap.ico_back);
        ab.setDisplayHomeAsUpEnabled(true);

        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar_main_layout, null);
        ((TextView)actionbarLayout.findViewById(R.id.textView)).setTextColor(getResources().getColor(R.color.white));
        tvTitle = ((TextView)actionbarLayout.findViewById(R.id.textView));
        ab.setCustomView(actionbarLayout);
        ab.setDisplayShowCustomEnabled(true);

    }

    private void initViews() {

        listView = (ListViewForScrollView) findViewById(R.id.projectDetailListView);
        adapter = new ProjectDetailAdapter(this);
        adapter.setInvestListener(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        headerView = inflater.inflate(R.layout.layout_chou_header, null);
        listView.addHeaderView(headerView);
        tv_reply = headerView.findViewById(R.id.tv_reply);
        tv_comment = headerView.findViewById(R.id.tv_comment);
        tv_supporter = headerView.findViewById(R.id.tv_supporter);
        tv_reply.setOnClickListener(this);
        tv_comment.setOnClickListener(this);
        tv_supporter.setOnClickListener(this);


        ivProjectDetailHeader = (ImageView) findViewById(R.id.ivProjectDetailHeader);
        tvProjectName = (TextView) headerView.findViewById(R.id.tvProjectName);
        tvProjectDesc = (TextView) headerView.findViewById(R.id.tvProjectDesc);
        progressBar   = (ProgressBar) headerView.findViewById(R.id.progressBar);
        ivProjectCfuserAvatar = (ImageView) headerView.findViewById(R.id.ivProjectCfuserAvatar);

        tvProgressPercent = (TextView) headerView.findViewById(R.id.tvProgressPercent);
        tvAlreadyMoneyAmount = (TextView) headerView.findViewById(R.id.tvAlreadyMoneyAmount);
        tvAlreadyGoodsAmount = (TextView) headerView.findViewById(R.id.tvAlreadyGoodsAmount);
        tvRemainDays = (TextView) headerView.findViewById(R.id.tvRemainDays);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_reply:
                tv_reply.setSelected(true);
                tv_comment.setSelected(false);
                tv_supporter.setSelected(false);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                adapter.setDataReward(currentProject.rewards);
                break;
            case R.id.tv_comment:
                tv_reply.setSelected(false);
                tv_comment.setSelected(true);
                tv_supporter.setSelected(false);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                ArrayList<String> strings2 = new ArrayList<>();
                for (int i=0; i<8 ;i++){
                    strings2.add("XXSASD");
                }
                //adapter.addDatas(strings2);
                break;
            case R.id.tv_supporter:
                tv_reply.setSelected(false);
                tv_comment.setSelected(false);
                tv_supporter.setSelected(true);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                ArrayList<String> strings3 = new ArrayList<>();
                for (int i=0; i<5 ;i++){
                    strings3.add("XXSASD");
                }
                //adapter.addDatas(strings3);
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

    private void loadProject(long projetId){
        GetCfProjectParam projectParam = new GetCfProjectParam();
        projectParam.projectId = projetId;
        CfProjectManager.getInstance().getCfProject(projectParam, new ManagerCallBack<OutsouringCrowdfunding.GetCfProjectResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetCfProjectResp result) {
                currentProject = result.project;
                loadDataToViews(result.project);
                //默认显示支持方式
                tv_reply.performClick();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouDetailActivity.this, "加载项目信息失败",msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouDetailActivity.this);
            }
        });
    }

    private void loadDataToViews(CfProject cfProject) {
        if (cfProject.pics == null || cfProject.pics.length == 0) {
            ivProjectDetailHeader.setVisibility(View.GONE);
        }else {
            imageLoader.loadUrlImageToView(cfProject.pics[0].url,400,400,R.mipmap.guangyuan_1,R.mipmap.guangyuan_1,ivProjectDetailHeader);
        }

        tvProjectName.setText(cfProject.title);
        tvProjectDesc.setText(cfProject.desc);
        CMTool.loadProjectUserAvatar(currentProject.hongId,this,ivProjectCfuserAvatar);
        progressBar.setProgress(CMTool.generateProjectProgress(cfProject));

        tvProgressPercent.setText(CMTool.generateProjectProgress(currentProject)+"%");
        tvAlreadyMoneyAmount.setText(Tool.fenToYuan(currentProject.alreadyMoneyAmount));
        tvAlreadyGoodsAmount.setText(String.valueOf(currentProject.alreadyGoodsAmount));
        tvRemainDays.setText(String.valueOf(TimeUnit.SECONDS.toDays(currentProject.deadline - currentProject.fundTime)));
        tvTitle.setText(currentProject.title);
    }

    @Override
    public void onNewInvest(CfProjectReward reward) {

        UITools.toastMsg(this, "搞不懂写的参与项目是哪个方法");

//        NewCfProjectInvestParam projectInvestParam = new NewCfProjectInvestParam();
//        projectInvestParam.cfProjectId  = reward.cfProjectId;
//        projectInvestParam.count = 1;
//        projectInvestParam.token = LocalDataConfig.getToken(context);
//        projectInvestParam.cfProjectRewardId = reward.id;
//        CfProjectManager.getInstance().newCfProjectInvest(projectInvestParam, new ManagerCallBack<OutsouringCrowdfunding.NewCfProjectInvestResp>() {
//            @Override
//            public void success(OutsouringCrowdfunding.NewCfProjectInvestResp result) {
//                UITools.toastMsg(context,"参与项目成功");
//            }
//
//            @Override
//            public void warning(int code, String msg) {
//                UITools.warning(context,"参与项目失败",msg);
//            }
//
//            @Override
//            public void error(Exception e) {
//                UITools.toastServerError(context);
//            }
//        });

    }
}
