package com.shichai.www.choume.activity.chou;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetCfProjectParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectComment;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectCommentParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectCommentResp;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;
import com.globalways.choume.R;
import com.globalways.proto.nano.Common;
import com.google.gson.Gson;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.adapter.ProjectDetailAdapter;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.HttpStatus;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.*;
import com.shichai.www.choume.view.ListViewForScrollView;
import org.apache.commons.lang3.ArrayUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class ChouDetailActivity extends BaseActivity implements View.OnClickListener, ProjectDetailAdapter.InvestListener, AdapterView.OnItemClickListener {

    public static final String PROJECT_ID = "project_id";
    public static final String PROJECT_INTRO = "project_intro";
    public static final String PROJECT_DESC = "project_desc";
    private final int REQUEST_NEW_INVEST = 100;
    private ListViewForScrollView listView;
    private ProjectDetailAdapter adapter;
    private View headerView;
    private View tv_reply, tv_comment, tv_supporter;
    private ImageButton btnCollect;
    private long projectId;

    private TextView tvTitle;
    //项目信息
    private TextView tvProjectName, tvProjectStatus, tvProjectDesc, tvDescMore,
            tvAlreadyPeopleAmount, tvAlreadyMoneyAmount, tvAlreadyGoodsAmount, tvRemainDays,
            tvAlreadyPeopleAmountLabel, tvAlreadyMoneyAmountLabel, tvAlreadyGoodsAmountLabel, tvRemainDaysLabel;
    private ImageView ivProjectCfuserAvatar, ivProjectDetailHeader;
    private ProgressBar progressBar;
    private NumberProgressBar npbProjectProgress;
    private TextView tvProgressPercent;
    private PicassoImageLoader imageLoader = new PicassoImageLoader(this);
    private CfProject currentProject;

    //项目回复列表
    private CfProjectComment[] comments;

    //项目回复控件
    private LinearLayout llAddCommentArea;
    private EditText etComments;
    private Button btnCommentSend;
    private long replyToUserId = -1;
    private SoftKeyboard softKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou);
        ActionBar();
        initViews();

        projectId = getIntent().getLongExtra(PROJECT_ID, -1);
        if (projectId != -1) {
            loadProject(projectId);
        } else {
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

    private void ActionBar() {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.mipmap.ico_back);
        ab.setDisplayHomeAsUpEnabled(true);

        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar_main_layout, null);
        ((TextView) actionbarLayout.findViewById(R.id.textView)).setTextColor(getResources().getColor(R.color.white));
        tvTitle = ((TextView) actionbarLayout.findViewById(R.id.textView));
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
                //未联网情况下点击收藏错误
                if (currentProject == null) {
                    return;
                }
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

        //项目评论
        llAddCommentArea = (LinearLayout) findViewById(R.id.llAddCommentArea);
        etComments = (EditText) findViewById(R.id.etComments);
        btnCommentSend = (Button) findViewById(R.id.btnCommentSend);
        btnCommentSend.setOnClickListener(this);

        //监听软键盘开关 FORM https://gist.github.com/felHR85/6070f643d25f5a0b3674 by wyp
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.rlChouDetailRoot);
        InputMethodManager im = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);

        softKeyboard = new SoftKeyboard(mainLayout, im);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged() {

            @Override
            public void onSoftKeyboardHide() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (Tool.isEmpty(etComments.getText().toString())) {
                            etComments.setText("");
                            etComments.setHint("评论");
                            replyToUserId = -1;
                        }
                    }
                });
            }

            @Override
            public void onSoftKeyboardShow() {
                Log.i("yangping", "show key board");
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
        tvDescMore = (TextView) headerView.findViewById(R.id.tvDescMore);
        tvDescMore.setOnClickListener(this);
        progressBar = (ProgressBar) headerView.findViewById(R.id.progressBar);
        ivProjectCfuserAvatar = (ImageView) headerView.findViewById(R.id.ivProjectCfuserAvatar);

        tvAlreadyPeopleAmount = (TextView) headerView.findViewById(R.id.tvAlreadyPeopleAmount);
        tvAlreadyPeopleAmountLabel = (TextView) headerView.findViewById(R.id.tvAlreadyPeopleAmountLabel);

        tvAlreadyMoneyAmount = (TextView) headerView.findViewById(R.id.tvAlreadyMoneyAmount);
        tvAlreadyMoneyAmountLabel = (TextView) headerView.findViewById(R.id.tvAlreadyMoneyAmountLabel);

        tvAlreadyGoodsAmount = (TextView) headerView.findViewById(R.id.tvAlreadyGoodsAmount);
        tvAlreadyGoodsAmountLabel = (TextView) headerView.findViewById(R.id.tvAlreadyGoodsAmountLable);

        tvRemainDays = (TextView) headerView.findViewById(R.id.tvRemainDays);
        tvRemainDays.setSelected(true);
        tvRemainDaysLabel = (TextView) headerView.findViewById(R.id.tvRemainDaysLabel);
        tvRemainDaysLabel.setActivated(true);

        npbProjectProgress = (NumberProgressBar) headerView.findViewById(R.id.pbNumber);
        tvProgressPercent = (TextView) headerView.findViewById(R.id.tvProgressPercent);

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
        switch (v.getId()) {
            //reward
            case R.id.tv_reply:
                tv_reply.setSelected(true);
                tv_comment.setSelected(false);
                tv_supporter.setSelected(false);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                adapter.setDataReward(currentProject.rewards);
                llAddCommentArea.setVisibility(View.GONE);
                break;
            case R.id.tv_comment:
                tv_reply.setSelected(false);
                tv_comment.setSelected(true);
                tv_supporter.setSelected(false);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this);
                adapter.setDataComments(comments);
                //显示评论输入框
                llAddCommentArea.setVisibility(View.VISIBLE);
                //ReplyDialog replyDialog = new ReplyDialog(ChouDetailActivity.this,R.style.dialog_translucent);
                //replyDialog.show();
                //newComment("cf project comment at:"+ Calendar.getInstance().toString());
                break;
            case R.id.tv_supporter:
                tv_reply.setSelected(false);
                tv_comment.setSelected(false);
                tv_supporter.setSelected(true);
                adapter.clearDatas();
                listView.setAdapter(adapter);
                adapter.setDataSupporter(currentProject.invests);
                llAddCommentArea.setVisibility(View.GONE);
                break;
            case R.id.btnCommentSend: //项目评论
                newComment(etComments.getText().toString(), replyToUserId);
                break;
            case R.id.tvDescMore: //展开项目详细介绍
                Intent intent = new Intent(this, ChouDescMore.class);
                intent.putExtra(PROJECT_INTRO, currentProject.intro);
                intent.putExtra(PROJECT_DESC, currentProject.desc);
                startActivity(intent);
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

    private void loadProject(long projetId) {
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
                CfProjectManager.getInstance().loadCfProjectComment(cfProjectCommentParam, new ManagerCallBack<CfProjectCommentResp>() {
                    @Override
                    public void success(CfProjectCommentResp result) {
                        loadDataToViews(currentProject);
                        //默认显示支持方式
                        tv_reply.performClick();
                        comments = result.comments;
                    }

                    @Override
                    public void warning(int code, String msg) {
                        UITools.warning(ChouDetailActivity.this, "加载评论信息失败", msg);
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
                UITools.warning(ChouDetailActivity.this, "加载项目信息失败", msg);
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
        } else {
            imageLoader.loadUrlImageToView(cfProject.pics[0].url, 400, 400, R.mipmap.loading_static, R.mipmap.loading_static, ivProjectDetailHeader);
        }

        tvProjectName.setText(cfProject.title);
        tvProjectStatus.setText(CMTool.getProjectStatus(cfProject.status));
        tvProjectDesc.setText(cfProject.desc);

        CMTool.loadProjectUserAvatar(currentProject.hongId, this, ivProjectCfuserAvatar);
        progressBar.setProgress(CMTool.generateProjectProgress(cfProject));

        String progressPercent = new StringBuilder("达成度: ")
                .append(CMTool.generateProjectProgress(currentProject))
                .append("%").toString();
        tvProgressPercent.setText(progressPercent);
        npbProjectProgress.setProgress(CMTool.generateProjectProgress(currentProject));

        //根据项目需求选择性地显示人员,金额,物品
        if (currentProject.requiredPeopleAmount > 0) {
            tvAlreadyPeopleAmountLabel.setActivated(true);
            tvAlreadyPeopleAmount.setText(currentProject.alreadyPeopleAmount+"人");
            tvAlreadyPeopleAmount.setSelected(true);
        } else tvAlreadyPeopleAmount.setText("不可用");

        if (currentProject.requiredMoneyAmount > 0) {
            tvAlreadyMoneyAmountLabel.setActivated(true);
            tvAlreadyMoneyAmount.setText(Tool.fenToYuan(currentProject.alreadyMoneyAmount));
            tvAlreadyMoneyAmount.setSelected(true);
        }else tvAlreadyMoneyAmount.setText("不可用");
        if (currentProject.requiredGoodsAmount > 0) {

            tvAlreadyGoodsAmountLabel.setActivated(true);
            tvAlreadyGoodsAmount.setSelected(true);
            tvAlreadyGoodsAmount.setText(String.valueOf(currentProject.alreadyGoodsAmount));
        }else tvAlreadyGoodsAmount.setText("不可用");

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

                tvProgressPercent.setText("达成度: "+CMTool.generateProjectProgress(currentProject) + "%");
                tvAlreadyMoneyAmount.setText(Tool.fenToYuan(currentProject.alreadyMoneyAmount));
                tvAlreadyGoodsAmount.setText(String.valueOf(currentProject.alreadyGoodsAmount));
                progressBar.setProgress(CMTool.generateProjectProgress(currentProject));
                npbProjectProgress.setProgress(CMTool.generateProjectProgress(currentProject));
            }
        }
    }

    @Override
    public void onNewInvest(final CfProjectReward reward) {



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

        Gson gson = new Gson();
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(ConfirmActivity.REWARD_CONFIRM, gson.toJson(reward));
        intent.putExtra(ConfirmActivity.PROJECT_CONFIRM, gson.toJson(currentProject));
        startActivityForResult(intent, REQUEST_NEW_INVEST);
    }

    /**
     * 当众筹成功后，不从网络获取数据，直接修改本地数据<br>
     * 调整alreadyXXX的数量
     *
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
     *
     * @param comment       评论的内容
     * @param repliedUserId 回复给某User id,不是回复则传 -1
     */
    private void newComment(String comment, long repliedUserId) {
        if (Tool.isEmpty(comment)) {
            UITools.toastMsg(this, "评论内容不能为空");
            return;
        }

        if (MyApplication.getCfUser() == null) {
            UITools.toastMsg(this, "请先登录");
            return;
        }

        CfProjectCommentParam param = new CfProjectCommentParam();
        param.content = comment;
        param.projectId = currentProject.id;
        if (replyToUserId != -1) {
            param.repliedUserId = repliedUserId;
        }
        param.token = LocalDataConfig.getToken(this);
        CfProjectManager.getInstance().newCfProjectComment(param, new ManagerCallBack<CfProjectCommentResp>() {
            @Override
            public void success(CfProjectCommentResp result) {
                //UITools.toastMsg(ChouDetailActivity.this, "comment success");
                etComments.setText("");
                etComments.setHint("评论");
                softKeyboard.closeSoftKeyboard();

                //重新加载项目评论列表
                CfProjectCommentParam cfProjectCommentParam = new CfProjectCommentParam();
                cfProjectCommentParam.projectId = currentProject.id;
                CfProjectManager.getInstance().loadCfProjectComment(cfProjectCommentParam, new ManagerCallBack<CfProjectCommentResp>() {
                    @Override
                    public void success(CfProjectCommentResp result) {
                        loadDataToViews(currentProject);
                        comments = result.comments;
                        adapter.setDataComments(comments);
                    }

                    @Override
                    public void warning(int code, String msg) {
                        UITools.warning(ChouDetailActivity.this, "加载评论信息失败", msg);
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
                UITools.warning(ChouDetailActivity.this, "评论失败", msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChouDetailActivity.this);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            CfProjectComment comment = (CfProjectComment) adapter.getItem(position - 1);
            //用户点击了自己的评论/回复
            if (comment.userId == MyApplication.getCfUser().hongId) {
                //不支持删除评论/回复
                return;
            }
            etComments.setHint("回复 " + comment.userNick);
            softKeyboard.openSoftKeyboard();
            etComments.requestFocus();
            replyToUserId = comment.userId;
        } catch (Exception e) {
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        softKeyboard.unRegisterSoftKeyboardCallback();
    }
}
