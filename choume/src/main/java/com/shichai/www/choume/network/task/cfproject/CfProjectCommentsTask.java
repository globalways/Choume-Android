package com.shichai.www.choume.network.task.cfproject;

import com.globalways.choume.proto.CfProjectServiceGrpc;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectCommentParam;
import com.globalways.choume.proto.nano.OutsouringCrowdfunding.CfProjectCommentResp;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wang on 16/1/22.
 */
public class CfProjectCommentsTask extends CommonTask<CfProjectCommentParam, CfProjectCommentResp> {
    @Override
    protected CfProjectCommentResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildCM();
            CfProjectServiceGrpc.CfProjectServiceBlockingStub stub = CfProjectServiceGrpc.newBlockingStub(channel);
            return stub.cfProjectComments(taskParam);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(CfProjectCommentResp response) {
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
