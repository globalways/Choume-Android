package com.shichai.www.choume.activity.mine;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.user.nano.UserCommon;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.HttpConfig;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.UITools;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private Context context = this;
    private EditText etTel, etPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
                login(context, etTel.getText().toString(), etPassword.getText().toString());
                break;
        }
    }

    public static void login(final Context context, final String tel, final String pwd) {
        UserCommon.LoginAppParam loginAppParam = new UserCommon.LoginAppParam();
        loginAppParam.password = pwd;
        loginAppParam.username = tel;
        CfUserManager.getInstance().login(loginAppParam, new ManagerCallBack<OutsouringCrowdfunding.LoginCFAppResp>() {
            @Override
            public void success(OutsouringCrowdfunding.LoginCFAppResp result) {
                UITools.ToastMsg(context,"登录成功");
                //跳转
                MyApplication.cfUser = result.cfUser;
                LocalDataConfig.setToken(context, result.token);
                LocalDataConfig.setPwd(context, pwd);
                LocalDataConfig.setTel(context, tel);

            }

            @Override
            public void warning(int code, String msg) {
                UITools.ToastMsg(context, msg);
            }

            @Override
            public void error(Exception e) {
                UITools.ToastServerError(context);
            }
        });
    }


}
