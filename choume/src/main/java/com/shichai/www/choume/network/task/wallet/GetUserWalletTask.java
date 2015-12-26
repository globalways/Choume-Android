package com.shichai.www.choume.network.task.wallet;

import com.globalways.user.wallet.UserWalletServiceGrpc;
import com.globalways.user.wallet.nano.UserWallet;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class GetUserWalletTask extends CommonTask<UserWallet.GetUserWalletParam,UserWallet.GetUserWalletResp> {
    @Override
    protected UserWallet.GetUserWalletResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildHT();
            UserWalletServiceGrpc.UserWalletServiceBlockingStub stub = UserWalletServiceGrpc.newBlockingStub(channel);
            return stub.getUserWallet(taskParam);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserWallet.GetUserWalletResp response) {
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
