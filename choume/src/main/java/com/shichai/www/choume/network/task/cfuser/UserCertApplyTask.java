package com.shichai.www.choume.network.task.cfuser;

import android.os.AsyncTask;
import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.UserCertApplyParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.UserCertApplyResp;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class UserCertApplyTask extends AsyncTask<Void,Void,UserCertApplyResp> {
    private ManagerCallBack<UserCertApplyResp> callBack;
    private UserCertApplyParam param;
    private ManagedChannel channel;
    private Exception exception;

    public UserCertApplyTask setCallBack(ManagerCallBack<UserCertApplyResp> callBack) {
        this.callBack = callBack;
        return this;
    }

    public UserCertApplyTask setParam(UserCertApplyParam param) {
        this.param = param;
        return this;
    }

    @Override
    protected UserCertApplyResp doInBackground(Void... params) {
        try {
            channel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(channel);
            return stub.userCertApply(param);
        } catch (Exception e) {
            e = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserCertApplyResp userCertApplyResp) {
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (userCertApplyResp == null) {
            callBack.error(exception);
        }else if (userCertApplyResp.resp.code != 1) {
            callBack.warning((int)userCertApplyResp.resp.code,userCertApplyResp.resp.msg);
        }else {
            callBack.success(userCertApplyResp);
        }

    }
}
