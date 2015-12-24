package com.shichai.www.choume.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.View;
import android.widget.RelativeLayout;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.mine.profile.CertApplyActivity;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class IndividualActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlToCert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        initActionBar();
        setTitle("个人设置");
        initViews();

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
        rlToCert = (RelativeLayout) findViewById(R.id.rlToCert);
        rlToCert.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlToCert:
                startActivity(new Intent(IndividualActivity.this, CertApplyActivity.class));
                break;
            default:break;
        }
    }
}
