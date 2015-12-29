package com.shichai.www.choume.activity.mine.profile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import com.globalways.choume.R;
import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserApp;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.network.protoenum.UserSex;
import com.shichai.www.choume.tools.LocalDataConfig;
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
                switch (checkedId) {
                    case R.id.rbMale: currentSelected = UserSex.MALE;break;
                    case R.id.rbFemale: currentSelected = UserSex.FAMALE;break;
                }
            }
        });
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toChangeSex();
            }
        });
    }

    private void toChangeSex() {

        final UserApp.ChangeUserSexParam param = new UserApp.ChangeUserSexParam();
        param.sex = currentSelected.code;
        param.token = LocalDataConfig.getToken(ChangeSexActivity.this);
        UserManager.getInstance().changeUserSex(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                ChangeSexActivity.this.finish();
                MyApplication.getCfUser().user.sex = param.sex;
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(ChangeSexActivity.this, "修改性别失败",msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChangeSexActivity.this);
            }
        });
    }
}
