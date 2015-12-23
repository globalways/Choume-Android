package com.shichai.www.choume.network.task.cfproject;

import com.globalways.choume.proto.CfProjectServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.RaiseCfProjectParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.RaiseCfProjectResp;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class RaiseCfProjectTask extends CommonTask<RaiseCfProjectParam,RaiseCfProjectResp> {
    @Override
    protected RaiseCfProjectResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildCM();
            CfProjectServiceGrpc.CfProjectServiceBlockingStub stub = CfProjectServiceGrpc.newBlockingStub(channel);
            return stub.raiseCfProject(taskParam);
        } catch (Exception e) {
            e = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(RaiseCfProjectResp response) {
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
