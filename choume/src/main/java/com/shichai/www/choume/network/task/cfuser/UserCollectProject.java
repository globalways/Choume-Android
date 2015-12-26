package com.shichai.www.choume.network.task.cfuser;

import android.os.AsyncTask;
import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding;
import com.globalways.proto.nano.Common.Response;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class UserCollectProject extends AsyncTask<Void,Void,Response> {

    private ManagerCallBack<Response> callBack;
    private OutsouringCrowdfunding.UserCollectProjectParam param;
    private ManagedChannel channel;
    private Exception exception;

    public UserCollectProject setCallBack(ManagerCallBack<Response> callBack) {
        this.callBack = callBack;
        return this;
    }

    public UserCollectProject setParam(OutsouringCrowdfunding.UserCollectProjectParam param) {
        this.param = param;
        return this;
    }

    @Override
    protected Response doInBackground(Void... params) {
        try {
            channel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(channel);
            return stub.userCollectProject(param);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Response response) {
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
