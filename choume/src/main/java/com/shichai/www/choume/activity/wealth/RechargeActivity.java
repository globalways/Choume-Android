package com.shichai.www.choume.activity.wealth;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.globalways.user.pingpp.nano.UserPingpp;
import com.globalways.user.wallet.nano.UserWallet;
import com.globalways.user.wallet.nano.UserWalletCommon.UserWalletHistory;
import com.outsouring.crowdfunding.R;
//import com.pingplusplus.android.PaymentActivity;
import com.pingplusplus.android.PaymentActivity;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.network.HttpConfig;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.ThirdPartyManager;
import com.shichai.www.choume.network.manager.WalletManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener{

    private Context context = this;
    private Button bt_recharge;
    private EditText etRechargeAmount;
    private TextView tv_balance;
    private RadioGroup rgPayChannel;
    private static final int REQUEST_CODE_PAYMENT = 1;
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付宝支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private String selectedChannel = null;
    private Button btnToPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initActionBar();
        setTitle("充值");
        bt_recharge = (Button) findViewById(R.id.bt_recharge);
        etRechargeAmount = (EditText) findViewById(R.id.etRechargeAmount);
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        rgPayChannel = (RadioGroup) findViewById(R.id.rgPayChannel);
        rgPayChannel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbChannelAlipay)
                    selectedChannel = CHANNEL_ALIPAY;
                if (checkedId == R.id.rbChannelWX)
                    selectedChannel = CHANNEL_WECHAT;
            }
        });
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
            case R.id.bt_recharge:
                if(bt_recharge.getText().toString().equals("去付款"))
                    toPay();
                break;
        }
    }

    private void toPay() {
        String amountStr = etRechargeAmount.getText().toString().trim();
        if (Tool.isEmpty(amountStr))
            return;
        if (selectedChannel == null) {
            UITools.toastMsg(this, "请选择支付方式");
            return;
        }
        long amount = Tool.yuanToFen(amountStr);
        UserWallet.PrepareUserWalletRechargeParam param = new UserWallet.PrepareUserWalletRechargeParam();
        param.amount = amount;
        param.appId  = HttpConfig.APPID;
        param.token  = LocalDataConfig.getToken(this);
        WalletManager.getInstance().prepareUserWalletRecharge(param, new ManagerCallBack<UserWallet.PrepareUserWalletRechargeResp>() {
            @Override
            public void success(UserWallet.PrepareUserWalletRechargeResp result) {
                pingpp(result.history);
            }

            @Override
            public void warning(int code, String msg) {
                UITools.toastMsg(context, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(context);
            }
        });
    }

    private void pingpp(UserWalletHistory history){
        UserPingpp.PingppChargeParam pingppChargeParam = new UserPingpp.PingppChargeParam();
        pingppChargeParam.appId   = HttpConfig.APPID;
        pingppChargeParam.orderId = history.orderId;
        pingppChargeParam.channel = selectedChannel;
        pingppChargeParam.subject = history.subject;
        pingppChargeParam.body    = history.subject;
        pingppChargeParam.amount  = history.amount;
        pingppChargeParam.token   = LocalDataConfig.getToken(this);
        ThirdPartyManager.getInstance().pingppCharge(pingppChargeParam, new ManagerCallBack<UserPingpp.PingppChargeResp>() {
            @Override
            public void success(final UserPingpp.PingppChargeResp result) {
                bt_recharge.setText("等待支付结果...");
                Intent intent = new Intent();
                String packageName = getPackageName();
                ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                intent.setComponent(componentName);
                intent.putExtra(PaymentActivity.EXTRA_CHARGE, result.charge);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            }

            @Override
            public void warning(int code, String msg) {
                UITools.toastMsg(context, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(context);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 *
                 * 如果是银联渠道返回 invalid，调用 UPPayAssistEx.installUPPayPlugin(this); 安装银联安全支付控件。
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                //showMsg(result, errorMsg, extraMsg)
                Intent intent = new Intent(this, RechargeSuccessActivity.class);
                switch (result) {
                    case "success":
                        intent.putExtra(RechargeSuccessActivity.ISSUCCESS, true);
                        startActivity(intent);
                        this.finish();
                        break;
                    default:
                        intent.putExtra(RechargeSuccessActivity.ISSUCCESS, false);
                        startActivity(intent);
                        this.finish();
                        UITools.toastMsg(this, result + "  " + errorMsg + "  " + extraMsg);
                        break;
                }
            }
        }
    }

}
