package com.shichai.www.choume.network.task.cfuser;

import android.os.AsyncTask;
import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.LoginCFAppResp;
import com.globalways.user.nano.UserCommon;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class LoginCFAppTask extends AsyncTask<Void,Void,LoginCFAppResp> {

    private ManagerCallBack<LoginCFAppResp> callBack;
    private ManagedChannel mChannel;
    private UserCommon.LoginAppParam loginAppParam;
    private Exception exception;

    public LoginCFAppTask setCallBack(ManagerCallBack<LoginCFAppResp> callBack){
        this.callBack = callBack;
        return this;
    }

    public LoginCFAppTask setLoginAppParam(UserCommon.LoginAppParam param) {
        this.loginAppParam = param;
        return this;
    }

    private LoginCFAppResp LoginApp(ManagedChannel  channel) {
        CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(channel);
        LoginCFAppResp resp = stub.loginApp(loginAppParam);
        return resp;
    }

    @Override
    protected LoginCFAppResp doInBackground(Void... params) {
        try {
            mChannel = CMChannel.buildCM();
            return LoginApp(mChannel);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(LoginCFAppResp resp) {
        try {
            mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if(resp == null){
            callBack.error(exception);
        }else{
            if(resp.resp.code != 1){
                callBack.warning((int) resp.resp.code, resp.resp.msg);
            }else {
                callBack.success(resp);
            }
        }

    }
}
