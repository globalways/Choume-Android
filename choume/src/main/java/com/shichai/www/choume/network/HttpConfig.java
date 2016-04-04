package com.shichai.www.choume.network;

/**
 * Created by wyp on 15/12/22.
 */
public class HttpConfig {

    //配置是否是测试服务器
    private static boolean isTest = true;

    private static final int CM_RPC_TEST_PORT = 8089; //测试端口
    private static final int CM_RPC_NORMAL_PORT = 9089; //正式端口

    private static final int HT_RPC_TEST_PORT = 6061;//测试端口
    private static final int HT_RPC_NORMAL_PORT = 4061;//正式端口


    public static final String CMRPCHost  = "121.42.48.12";
    public static final int CMRPCPort = isTest ? CM_RPC_TEST_PORT : CM_RPC_NORMAL_PORT;
    public static final String HTRPCHost = "121.42.48.12";
    public static final int HTRPCPort = isTest ? HT_RPC_TEST_PORT : HT_RPC_NORMAL_PORT;

    public static final String APPID = "com.globalways.choume";

    public static final String QINIU_BUCKET = "choume";
    public static final String QINIU_FEEDBACK_KEY = "key";


}
