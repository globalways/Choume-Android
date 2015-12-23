package com.shichai.www.choume.network.manager;

import com.globalways.user.wallet.nano.UserWallet.*;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.task.wallet.*;


/**
 * Created by wyp on 15/12/23.
 */
public class WalletManager {
    private WalletManager manager;
    private WalletManager(){}

    public WalletManager getInstance(){
        if (manager == null){
            manager = new WalletManager();
        }
        return manager;
    }

    /**
     * 钱包
     * @param param
     * @param callBack
     */
    public void getUserWallet(final GetUserWalletParam param, ManagerCallBack<GetUserWalletResp> callBack) {
        new GetUserWalletTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 钱包明细
     * @param param
     * @param callBack
     */
    public void listUserWalletHistories(final ListUserWalletHistoriesParam param, ManagerCallBack<ListUserWalletHistoriesResp> callBack) {
        new ListUserWalletHistoriesTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 准备钱包充值
     * @param param
     * @param callBack
     */
    public void prepareUserWalletRecharge(final PrepareUserWalletRechargeParam param, ManagerCallBack<PrepareUserWalletRechargeResp> callBack) {
        new PrepareUserWalletRechargeTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 钱包付款
     * @param param
     * @param callBack
     */
    public void userWalletPay(final UserWalletPayParam param, ManagerCallBack<UserWalletPayResp> callBack) {
        new UserWalletPayTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 第三方钱包绑定
     * @param param
     * @param callBack
     */
    public void thirdPartyWalletBind(final ThirdPartyWalletBindParam param, ManagerCallBack<ThirdPartyWalletBindResp> callBack) {
        new ThirdPartyWalletBindTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 钱包提现
     * @param param
     * @param callBack
     */
    public void userWalletWithdraw(final UserWalletWithdrawParam param, ManagerCallBack<UserWalletWithdrawResp> callBack) {
        new UserWalletWithdrawTask().setCallBack(callBack).setTaskParam(param).execute();
    }

}
