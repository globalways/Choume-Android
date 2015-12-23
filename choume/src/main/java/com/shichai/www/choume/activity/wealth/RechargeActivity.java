package com.shichai.www.choume.activity.wealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener{
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
                startActivity(new Intent(this,RechargeSuccessActivity.class));
                finish();
                break;
        }
    }
}
