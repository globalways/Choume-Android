package com.shichai.www.choume.network.task.sms;

import com.globalways.proto.nano.Common;
import com.globalways.user.sms.UserSMSServiceGrpc;
import com.globalways.user.sms.nano.UserSms;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class SendSMSTask extends CommonTask<UserSms.SendSMSParam,Common.Response> {
    @Override
    protected Common.Response doInBackground(Object... params) {
        try {
            channel = CMChannel.buildHT();
            UserSMSServiceGrpc.UserSMSServiceBlockingStub stub = UserSMSServiceGrpc.newBlockingStub(channel);
            return stub.sendSMS(taskParam);
        } catch (Exception e) {
            e = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Common.Response response) {
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (response == null) {
            callBack.error(exception);
        }else if (response.code != 1) {
            callBack.warning((int)response.code,response.msg);
        }else {
            callBack.success(response);
        }
    }
}
