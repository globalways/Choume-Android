package com.shichai.www.choume.network.task.cfuser;

import android.os.AsyncTask;
import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.GetCFUserResp;
import com.globalways.user.nano.UserCommon.GetAppUserParam;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class GetAppUserTask extends AsyncTask<Void, Void, GetCFUserResp> {

    private ManagerCallBack<GetCFUserResp> callBack;
    private GetAppUserParam param;
    private ManagedChannel channel;
    private Exception exception;

    public GetAppUserTask setCallBack(ManagerCallBack<GetCFUserResp> callBack) {
        this.callBack = callBack;
        return this;
    }

    public GetAppUserTask setParam(GetAppUserParam param) {
        this.param = param;
        return this;
    }

    @Override

    protected GetCFUserResp doInBackground(Void... params) {
        try {
            channel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(channel);
            return stub.getAppUser(param);
        } catch (Exception e) {
            e = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(GetCFUserResp getCFUserResp) {
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (getCFUserResp == null) {
            callBack.error(exception);
        }else if (getCFUserResp.resp.code != 1) {
            callBack.warning((int)getCFUserResp.resp.code,getCFUserResp.resp.msg);
        }else {
            callBack.success(getCFUserResp);
        }
    }
}
