package com.shichai.www.choume.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.user.nano.UserCommon;
import com.globalways.choume.R;
import com.google.common.cache.Weigher;
import com.google.gson.Gson;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.shichai.www.choume.activity.mine.MyMessageActivity;
import com.shichai.www.choume.application.MyApplication;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.manager.CfUserManager;
import com.shichai.www.choume.network.rongcloud.RCMessage;
import com.shichai.www.choume.tools.LocalDataConfig;
import com.shichai.www.choume.tools.Tool;
import com.shichai.www.choume.tools.UITools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class WelcomeActivity extends Activity {

    private UpdateManagerListener updateManagerListener;
    private PackageInfo packageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView imageViewWelcome = (ImageView) findViewById(R.id.imageWelcome);

        try {
            packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        updateManagerListener = new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {
                toLogin();
            }

            @Override
            public void onUpdateAvailable(final String s) {
                //Pgy需要下载后主动调用这个方法才更新本地版本信息
                //UpdateManagerListener.updateLocalBuildNumber(s);或者使用这个,但是不能改进度条(丑的一笔)
                //startDownloadTask(WelcomeActivity.this, appBean.getDownloadURL());
                //由于这个机制太蠢,就通过自己比对versionCode和versionName作为是否更新依据
                final AppBean appBean = getAppBeanFromString(s);
                //判断是否更新 比对versionCode和versionName
                if (String.valueOf(packageInfo.versionCode).equals(appBean.getVersionCode()) &&
                        packageInfo.versionName.equals(appBean.getVersionName())) {
                    //不用更新
                    toLogin();
                } else {
                    MaterialDialog.Builder builder = new MaterialDialog.Builder(WelcomeActivity.this)
                            .title("检测到有更新")
                            .content(appBean.getReleaseNote()+"\n现在下载安装或稍后在 [系统设置] 中检查更新")
                            .positiveText("下载")
                            .positiveColorRes(R.color.cmblue_11a2ff)
                            .neutralText("暂不更新")
                            .neutralColorRes(R.color.cmyellow)
                            .canceledOnTouchOutside(false);
                    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            //调用浏览器下载更新
                            Uri uri = Uri.parse(appBean.getDownloadURL());
                            Intent it = new Intent(Intent.ACTION_VIEW, uri);
                            WelcomeActivity.this.startActivity(it);
                            toLogin();
                        }
                    }).onNeutral(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            toLogin();
                        }
                    }).show();
                }
            }
        };

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(2000);
        imageViewWelcome.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //检查版本更新
                PgyUpdateManager.register(WelcomeActivity.this, updateManagerListener);
                //登录
                //toLogin();

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
        param.token = LocalDataConfig.getToken(this);
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
                        UITools.toastMsg(WelcomeActivity.this, "消息服务异常(rcError:" + errorCode + ")");
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

    private class UpdateTask extends AsyncTask<String, Void, Void> {

        String pathTo = "/mnt/sdcard/Download/";
        String fileName = "Choume.apk";

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                File file = new File(pathTo);
                file.mkdirs();
                File outputFile = new File(file, fileName);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                FileOutputStream fos = new FileOutputStream(outputFile);

                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.close();
                is.close();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(pathTo + fileName)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                WelcomeActivity.this.startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
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
