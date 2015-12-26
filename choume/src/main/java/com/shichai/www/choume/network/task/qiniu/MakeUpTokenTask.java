package com.shichai.www.choume.network.task.qiniu;

import com.globalways.user.qiniu.QiniuServiceGrpc;
import com.globalways.user.qiniu.nano.UserQiniu;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class MakeUpTokenTask extends CommonTask<UserQiniu.MakeQiniuUpTokenParam,UserQiniu.MakeQiniuUpTokenResp> {
    @Override
    protected UserQiniu.MakeQiniuUpTokenResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildHT();
        QiniuServiceGrpc.QiniuServiceBlockingStub stub = QiniuServiceGrpc.newBlockingStub(channel);
            return stub.makeUpToken(taskParam);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserQiniu.MakeQiniuUpTokenResp response) {
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
