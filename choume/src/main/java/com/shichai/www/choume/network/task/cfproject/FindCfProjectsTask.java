package com.shichai.www.choume.network.task.cfproject;

import com.globalways.choume.proto.CfProjectServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.FindCfProjectsParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.FindCfProjectsResp;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class FindCfProjectsTask extends CommonTask<FindCfProjectsParam,FindCfProjectsResp> {
    @Override
    protected FindCfProjectsResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildCM();
            CfProjectServiceGrpc.CfProjectServiceBlockingStub stub = CfProjectServiceGrpc.newBlockingStub(channel);
            return stub.findCfProjects(taskParam);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(FindCfProjectsResp response) {
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
