package com.shichai.www.choume.activity.chou;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.NewCfProjectInvestParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetCfProjectParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectComment;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectCommentParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectCommentResp;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfUserCBConsumeParam;
import com.globalways.choume.R;
import com.globalways.proto.nano.Common;
import com.google.gson.Gson;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.ProjectDetailAdapter;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.dialog.ReplyDialog;
import com.shichai.www.choume.network.HttpStatus;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.*;
import com.shichai.www.choume.view.ListViewForScrollView;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class ChouDetailActivity extends BaseActivity implements View.OnClickListener, ProjectDetailAdapter.InvestListener{

    public static final String PROJECT_ID = "project_id";
    private final int REQUEST_NEW_INVEST = 100;
    private ListViewForScrollView listView;
    private ProjectDetailAdapter adapter;
    private View headerView;
    private View tv_reply,tv_comment,tv_supporter;
    private ImageButton btnCollect;
    private long projectId;

    private TextView tvTitle;
    //项目信息
    private TextView tvProjectName ,tvProjectStatus, tvProjectDesc, tvProgressPercent, tvAlreadyMoneyAmount, tvAlreadyGoodsAmount,tvRemainDays;
    private ImageView ivProjectCfuserAvatar, ivProjectDetailHeader;
    private ProgressBar progressBar;
    private PicassoImageLoader imageLoader = new PicassoImageLoader(this);
    private CfProject currentProject;

    //项目回复列表
    private CfProjectComment[] comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou);
        ActionBar();
        initViews();

        projectId = getIntent().getLongExtra(PROJECT_ID, -1);
        if (projectId != -1) {
            loadProject(projectId);
        }else {
            UITools.toastMsg(this, "获取项目信息错误");
        }

        CfProjectCommentParam param = new CfProjectCommentParam();
        param.projectId = projectId;
        CfProjectManager.getInstance().loadCfProjectComment(param, new ManagerCallBack<CfProjectCommentResp>() {
            @Override
            public void success(CfProjectCommentResp result) {
                for (CfProjectComment comment : result.comments) {
                    Log.i("yangping", comment.content);
                }
            }

            @Override
            public void error(Exception e) {
                super.error(e);
            }

            @Override
            public void warning(int code, String msg) {
                super.warning(code, msg);
            }
        });

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
        btnCollect = (ImageButton) actionbarLayout.findViewById(R.id.bt_add);
        btnCollect.setImageDrawable(getResources().getDrawable(R.drawable.seletor_collect));
        ab.setCustomView(actionbarLayout);
        ab.setDisplayShowCustomEnabled(true);

    }

    private void initViews() {
        btnCollect.setVisibility(View.VISIBLE);
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CMTool.isCollectedByCurrentUser(currentProject)) {
                    OutsouringCrowdfunding.UserCollectProjectParam param = new OutsouringCrowdfunding.UserCollectProjectParam();
                    param.projectId = projectId;
                    param.token = LocalDataConfig.getToken(ChouDetailActivity.this);
                    CfUserManager.getInstance().userCollectProject(param, new ManagerCallBack<Common.Response>() {
                        @Override
                        public void success(Common.Response result) {
                            btnCollect.setSelected(true);
                            MyApplication.getCfUser().collectedProjects = ArrayUtils.add(MyApplication.getCfUser().collectedProjects, currentProject);
                        }

                        @Override
                        public void warning(int code, String msg) {
                            UITools.warning(ChouDetailActivity.this, "收藏失败", HttpStatus.codeOf(code).desc);
                        }

                        @Override
                        public void error(Exception e) {
                            UITools.toastServerError(ChouDetailActivity.this);
                        }
                    });
                } else {
                    OutsouringCrowdfunding.UserUnCollectProjectParam param = new OutsouringCrowdfunding.UserUnCollectProjectParam();
                    param.projectId = projectId;
                    param.token = LocalDataConfig.getToken(ChouDetailActivity.this);
                    CfUserManager.getInstance().userUnCollectProject(param, new ManagerCallBack<Common.Response>() {
                        @Override
                        public void success(Common.Response result) {
                            int index = 0;
                            for (; index < MyApplication.getCfUser().collectedProjects.length; index++) {
                                if (MyApplication.getCfUser().collectedProjects[index].id == currentProject.id) {
                                    break;
                                }
                            }
                            MyApplication.getCfUser().collectedProjects = ArrayUtils.remove((MyApplication.getCfUser().collectedProjects), index);
                            btnCollect.setSelected(false);
                        }

                        @Override
                        public void warning(int code, String msg) {
                            UITools.warning(ChouDetailActivity.this, "取消收藏失败", HttpStatus.codeOf(code).desc);
                        }

                        @Override
                        public void error(Exception e) {
                            UITools.toastServerError(ChouDetailActivity.this);
                        }
                    });
                }
            }
        });

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
        tvProjectStatus = (TextView) headerView.findViewById(R.id.tvProjectStatus);
        tvProjectDesc = (TextView) headerView.findViewById(R.id.tvProjectDesc);
        progressBar   = (ProgressBar) headerView.findViewById(R.id.progressBar);
        ivProjectCfuserAvatar = (ImageView) headerView.findViewById(R.id.ivProjectCfuserAvatar);

        tvProgressPercent = (TextView) headerView.findViewById(R.id.tvProgressPercent);
        tvAlreadyMoneyAmount = (TextView) headerView.findViewById(R.id.tvAlreadyMoneyAmount);
        tvAlreadyGoodsAmount = (TextView) headerView.findViewById(R.id.tvAlreadyGoodsAmount);
        tvRemainDays = (TextView) headerView.findViewById(R.id.tvRemainDays);


