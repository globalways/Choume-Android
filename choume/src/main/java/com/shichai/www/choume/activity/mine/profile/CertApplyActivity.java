package com.shichai.www.choume.activity.mine.profile;

import android.os.Bundle;
import android.view.MenuItem;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;

public class CertApplyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cert_apply);
        initActionBar();
        setTitle("提交认证");
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
