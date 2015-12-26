package com.shichai.www.choume.network.task.cfuser;

import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfUserWithdrawParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfUserWithdrawResp;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;


import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class CfUserWithdrawTask extends CommonTask<CfUserWithdrawParam,CfUserWithdrawResp> {
    @Override
    protected CfUserWithdrawResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(channel);
            return stub.cfUserWithdraw(taskParam);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(CfUserWithdrawResp response) {
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
