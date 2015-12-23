package com.shichai.www.choume.activity.wealth;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class RechargeSuccessActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_ok);
        initActionBar();
        setTitle("充值成功");
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
            case R.id.bt_know:
                finish();
                break;
        }
    }
}
