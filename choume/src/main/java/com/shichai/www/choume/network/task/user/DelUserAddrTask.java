package com.shichai.www.choume.network.task.user;

import com.globalways.proto.nano.Common;
import com.globalways.user.UserAppServiceGrpc;
import com.globalways.user.nano.UserApp;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/28.
 */
public class DelUserAddrTask extends CommonTask<UserApp.DelUserAddrParam, Common.Response> {
    @Override
    protected Common.Response doInBackground(Object... params) {
        try {
            channel = CMChannel.buildHT();
            UserAppServiceGrpc.UserAppServiceBlockingStub stub = UserAppServiceGrpc.newBlockingStub(channel);
            return stub.delUserAddr(taskParam);
        } catch (Exception e) {
            exception = e;
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
