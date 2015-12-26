package com.shichai.www.choume.network.task.cfuser;

import android.os.AsyncTask;
import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.RegisterCFAppUserResp;
import com.globalways.user.nano.UserCommon;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.ManagerCallBack;
import io.grpc.ManagedChannel;

/**
 * Created by wyp on 15/12/22.
 */
public class RegisterAppUser extends AsyncTask<Void,Void,RegisterCFAppUserResp> {

    public ManagerCallBack<RegisterCFAppUserResp> callBack;

    private UserCommon.RegisterAppUserParam param;
    private ManagedChannel managedChannel;
    private Exception exception;

    public RegisterAppUser setParam(UserCommon.RegisterAppUserParam param) {
        this.param = param;
        return this;
    }

    public RegisterAppUser setCallBack(ManagerCallBack<RegisterCFAppUserResp> callBack) {
        this.callBack = callBack;
        return this;
    }

    @Override
    protected RegisterCFAppUserResp doInBackground(Void... params) {
        try {
            managedChannel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(managedChannel);
            return stub.registerAppUser(param);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(RegisterCFAppUserResp registerCFAppUserResp) {
        if (registerCFAppUserResp == null) {
            callBack.error(exception);
        }else if (registerCFAppUserResp.resp.code != 1) {
            callBack.warning((int)registerCFAppUserResp.resp.code,registerCFAppUserResp.resp.msg);
        }else {
            callBack.success(registerCFAppUserResp);
        }
    }
}
