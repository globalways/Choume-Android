package com.shichai.www.choume.network.task.wallet;

import com.globalways.user.wallet.UserWalletServiceGrpc;
import com.globalways.user.wallet.nano.UserWallet;
import com.shichai.www.choume.network.CMChannel;
import com.shichai.www.choume.network.task.CommonTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by wyp on 15/12/22.
 */
public class ListUserWalletHistoriesTask extends CommonTask<UserWallet.ListUserWalletHistoriesParam,UserWallet.ListUserWalletHistoriesResp> {
    @Override
    protected UserWallet.ListUserWalletHistoriesResp doInBackground(Object... params) {
        try {
            channel = CMChannel.buildHT();
            UserWalletServiceGrpc.UserWalletServiceBlockingStub stub = UserWalletServiceGrpc.newBlockingStub(channel);
            return stub.listUserWalletHistories(taskParam);
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(UserWallet.ListUserWalletHistoriesResp response) {
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
