package com.shichai.www.choume.activity.chou;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectReward;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.google.gson.Gson;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.HttpStatus;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.UITools;

public class ConfirmActivity extends BaseActivity implements View.OnClickListener{

    public static final String REWARD_CONFIRM = "reward_confirm";
    public static final String PROJECT_CONFIRM = "project_confirm";
    public static final String REWARD_SUPPORT_TYPE = "reward_support_type";
    public static final String REWARD_AMOUNT = "reward_amount";

    private TextView tvNumber, tvRewardAbbr, tvTotal;
    private Button btnLess, btnAdd;
    private EditText etComments;
    private CfProject currentProject;
    private CfProjectReward currentReward;

    private int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        initActionBar();
        setTitle("参与项目");
        initViews();
        Gson gson = new Gson();
        String rewardJson = getIntent().getStringExtra(REWARD_CONFIRM);
        String projectJson = getIntent().getStringExtra(PROJECT_CONFIRM);
        currentReward = gson.fromJson(rewardJson, CfProjectReward.class);
        currentProject = gson.fromJson(rewardJson, CfProject.class);

        initDatas();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        setRightButton("提交");
        bt_right.setOnClickListener(this);
        tvRewardAbbr = (TextView) findViewById(R.id.tvRewardAbbr);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        btnLess = (Button) findViewById(R.id.btnLess);
        btnLess.setOnClickListener(this);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        etComments = (EditText) findViewById(R.id.etComments);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_right:
                toNewInvest();
                break;
            case R.id.btnAdd:
                num += 1;
                tvNumber.setText(String.valueOf(num));
                tvTotal.setText(getRewardAbbr(currentReward, num * currentReward.amount));
                break;
            case R.id.btnLess:
                if (num > 1){
                    num -= 1;
                }
                tvNumber.setText(String.valueOf(num));
                tvTotal.setText(getRewardAbbr(currentReward, num * currentReward.amount));
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initDatas() {
        tvRewardAbbr.setText(getRewardAbbr(currentReward, -1));
        tvTotal.setText(getRewardAbbr(currentReward, num * currentReward.amount));
    }

    private String getRewardAbbr(CfProjectReward reward, long amount) {
        String abbr = "";
        long newAmount = amount > 0 ? amount: reward.amount;
        switch (reward.supportType) {
            case OutsouringCrowdfunding.MONEY_CFPST:
                abbr = newAmount+" 筹币";
                break;
            case OutsouringCrowdfunding.PEOPLE_CFPST:
                abbr = "人员"+newAmount+"名";
                break;
            case OutsouringCrowdfunding.GOODS_CFPST:
                abbr = "物品［"+currentProject.requiredGoodsName+"］ "+newAmount+"件";
                break;
            case OutsouringCrowdfunding.EQUITY_CFPST:
                abbr = "入股"+newAmount;
                break;
            case OutsouringCrowdfunding.INVALID_CFPST:
                abbr = "未知";
                break;
        }
        return abbr;
    }


    private void toNewInvest() {

        //如果用户没那么多筹币就不能参与
        if (currentReward.supportType == OutsouringCrowdfunding.MONEY_CFPST && MyApplication.getCfUser().coin < currentReward.amount) {
            UITools.toastMsg(this, "您的筹币不足，请先充值兑换");
            return;
        }

        OutsouringCrowdfunding.NewCfProjectInvestParam projectInvestParam = new OutsouringCrowdfunding.NewCfProjectInvestParam();
        projectInvestParam.cfProjectId = currentProject.id;
        projectInvestParam.count = num;
        projectInvestParam.comment = etComments.getText().toString().trim();
        projectInvestParam.token = LocalDataConfig.getToken(ConfirmActivity.this);
        projectInvestParam.cfProjectRewardId = currentReward.id;
        CfProjectManager.getInstance().newCfProjectInvest(projectInvestParam, new ManagerCallBack<OutsouringCrowdfunding.NewCfProjectInvestResp>() {
            @Override
            public void success(OutsouringCrowdfunding.NewCfProjectInvestResp result) {

                Intent intent = new Intent();
                intent.putExtra(REWARD_AMOUNT, num * currentReward.amount);
                intent.putExtra(REWARD_SUPPORT_TYPE, currentReward.supportType);
                setResult(Activity.RESULT_OK, intent);

                //如果是需要付钱的方式，则立马付钱
                if (result.invest.coinPay > 0) {
                    UITools.toastMsg(ConfirmActivity.this, "参与项目成功，支付...");
                    OutsouringCrowdfunding.CfUserCBConsumeParam param = new OutsouringCrowdfunding.CfUserCBConsumeParam();
                    param.coin = result.invest.coinPay;
                    param.orderId = result.invest.orderId;
                    param.token = LocalDataConfig.getToken(ConfirmActivity.this);
                    CfUserManager.getInstance().cfUserCBConsume(param, new ManagerCallBack<OutsouringCrowdfunding.CfUserCBConsumeResp>() {
                        @Override
                        public void success(OutsouringCrowdfunding.CfUserCBConsumeResp result) {
                            //result.history.
                            //UITools.toastMsg(ChouDetailActivity.this, "支付成功");
                            //修改本地用户筹币数目
                            MyApplication.getCfUser().coin -= result.history.coin;
                            ConfirmActivity.this.finish();
                        }

                        @Override
                        public void warning(int code, String msg) {
                            UITools.warning(ConfirmActivity.this, "支付筹币失败", HttpStatus.codeOf(code).desc);
                        }

                        @Override
                        public void error(Exception e) {
                            UITools.toastServerError(ConfirmActivity.this);
                        }
                    });
                } else {
                    UITools.toastMsg(ConfirmActivity.this, "参与项目成功");
                    ConfirmActivity.this.finish();
                    //重新加载数据
                    //refreshProjectDatas(reward);
                }
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ConfirmActivity.this, "参与项目失败", HttpStatus.codeOf(code).desc);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ConfirmActivity.this);
            }
        });
    }

}
