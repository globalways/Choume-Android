package com.shichai.www.choume.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.outsouring.crowdfunding.R;

/**
 * Created by HeJianjun on 2015/12/22.
 */
public class IndividualActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual);
        initActionBar();
        setTitle("个人设置");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
