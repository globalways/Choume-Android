package com.shichai.www.choume.activity.chou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectInvest;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProject;
import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserApp;
import com.google.gson.Gson;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfProjectManager;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.tools.CMTool;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;


public class ChouMemberDetailActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvCfUserName, tvInvestTime, tvInvestStatus,
            tvComments, tvRewardCount, tvRewardAmountDesc, tvTotal,
            tvRefuseInvest, tvPassInvest;
    private ImageView ivUserAvatar;

    private CfProjectInvest invest;
    private CfProject cfProject;

    //address
    private TextView tvToSelectAddrLabel;
    private RelativeLayout rlToSelectAddr;
    private TextView tvAddresName, tvAddresContact, tvAddres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_member_detail);
        initActionBar();
        setTitle("项目支持详细");
        invest = new Gson().fromJson(getIntent().getStringExtra(ChouMemberActivity.INVEST_DETAIL),
                CfProjectInvest.class);
        cfProject = new Gson().fromJson(getIntent().getStringExtra(CMTool.PROJECT),
                CfProject.class);
        initViews();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRefuseInvest:
                rejectInvest(invest);
                break;
            case R.id.tvPassInvest:
                passInvest(invest);
                break;
        }
    }

    private void initViews() {
        ivUserAvatar = (ImageView) findViewById(R.id.ivUserAvatar);

        tvCfUserName = (TextView) findViewById(R.id.tvCfUserName);
        tvInvestTime = (TextView) findViewById(R.id.tvInvestTime);
        tvInvestStatus = (TextView) findViewById(R.id.tvInvestStatus);

        tvComments = (TextView) findViewById(R.id.tvComments);
        tvRewardCount = (TextView) findViewById(R.id.tvRewardCount);
        tvRewardAmountDesc = (TextView) findViewById(R.id.tvRewardAmountDesc);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        tvRefuseInvest = (TextView) findViewById(R.id.tvRefuseInvest);
        tvRefuseInvest.setOnClickListener(this);
        tvPassInvest = (TextView) findViewById(R.id.tvPassInvest);
        tvPassInvest.setOnClickListener(this);

        //address
        tvToSelectAddrLabel = (TextView) findViewById(R.id.tvToSelectAddrLabel);
        rlToSelectAddr = (RelativeLayout) findViewById(R.id.rlToSelectAddr);
        tvAddres = (TextView) findViewById(R.id.tvAddres);
        tvAddresName = (TextView) findViewById(R.id.tvAddresName);
        tvAddresContact = (TextView) findViewById(R.id.tvAddresContact);
    }

    private void initDatas() {
        CMTool.loadAvatar(invest.investorAvatar, this, ivUserAvatar);
        tvCfUserName.setText(invest.investorNick);
        tvInvestTime.setText(Tool.formatDateTime(invest.investTime * 1000));
        tvInvestStatus.setText(CMTool.getCfProjectInvestStatus(invest.status));

        tvComments.setText(Tool.isEmpty(invest.comment)? "暂无": invest.comment);
        tvRewardCount.setText(String.valueOf(invest.rewardCount));
        tvRewardAmountDesc.setText(CMTool.getRewardAbbr(invest.rewardSupportType,
                invest.rewardAmount, cfProject.requiredGoodsName));
        tvTotal.setText(CMTool.getRewardAbbr(invest.rewardSupportType,
                invest.rewardCount * invest.rewardAmount, cfProject.requiredGoodsName));

        if (invest.addrId != 0) {
            tvToSelectAddrLabel.setVisibility(View.VISIBLE);
            rlToSelectAddr.setVisibility(View.VISIBLE);
            UserApp.GetUserAddrParam param = new UserApp.GetUserAddrParam();
            param.addrId = invest.addrId;
            UserManager.getInstance().getUserAddr(param, new ManagerCallBack<UserApp.GetUserAddrResp>() {
                @Override
                public void success(UserApp.GetUserAddrResp result) {
                    if (result != null) {
                        tvAddresName.setText(result.addr.name);
                        tvAddresContact.setText(result.addr.contact);
                        tvAddres.setText(result.addr.area + " " + result.addr.detail);
                    }
                }

                @Override
                public void warning(int code, String msg) {
                    UITools.warning(ChouMemberDetailActivity.this, "获取用户地址失败", msg);
                }

                @Override
                public void error(Exception e) {
                    UITools.toastServerError(ChouMemberDetailActivity.this);
                }
            });
        }
    }


    private void rejectInvest(final CfProjectInvest invest) {
        dialog.show();
        OutsouringCrowdfunding.RejectCfProjectInvestParam param = new OutsouringCrowdfunding.RejectCfProjectInvestParam();
        param.investId = invest.id;
        param.token = LocalDataConfig.getToken(this);
        CfProjectManager.getInstance().rejectCfProjectInvest(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                dialog.dismiss();
                UITools.toastMsg(ChouMemberDetailActivity.this, "拒绝成功");
            }

            @Override
            public void warning(int code, String msg) {
                dialog.dismiss();
                UITools.warning(ChouMemberDetailActivity.this, "拒绝失败", msg);
            }

            @Override
            public void error(Exception e) {
                dialog.dismiss();
                UITools.toastServerError(ChouMemberDetailActivity.this);
            }
        });
    }



    private void passInvest(final CfProjectInvest invest) {
        dialog.show();
        OutsouringCrowdfunding.PassCfProjectInvestParam param = new OutsouringCrowdfunding.PassCfProjectInvestParam();
        param.investId = invest.id;
        param.token = LocalDataConfig.getToken(this);
        CfProjectManager.getInstance().passCfProjectInvest(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                dialog.dismiss();
                UITools.toastMsg(ChouMemberDetailActivity.this, "通过投资成功");
            }

            @Override
            public void warning(int code, String msg) {
                dialog.dismiss();
                UITools.warning(ChouMemberDetailActivity.this, "通过投资失败", msg);
            }

            @Override
            public void error(Exception e) {
                dialog.dismiss();
                UITools.toastServerError(ChouMemberDetailActivity.this);
            }
        });
    }
}
