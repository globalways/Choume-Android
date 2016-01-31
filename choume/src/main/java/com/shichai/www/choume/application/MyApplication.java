package com.shichai.www.choume.application;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shichai.www.choume.network.rongcloud.RCMessage;

import io.rong.imkit.RongIM;

/**
 * Created by HeJianjun on 2015/11/17.
 * 继承MultiDexApplication解决集成融云LIB冲突
 */
public class MyApplication extends MultiDexApplication {

    private static OutsouringCrowdfunding.CfUser cfUser;

    private static boolean isRCConnected = false;

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        RongIM.registerMessageType(RCMessage.class);
        initImageLoader(this);
    }
    private void initImageLoader(Context context) {
        // TODO Auto-generated method stub
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCacheSize(32 * 1024 * 1024).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
    public static class Config {
        public static final boolean DEVELOPER_MODE = false;
    }
    public static void setCfUser(OutsouringCrowdfunding.CfUser cfUser) {
        MyApplication.cfUser = cfUser;
    }

    public static OutsouringCrowdfunding.CfUser getCfUser() {

        return cfUser;
    }

    public static boolean isRCConnected() {
        return isRCConnected;
    }

    public static void setIsRCConnected(boolean isRCConnected) {
        MyApplication.isRCConnected = isRCConnected;
    }
}
