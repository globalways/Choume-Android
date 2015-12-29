package com.shichai.www.choume.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.shichai.www.choume.tools.Utils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    private EditText etTel, etPassword;
    private Button btnLogin;

    private String tel,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
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
                tel = etTel.getText().toString().trim();
                pwd = etPassword.getText().toString().trim();
                login();

                break;
        }
    }

    /**
     */
    private void login() {
        if (TextUtils.isEmpty(tel)){
            Toast.makeText(this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Utils.isNumber(tel)|| !tel.startsWith("1")){
            Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            return;
        }
        UITools.toastMsg(this, "login...");

        pwd = MD5.getMD5(pwd);
        UserCommon.LoginAppParam loginAppParam = new UserCommon.LoginAppParam();
        loginAppParam.password = pwd;
        loginAppParam.username = tel;
        CfUserManager.getInstance().login(loginAppParam, new ManagerCallBack<OutsouringCrowdfunding.LoginCFAppResp>() {
            @Override
            public void success(OutsouringCrowdfunding.LoginCFAppResp result) {
                UITools.toastMsg(context, "登录成功");
                //跳转
                MyApplication.setCfUser(result.cfUser);
                LocalDataConfig.setToken(context, result.token);
                LocalDataConfig.setPwd(context,pwd);
                LocalDataConfig.setTel(context,tel);
                setResult(Activity.RESULT_OK);
                LoginActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                setResult(Activity.RESULT_CANCELED);
                UITools.toastMsg(context, msg);
            }

            @Override
            public void error(Exception e) {
                setResult(Activity.RESULT_CANCELED);
                UITools.toastServerError(context);
            }
        });
    }


}
