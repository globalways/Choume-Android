package com.shichai.www.choume.network.manager;

import com.globalways.proto.nano.Common;
import com.globalways.user.pingpp.nano.UserPingpp;
import com.globalways.user.sms.nano.UserSms;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.task.pingpp.PingppChargeTask;
import com.shichai.www.choume.network.task.sms.SendSMSTask;
import com.shichai.www.choume.network.task.sms.VarifySMSCodeTask;


/**
 * 与第三方接口有关的方法：支付、短信验证
 * Created by wyp on 15/12/23.
 */
public class ThirdPartyManager {
    private static ThirdPartyManager manager;
    private ThirdPartyManager(){}

    public static ThirdPartyManager getInstance(){
        if (manager == null){
            manager = new ThirdPartyManager();
        }
        return manager;
    }

    /**
     * 创建第三方支付凭证
     * @param param
     * @param callBack
     */
    public void pingppCharge(final UserPingpp.PingppChargeParam param, ManagerCallBack<UserPingpp.PingppChargeResp> callBack) {
        new PingppChargeTask().setCallBack(callBack).setTaskParam(param).execute();
    }


    /**
     * 发送sms
     * @param param
     * @param callBack
     */
    public void sendSMS(final UserSms.SendSMSParam param, ManagerCallBack<Common.Response> callBack) {
        new SendSMSTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 验证手机验证码
     * @param param
     * @param callBack
     */
    public void varifySMSCode(final UserSms.VarifySMSCodeParam param, ManagerCallBack<Common.Response> callBack) {
        new VarifySMSCodeTask().setCallBack(callBack).setTaskParam(param).execute();
    }

}
