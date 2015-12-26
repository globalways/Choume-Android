package com.shichai.www.choume.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.user.nano.UserCommon;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.MD5;
import com.shichai.www.choume.tools.UITools;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Context context = this;
    private EditText etTel, etPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initActionBar();
        setTitle("登录");
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
        etTel      = (EditText) findViewById(R.id.etTel);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin   = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                UITools.ToastMsg(context, "login...");
                login(context, etTel.getText().toString(), MD5.getMD5(etPassword.getText().toString()));

                break;
        }
    }

    /**
     *
     * @param context
     * @param tel
     * @param pwd md5加密后的
     */
    private void login(final Context context, final String tel, final String pwd) {
        UserCommon.LoginAppParam loginAppParam = new UserCommon.LoginAppParam();
        loginAppParam.password = pwd;
        loginAppParam.username = tel;
        CfUserManager.getInstance().login(loginAppParam, new ManagerCallBack<OutsouringCrowdfunding.LoginCFAppResp>() {
            @Override
            public void success(OutsouringCrowdfunding.LoginCFAppResp result) {
                UITools.ToastMsg(context,"登录成功");
                //跳转
                MyApplication.setCfUser(result.cfUser);
                LocalDataConfig.setToken(context, result.token);
                LocalDataConfig.setPwd(context, pwd);
                LocalDataConfig.setTel(context, tel);
                setResult(Activity.RESULT_OK);
                LoginActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                setResult(Activity.RESULT_CANCELED);
                UITools.ToastMsg(context, msg);
            }

            @Override
            public void error(Exception e) {
                setResult(Activity.RESULT_CANCELED);
                UITools.ToastServerError(context);
            }
        });
    }


}
