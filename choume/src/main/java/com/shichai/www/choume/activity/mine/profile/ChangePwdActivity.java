package com.shichai.www.choume.activity.mine.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserApp;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.MainActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.network.manager.UserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.MD5;
import com.shichai.www.choume.tools.UITools;

public class ChangePwdActivity extends BaseActivity {

    private EditText etOldPwd, etNewPwd, etRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initActionBar();
        setTitle("修改密码");
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
        setRightButton("提交");
        etNewPwd = (EditText) findViewById(R.id.etNewPwd);
        etRepeat = (EditText) findViewById(R.id.etRepeat);
        etOldPwd = (EditText) findViewById(R.id.etOldPwd);
        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toChangePwd();
            }
        });
    }

    private void toChangePwd() {
        String oldPwd = etOldPwd.getText().toString().trim();
        final String newPwd = etNewPwd.getText().toString().trim();
        String repeat = etRepeat.getText().toString().trim();
        if (oldPwd.isEmpty() || newPwd.isEmpty() || repeat.isEmpty())
            return;
        if (!newPwd.equals(repeat)) {
            UITools.toastMsg(this, "两次输入的密码不一致");
            return;
        }
        UserApp.ChangePasswordParam passwordParam = new UserApp.ChangePasswordParam();
        passwordParam.passwordNew = MD5.getMD5(newPwd);
        passwordParam.passwordOld = MD5.getMD5(oldPwd);
        passwordParam.token = LocalDataConfig.getToken(this);
        UserManager.getInstance().changePassword(passwordParam, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                //logout
                logout();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.toastMsg(ChangePwdActivity.this, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChangePwdActivity.this);
            }
        });
    }

    private void logout(){
        UserCommon.LogoutParam logoutParam = new UserCommon.LogoutParam();
        logoutParam.token = LocalDataConfig.getToken(this);
        CfUserManager.getInstance().logoutApp(logoutParam, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                LocalDataConfig.logout(ChangePwdActivity.this);
                MyApplication.setCfUser(null);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.isLogin = false;
                startActivity(intent);
                //ChangePwdActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.toastMsg(ChangePwdActivity.this, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(ChangePwdActivity.this);
            }
        });
    }


}
