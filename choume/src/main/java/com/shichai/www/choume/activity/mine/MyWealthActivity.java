package com.shichai.www.choume.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.wealth.RechargeActivity;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class MyWealthActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wealth);
        initActionBar();
        setTitle("我的财富");
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
}
