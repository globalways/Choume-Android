package com.shichai.www.choume.network.task.pingpp;

import com.globalways.user.pingpp.UserPingppServiceGrpc;
import com.globalways.user.pingpp.nano.UserPingpp.PingppChargeParam;
import com.globalways.user.pingpp.nano.UserPingpp.PingppChargeResp;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * 创建第三方支付凭证
 * Created by wyp on 15/12/22.
 */
public class PingppChargeTask extends CommonTask<PingppChargeParam,PingppChargeResp> {
    @Override
    protected PingppChargeResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildHT();
            UserPingppServiceGrpc.UserPingppServiceBlockingStub stub = UserPingppServiceGrpc.newBlockingStub(channel);
            return stub.pingppCharge(taskParam);
        } catch (Exception e) {
            exception = e;
            return null;
        }

    }

    @Override
    protected void onPostExecute(PingppChargeResp response) {
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (response == null) {
            callBack.error(exception);
        }else if (response.resp.code != 1) {
            callBack.warning((int)response.resp.code,response.resp.msg);
        }else {
            callBack.success(response);
        }
    }
}
