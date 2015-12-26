package com.shichai.www.choume.network.task.cfuser;

import com.globalways.choume.proto.CFAppUserServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.UserUnCollectProjectParam;
import com.globalways.proto.nano.Common.Response;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class UserUnCollectProject extends CommonTask<UserUnCollectProjectParam,Response> {

    @Override
    protected Response doInBackground(Object... params) {
        try {
            channel = CMChannel.buildCM();
            CFAppUserServiceGrpc.CFAppUserServiceBlockingStub stub = CFAppUserServiceGrpc.newBlockingStub(channel);
            return stub.userUnCollectProject(taskParam);
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
