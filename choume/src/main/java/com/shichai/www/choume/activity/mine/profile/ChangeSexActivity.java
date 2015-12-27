package com.shichai.www.choume.activity.mine.profile;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.network.protoenum.UserSex;
import com.shichai.www.choume.tools.UITools;

public class ChangeSexActivity extends BaseActivity {

    private RadioGroup rgChooseSex;
    private UserSex currentSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sex);
        initActionBar();
        setTitle("选择性别");
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
        setRightButton("保存");
        rgChooseSex = (RadioGroup) findViewById(R.id.rgChooseSex);
        rgChooseSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentSelected = UserSex.codeOf(checkedId);
            }
        });
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void toChangeSex() {
    }
}
