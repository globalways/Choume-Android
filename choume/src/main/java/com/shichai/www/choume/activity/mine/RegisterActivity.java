package com.shichai.www.choume.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserCommon;
import com.globalways.user.nano.UserCommon.*;
import com.globalways.user.sms.nano.UserSms;
import com.outsouring.crowdfunding.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.HttpConfig;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.network.manager.ThirdPartyManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.MD5;
import com.shichai.www.choume.tools.UITools;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    //是否在注册过程（网络请求）
    private boolean isRegister  = false;

    private EditText etNick, etTel, etPassword, etSmsCode;
    private Button btnSubmit, btnRequestSmsCode;

    private String nick,tel,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initActionBar();
        setTitle("注册");
        context = this;
        initViews();
    }




    private void initViews() {
        etNick            = (EditText) findViewById(R.id.etNick);
        etTel             = (EditText) findViewById(R.id.etTel);
        etPassword        = (EditText) findViewById(R.id.etPassword);
        //短信验证码
        etSmsCode         = (EditText) findViewById(R.id.etSmsCode);
        btnSubmit         = (Button) findViewById(R.id.btnToRegister);
        btnRequestSmsCode = (Button) findViewById(R.id.btnRequestSmsCode);
        btnSubmit.setOnClickListener(this);
        btnRequestSmsCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnToRegister:
                register();
                break;
            case R.id.btnRequestSmsCode:
                smsCodeRequest();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void register(){
        nick = etNick.getText().toString().trim();
        tel  = etTel.getText().toString().trim();
        pwd  = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nick)){
            Toast.makeText(this,"昵称不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tel)){
            Toast.makeText(this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }


        final RegisterAppUserParam param = new RegisterAppUserParam();
        param.nick     = nick;
        param.tel      = tel;
        param.password = MD5.getMD5(pwd);
        isRegister = true;
        //验证短信验证码
        UserSms.VarifySMSCodeParam varifyParam = new UserSms.VarifySMSCodeParam();
        varifyParam.appId = HttpConfig.APPID;
        varifyParam.tel   = param.tel;
        varifyParam.code  = etSmsCode.getText().toString();
        ThirdPartyManager.getInstance().varifySMSCode(varifyParam, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {

                CfUserManager.getInstance().register(param, new ManagerCallBack<OutsouringCrowdfunding.RegisterCFAppUserResp>() {
                    @Override
                    public void error(Exception e) {
                        UITools.ToastServerError(context);
                        isRegister = false;
                    }

                    @Override
                    public void warning(int code, String msg) {
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                        isRegister = false;
                    }

                    @Override
                    public void success(OutsouringCrowdfunding.RegisterCFAppUserResp result) {
                        UITools.ToastMsg(context, "注册成功，正在自动登录..");
                        isRegister = false;
                        //注册成功自动登录
                        login(context, param.tel, param.password);
                    }
                });
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

    /**
     * 关闭注册界面
     */
    private void cancelRegister(){
        //已经在注册了，不能取消
        if(isRegister){
            return;
        }
        finish();
    }

    /**
     * 发送验证码请求
     */
    private void smsCodeRequest(){
        String tel = etTel.getText().toString();
        if (tel == null || tel.isEmpty()){
            return;
        }
        UserSms.SendSMSParam param = new UserSms.SendSMSParam();
        param.appId      = HttpConfig.APPID;
        param.tels       = new String[]{tel};
        param.templateId = 56890;
        param.type       = UserSms.VARIFY;
        ThirdPartyManager.getInstance().sendSMS(param, new ManagerCallBack<Common.Response>() {
            @Override
            public void success(Common.Response result) {
                UITools.ToastMsg(context, "验证码已经发送，注意查收");
                //btnRequestSmsCode.setText(String.valueOf(result.code));
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
                RegisterActivity.this.finish();
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
