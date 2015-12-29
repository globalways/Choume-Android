package com.shichai.www.choume.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.globalways.user.wallet.nano.UserWallet;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.wealth.RechargeActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.WalletManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyWealthActivity extends BaseActivity implements View.OnClickListener{

    private TextView tv_balance, tv_jifen, tv_choubi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wealth);
        initActionBar();
        setTitle("我的财富");
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatas();
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
            case R.id.tv_recharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
            case R.id.tv_get_cash:
                Toast.makeText(this,"暂无体现功能",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initViews() {
        tv_balance = (TextView) findViewById(R.id.tv_balance);
        tv_choubi = (TextView) findViewById(R.id.tv_choubi);
        tv_jifen = (TextView) findViewById(R.id.tv_jifen);
    }

    private void loadDatas(){

        tv_choubi.setText(String.valueOf(MyApplication.getCfUser().coin));
        tv_jifen.setText(String.valueOf(MyApplication.getCfUser().point));

        UserWallet.GetUserWalletParam param = new UserWallet.GetUserWalletParam();
        param.token = LocalDataConfig.getToken(this);
        WalletManager.getInstance().getUserWallet(param, new ManagerCallBack<UserWallet.GetUserWalletResp>() {
            @Override
            public void success(UserWallet.GetUserWalletResp result) {
                tv_balance.setText(Tool.fenToYuan(result.wallet.amount));
            }

            @Override
            public void warning(int code, String msg) {
                UITools.toastMsg(MyWealthActivity.this, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(MyWealthActivity.this);
            }
        });
    }
}
