package com.shichai.www.choume.activity.wealth;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import com.globalways.user.pingpp.nano.UserPingpp;
import com.globalways.user.wallet.nano.UserWallet;
import com.globalways.user.wallet.nano.UserWalletCommon.UserWalletHistory;
import com.outsouring.crowdfunding.R;
//import com.pingplusplus.android.PaymentActivity;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.network.HttpConfig;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.ThirdPartyManager;
import com.shichai.www.choume.network.manager.WalletManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.UITools;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener{

    private Context context = this;
    private static final int REQUEST_CODE_PAYMENT = 1;
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付宝支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    private Button btnToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initActionBar();
        setTitle("充值");
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
//                startActivity(new Intent(this,RechargeSuccessActivity.class));
//                finish();
                toPay();
                break;
        }
    }

    private void toPay() {

        UserWallet.PrepareUserWalletRechargeParam param = new UserWallet.PrepareUserWalletRechargeParam();
        param.amount = 1;
        param.appId  = HttpConfig.APPID;
        param.token  = LocalDataConfig.getToken(this);
        WalletManager.getInstance().prepareUserWalletRecharge(param, new ManagerCallBack<UserWallet.PrepareUserWalletRechargeResp>() {
            @Override
            public void success(UserWallet.PrepareUserWalletRechargeResp result) {
//                Log.i(UITools.TAGW, "success: prepareUserWalletRecharge");
                pingpp(result.history);
            }

            @Override
            public void warning(int code, String msg) {
                UITools.ToastMsg(context, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.ToastServerError(context);
            }
        });
    }

    private void pingpp(UserWalletHistory history){
        UserPingpp.PingppChargeParam pingppChargeParam = new UserPingpp.PingppChargeParam();
        pingppChargeParam.appId   = HttpConfig.APPID;
        pingppChargeParam.orderId = history.orderId;
        pingppChargeParam.channel = CHANNEL_ALIPAY;
        pingppChargeParam.subject = history.subject;
        pingppChargeParam.body    = history.subject;
        pingppChargeParam.amount  = history.amount;
        pingppChargeParam.token   = LocalDataConfig.getToken(this);
        ThirdPartyManager.getInstance().pingppCharge(pingppChargeParam, new ManagerCallBack<UserPingpp.PingppChargeResp>() {
            @Override
            public void success(UserPingpp.PingppChargeResp result) {
                Intent intent = new Intent();
                String packageName = getPackageName();
                ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
                intent.setComponent(componentName);
//                intent.putExtra(PaymentActivity.EXTRA_CHARGE, result.charge);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);
            }

            @Override
            public void warning(int code, String msg) {
                UITools.ToastMsg(context, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.ToastServerError(context);
            }
        });
    }

}
