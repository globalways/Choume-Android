package com.shichai.www.choume.activity.wealth;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.globalways.choume.R;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.mine.MyWealthActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

public class ExchangeCBActivity extends BaseActivity {

    private EditText etExchangeAmount;
    private Button btnToExchange;
    private TextView tv_balance;
    private long balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_cb);
        initActionBar();
        setTitle("充值");

        tv_balance = (TextView) findViewById(R.id.tv_balance);
        etExchangeAmount = (EditText) findViewById(R.id.etExchangeAmount);
        btnToExchange = (Button) findViewById(R.id.btnToExchange);
        btnToExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toExchangeCB();
            }
        });

        balance = getIntent().getLongExtra(MyWealthActivity.BALANCE, 0);
        tv_balance.setText(Tool.fenToYuan(balance));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toExchangeCB() {
        String cbAmountStr = etExchangeAmount.getText().toString().trim();
        if (Tool.isEmpty(cbAmountStr)) {
            UITools.toastMsg(this,"请输入要兑换的筹币数");
            return;
        }
        long cbAmount = Long.parseLong(cbAmountStr);
        if (cbAmount > 0) {
            final OutsouringCrowdfunding.CfUserCBExchangeParam param = new OutsouringCrowdfunding.CfUserCBExchangeParam();
            param.password = LocalDataConfig.getPwd(this);
            //1 rmb = 100 cb,提交RMB 单位是分 刚好 param.rmb = cb
            param.rmb = cbAmount;
            param.token = LocalDataConfig.getToken(this);
            CfUserManager.getInstance().cfUserCBExchange(param, new ManagerCallBack<OutsouringCrowdfunding.CfUserCBExchangeResp>() {
                @Override
                public void success(OutsouringCrowdfunding.CfUserCBExchangeResp result) {
                    UITools.toastMsg(ExchangeCBActivity.this, "兑换筹币成功");
                    //修改本地数据
                    MyApplication.getCfUser().coin += param.rmb;
                    ExchangeCBActivity.this.finish();
                }

                @Override
                public void warning(int code, String msg) {
                    UITools.warning(ExchangeCBActivity.this,"兑换筹币失败", msg);
                }

                @Override
                public void error(Exception e) {
                    UITools.toastServerError(ExchangeCBActivity.this);
                }
            });
        }
    }
}
