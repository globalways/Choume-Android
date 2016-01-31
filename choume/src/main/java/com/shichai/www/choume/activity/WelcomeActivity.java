package com.shichai.www.choume.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;
import com.google.gson.Gson;
import com.shichai.www.choume.activity.mine.MyMessageActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.network.rongcloud.RCMessage;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

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
                //UITools.jumpToMainActivity(WelcomeActivity.this, true);
                //WelcomeActivity.this.finish();
                //登陆成功后,连接融云消息服务
                connectToRC();
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

    /**
     * 连接融云
     */
    private void connectToRC() {
        OutsouringCrowdfunding.GetRCCFUserTokenParam param = new OutsouringCrowdfunding.GetRCCFUserTokenParam();
        param.token=LocalDataConfig.getToken(this);
        CfUserManager.getInstance().getRCCFUserToken(param, new ManagerCallBack<OutsouringCrowdfunding.GetRCCFUserTokenResp>() {
            @Override
            public void success(OutsouringCrowdfunding.GetRCCFUserTokenResp result) {
                String rcToken = result.rcToken;
                RongIM.setOnReceiveMessageListener(new MyRCMessageListener());
                RongIM.connect(rcToken, new RongIMClient.ConnectCallback() {

                    @Override
                    public void onSuccess(String s) {
                        MyApplication.setIsRCConnected(true);
                        UITools.jumpToMainActivity(WelcomeActivity.this, true);
                        WelcomeActivity.this.finish();
                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        UITools.toastMsg(WelcomeActivity.this, "消息服务异常(rcError:"+errorCode+")");
                        WelcomeActivity.this.finish();
                    }
                    @Override
                    public void onTokenIncorrect() {
                        UITools.toastMsg(WelcomeActivity.this, "消息服务异常(rcError:TokenIncorrect)");
                        WelcomeActivity.this.finish();
                    }
                });
            }

            @Override
            public void warning(int code, String msg) {
                UITools.warning(WelcomeActivity.this, "消息服务异常", msg);
                WelcomeActivity.this.finish();
            }

            @Override
            public void error(Exception e) {
                UITools.toastServerError(WelcomeActivity.this);
                WelcomeActivity.this.finish();
            }
        });
    }

    private class MyRCMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param left    剩余未拉取消息数目。
         * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
         */
        @Override
        public boolean onReceived(Message message, int left) {
            int notificationId = 100;
            OutsouringCrowdfunding.CfMessage cfMessage = new Gson().fromJson(((RCMessage) message.getContent()).getContent(), OutsouringCrowdfunding.CfMessage.class);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(WelcomeActivity.this)
                            .setSmallIcon(R.mipmap.choume)
                            .setContentTitle(cfMessage.title)
                            .setContentText(cfMessage.content)
                            .setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_SOUND);
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(WelcomeActivity.this, MyMessageActivity.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(WelcomeActivity.this);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MyMessageActivity.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            //notificationId notify allows you to update the notification later on.
            mNotificationManager.notify(notificationId, mBuilder.build());
            return true;
        }
    }
}
