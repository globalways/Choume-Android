package com.shichai.www.choume.activity.chou;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;

/**
 * Created by HeJianjun on 2015/12/28.
 */
public class ChouManagerActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_manager);
        initActionBar();
        setTitle("项目管理");
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
            case R.id.tv_manager:
                startActivity(new Intent(this,ChouMemberActivity.class));
                break;
            case R.id.tv_support_way:
                startActivity(new Intent(this,ChouSupportWayActivity.class));
                break;
        }
    }
}