//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ReplyDialog replyDialog = new ReplyDialog(ChouDetailActivity.this,R.style.dialog);
//                replyDialog.show();
//            }
//        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //reward
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
                adapter.setDataComments(comments);
                ReplyDialog replyDialog = new ReplyDialog(ChouDetailActivity.this,R.style.dialog);
                replyDialog.show();
                //newComment("cf project comment at:"+ Calendar.getInstance().toString());
                break;
            case R.id.tv_supporter:
                tv_reply.setSelected(false);
                tv_comment.setSelected(false);
                tv_supporter.setSelected(true);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                adapter.setDataSupporter(currentProject.invests);
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
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        GetCfProjectParam projectParam = new GetCfProjectParam();
        projectParam.projectId = projetId;
        CfProjectManager.getInstance().getCfProject(projectParam, new ManagerCallBack<OutsouringCrowdfunding.GetCfProjectResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetCfProjectResp result) {
                currentProject = result.project;

                //加载项目评论
                CfProjectCommentParam cfProjectCommentParam = new CfProjectCommentParam();
                cfProjectCommentParam.projectId = currentProject.id;
                CfProjectManager.getInstance().loadCfProjectComment(cfProjectCommentParam, new ManagerCallBack<OutsouringCrowdfunding.CfProjectCommentResp>() {
                    @Override
                    public void success(OutsouringCrowdfunding.CfProjectCommentResp result) {
                        loadDataToViews(currentProject);
                        //默认显示支持方式
                        tv_reply.performClick();
                        comments = result.comments;
                    }

                    @Override
                    public void warning(int code, String msg) {
                        UITools.warning(ChouDetailActivity.this, "加载评论信息失败",msg);
                        dialog.dismiss();
                    }

                    @Override
                    public void error(Exception e) {
                        UITools.toastServerError(ChouDetailActivity.this);
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouDetailActivity.this, "加载项目信息失败",msg);
                dialog.dismiss();
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouDetailActivity.this);
                dialog.dismiss();
            }
        });
    }

    private void loadDataToViews(CfProject cfProject) {
        if (CMTool.isCollectedByCurrentUser(currentProject)) {
            btnCollect.setSelected(true);
        } else {
            btnCollect.setSelected(false);
        }

        if (cfProject.pics == null || cfProject.pics.length == 0) {
            ivProjectDetailHeader.setVisibility(View.GONE);
        }else {
            imageLoader.loadUrlImageToView(cfProject.pics[0].url,400,400,R.mipmap.guangyuan_1,R.mipmap.guangyuan_1,ivProjectDetailHeader);
        }

        tvProjectName.setText(cfProject.title);
        tvProjectStatus.setText(CMTool.getProjectStatus(cfProject.status));
        tvProjectDesc.setText(cfProject.desc);
        CMTool.loadProjectUserAvatar(currentProject.hongId,this,ivProjectCfuserAvatar);
        progressBar.setProgress(CMTool.generateProjectProgress(cfProject));

        tvProgressPercent.setText(CMTool.generateProjectProgress(currentProject)+"%");
        tvAlreadyMoneyAmount.setText(Tool.fenToYuan(currentProject.alreadyMoneyAmount));
        tvAlreadyGoodsAmount.setText(String.valueOf(currentProject.alreadyGoodsAmount));
        tvRemainDays.setText(String.valueOf(TimeUnit.SECONDS.toDays(currentProject.deadline - currentProject.fundTime)));
        tvTitle.setText(currentProject.title);



        dialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_NEW_INVEST) {
            if (resultCode == Activity.RESULT_OK) {
                int supportType = data.getIntExtra(ConfirmActivity.REWARD_SUPPORT_TYPE, -1);
                long amount = data.getLongExtra(ConfirmActivity.REWARD_AMOUNT, 0);
                switch (supportType) {
                    case OutsouringCrowdfunding.GOODS_CFPST:
                        currentProject.alreadyGoodsAmount += amount;
                        break;
                    case OutsouringCrowdfunding.MONEY_CFPST:
                        currentProject.alreadyMoneyAmount += amount;
                        break;
                    case OutsouringCrowdfunding.PEOPLE_CFPST:
                        currentProject.alreadyPeopleAmount += amount;
                        break;
                    case OutsouringCrowdfunding.EQUITY_CFPST:
                        currentProject.alreadyProjectEquity += amount;
                        break;
                }

                tvProgressPercent.setText(CMTool.generateProjectProgress(currentProject) + "%");
                tvAlreadyMoneyAmount.setText(Tool.fenToYuan(currentProject.alreadyMoneyAmount));
                tvAlreadyGoodsAmount.setText(String.valueOf(currentProject.alreadyGoodsAmount));
                progressBar.setProgress(CMTool.generateProjectProgress(currentProject));
            }
        }
    }

    @Override
    public void onNewInvest(final CfProjectReward reward) {

        Gson gson = new Gson();
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(ConfirmActivity.REWARD_CONFIRM, gson.toJson(reward));
        intent.putExtra(ConfirmActivity.PROJECT_CONFIRM, gson.toJson(currentProject));
        startActivityForResult(intent, REQUEST_NEW_INVEST);


        if (MyApplication.getCfUser() == null) {
            UITools.toastMsg(this, "请先登录");
            return;
        }

        //如果项目不是在已发布状态则不能再参与项目
        if (currentProject.status != OutsouringCrowdfunding.PUBLISHED_CFPS) {
            UITools.toastMsg(this, "项目当前不接受新的支持");
            return;
        }
        //如果用户没那么多筹币就不能参与
        if (reward.supportType == OutsouringCrowdfunding.MONEY_CFPST && MyApplication.getCfUser().coin < reward.amount) {
            UITools.toastMsg(this, "您的筹币不足，请先充值兑换");
            return;
        }

//        NewCfProjectInvestParam projectInvestParam = new NewCfProjectInvestParam();
//        projectInvestParam.cfProjectId = reward.cfProjectId;
//        projectInvestParam.count = 1;
//        projectInvestParam.token = LocalDataConfig.getToken(context);
//        projectInvestParam.cfProjectRewardId = reward.id;
//        CfProjectManager.getInstance().newCfProjectInvest(projectInvestParam, new ManagerCallBack<OutsouringCrowdfunding.NewCfProjectInvestResp>() {
//            @Override
//            public void success(OutsouringCrowdfunding.NewCfProjectInvestResp result) {
//                //如果是需要付钱的方式，则立马付钱
//                if (result.invest.coinPay > 0) {
//                    UITools.toastMsg(context, "参与项目成功，支付...");
//                    CfUserCBConsumeParam param = new CfUserCBConsumeParam();
//                    param.coin = result.invest.coinPay;
//                    param.orderId = result.invest.orderId;
//                    param.token = LocalDataConfig.getToken(ChouDetailActivity.this);
//                    CfUserManager.getInstance().cfUserCBConsume(param, new ManagerCallBack<OutsouringCrowdfunding.CfUserCBConsumeResp>() {
//                        @Override
//                        public void success(OutsouringCrowdfunding.CfUserCBConsumeResp result) {
//                            //result.history.
//                            //UITools.toastMsg(ChouDetailActivity.this, "支付成功");
//                            //修改本地用户筹币数目
//                            MyApplication.getCfUser().coin -= result.history.coin;
//                            refreshProjectDatas(reward);
//                        }
//
//                        @Override
//                        public void warning(int code, String msg) {
//                            UITools.warning(ChouDetailActivity.this, "支付筹币失败", HttpStatus.codeOf(code).desc);
//                        }
//
//                        @Override
//                        public void error(Exception e) {
//                            UITools.toastServerError(ChouDetailActivity.this);
//                        }
//                    });
//                } else {
//                    UITools.toastMsg(context, "参与项目成功");
//                    //重新加载数据
//                    refreshProjectDatas(reward);
//                }
//            }
//
//            @Override
//            public void warning(int code, String msg) {
//                UITools.warning(context, "参与项目失败", HttpStatus.codeOf(code).desc);
//            }
//
//            @Override
//            public void error(Exception e) {
//                UITools.toastServerError(context);
//            }
//        });

    }

    /**
     * 当众筹成功后，不从网络获取数据，直接修改本地数据<br>
     * 调整alreadyXXX的数量
     * @param reward
     */
    private void refreshProjectDatas(CfProjectReward reward) {

        switch (reward.supportType) {
            case OutsouringCrowdfunding.GOODS_CFPST:
                currentProject.alreadyGoodsAmount += reward.amount;
                break;
            case OutsouringCrowdfunding.MONEY_CFPST:
                currentProject.alreadyMoneyAmount += reward.amount;
                break;
            case OutsouringCrowdfunding.PEOPLE_CFPST:
                currentProject.alreadyPeopleAmount += reward.amount;
                break;
            case OutsouringCrowdfunding.EQUITY_CFPST:
                currentProject.alreadyProjectEquity += reward.amount;
                break;
        }

        tvProgressPercent.setText(CMTool.generateProjectProgress(currentProject) + "%");
        tvAlreadyMoneyAmount.setText(Tool.fenToYuan(currentProject.alreadyMoneyAmount));
        tvAlreadyGoodsAmount.setText(String.valueOf(currentProject.alreadyGoodsAmount));
        progressBar.setProgress(CMTool.generateProjectProgress(currentProject));

    }

    /**
     * 新增项目评论
     * @param comment
     */
    private void newComment(String comment) {
        CfProjectCommentParam param = new CfProjectCommentParam();
        param.content = comment;
        param.projectId = currentProject.id;
        param.token = LocalDataConfig.getToken(this);
        CfProjectManager.getInstance().newCfProjectComment(param, new ManagerCallBack<OutsouringCrowdfunding.CfProjectCommentResp>() {
            @Override
            public void success(OutsouringCrowdfunding.CfProjectCommentResp result) {
                UITools.toastMsg(ChouDetailActivity.this, "comment success");
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChouDetailActivity.this, "评论失败", msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouDetailActivity.this);
            }
        });
    }

}
