package com.shichai.www.choume.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView imageViewWelcome = (ImageView) findViewById(R.id.imageWelcome);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(2000);
        imageViewWelcome.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //检查版本更新

                //登录
                toLogin();

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //toLogin();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void toLogin() {
        String tel = LocalDataConfig.getTel(this);
        if (Tool.isEmpty(tel)) {
            UITools.jumpToMainActivity(this, false);
            finish();
            return;
        }
        String pwd = LocalDataConfig.getPwd(this);
        if (Tool.isEmpty(pwd)) {
            UITools.jumpToMainActivity(this, false);
            finish();
            return;
        }
        UserCommon.LoginAppParam loginAppParam = new UserCommon.LoginAppParam();
        loginAppParam.password = pwd;
        loginAppParam.username = tel;
        CfUserManager.getInstance().login(loginAppParam, new ManagerCallBack<OutsouringCrowdfunding.LoginCFAppResp>() {
            @Override
            public void success(OutsouringCrowdfunding.LoginCFAppResp result) {
                MyApplication.setCfUser(result.cfUser);
                UITools.jumpToMainActivity(WelcomeActivity.this, true);
                WelcomeActivity.this.finish();
            }

            @Override
            public void warning(int code, String msg) {
                UITools.toastMsg(WelcomeActivity.this, msg);
                UITools.jumpToMainActivity(WelcomeActivity.this, false);
                WelcomeActivity.this.finish();
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(WelcomeActivity.this);
                UITools.jumpToMainActivity(WelcomeActivity.this, false);
                WelcomeActivity.this.finish();
            }
        });
    }
}
