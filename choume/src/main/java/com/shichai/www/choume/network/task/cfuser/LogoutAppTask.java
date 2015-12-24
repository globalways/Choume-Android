package com.shichai.www.choume.network.task.cfuser;

import android.os.AsyncTask;
import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.proto.nano.Common.Response;
import com.globalways.user.nano.UserCommon.LogoutParam;

import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

/**
 * Created by wyp on 15/12/22.
 */
public class LogoutAppTask extends AsyncTask<Void,Void,Response> {
    private ManagerCallBack<Response> callBack;
    private ManagedChannel managedChannel;
    private LogoutParam logoutParam;
    private Exception exception;

    public LogoutAppTask setLogoutParam(LogoutParam logoutParam) {
        this.logoutParam = logoutParam;
        return this;
    }

    public LogoutAppTask setCallBack(ManagerCallBack<Response> callBack) {
        this.callBack = callBack;
        return this;
    }

    @Override
    protected Response doInBackground(Void... params) {
        try {
            managedChannel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(managedChannel);
            return stub.logoutApp(logoutParam);
        } catch (Exception e) {
            e = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        if (response == null) {
            callBack.error(exception);
        }else if (response.code != 1) {
            callBack.warning((int)response.code,response.msg);
        }else {
            callBack.success(response);
        }
    }
}

