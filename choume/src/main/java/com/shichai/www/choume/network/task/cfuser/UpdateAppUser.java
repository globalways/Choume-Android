package com.shichai.www.choume.network.task.cfuser;

import android.os.AsyncTask;
import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.UpdateCFUserParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.UpdateCFUserResp;;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class UpdateAppUser extends AsyncTask<Void,Void,UpdateCFUserResp> {
    private ManagerCallBack<UpdateCFUserResp> callBack;
    private UpdateCFUserParam param;
    private ManagedChannel channel;
    private Exception exception;

    public UpdateAppUser setCallBack(ManagerCallBack<UpdateCFUserResp> callBack) {
        this.callBack = callBack;
        return this;
    }

    public UpdateAppUser setParam(UpdateCFUserParam param) {
        this.param = param;
        return this;
    }

    @Override
    protected UpdateCFUserResp doInBackground(Void... params) {
        try {
            channel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(channel);
            return stub.updateAppUser(param);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(UpdateCFUserResp updateCFUserResp) {
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (updateCFUserResp == null) {
            callBack.error(exception);
        }else if (updateCFUserResp.resp.code != 1) {
            callBack.warning((int)updateCFUserResp.resp.code,updateCFUserResp.resp.msg);
        }else {
            callBack.success(updateCFUserResp);
        }
    }
}
