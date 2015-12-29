package com.shichai.www.choume.activity.wealth;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class RechargeSuccessActivity extends BaseActivity implements View.OnClickListener{

    public static final String ISSUCCESS = "isSuccess";

    private TextView tvResult;
    private ImageView ivResult;
    private boolean isSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_ok);
        initActionBar();
        tvResult = (TextView) findViewById(R.id.tvResult);
        ivResult = (ImageView) findViewById(R.id.ivResult);
        isSuccess = getIntent().getBooleanExtra(ISSUCCESS,false);
        if (isSuccess){
            setTitle("充值成功");
        }else {
            setTitle("充值失败");
            ivResult.setImageResource(R.mipmap.ico_recharge_not_ok);
            tvResult.setText("充值失败");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_know:
                finish();
                break;
        }
    }
}
