package com.shichai.www.choume.network.manager;

import com.globalways.choume.proto.nano.OutsouringCrowdfunding.*;
import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserCommon.GetAppUserParam;
import com.globalways.user.nano.UserCommon.LoginAppParam;
import com.globalways.user.nano.UserCommon.LogoutParam;
import com.globalways.user.nano.UserCommon.RegisterAppUserParam;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.task.cfuser.*;

/**
 * CfUser是APP User<br />
 * User是通用用户基础信息
 * Created by wyp on 15/12/23.
 */
public class CfUserManager {

    private static CfUserManager cfUserManager;
    private CfUserManager() {}

    public static CfUserManager getInstance() {
        if (cfUserManager == null) {
            cfUserManager = new CfUserManager();
        }
        return cfUserManager;
    }

    /**
     * app登录
     * @param param LoginAppParam
     * @param callBack
     */
    public void login(final LoginAppParam param, ManagerCallBack<LoginCFAppResp> callBack) {
        new LoginCFAppTask().setCallBack(callBack).setLoginAppParam(param).execute();
    }

    /**
     * app注册
     * @param param
     * @param callBack
     */
    public void register(final RegisterAppUserParam param, ManagerCallBack<RegisterCFAppUserResp> callBack) {
        new RegisterAppUser().setCallBack(callBack).setParam(param).execute();
    }

    /**
     * app登出
     * @param param
     * @param callBack
     */
    public void logoutApp(final LogoutParam param, ManagerCallBack<Common.Response> callBack){
        new LogoutAppTask().setLogoutParam(param).setCallBack(callBack).execute();
    }

    /**
     * 更新用户信息
     * @param param
     * @param callBack
     */
    public void updateAppUser(final UpdateCFUserParam param, ManagerCallBack<UpdateCFUserResp> callBack) {
        new UpdateAppUser().setCallBack(callBack).setParam(param).execute();
    }

    /**
     * 获取用户信息
     * @param param
     * @param callBack
     */
    public void getAppUser(final GetAppUserParam param, ManagerCallBack<GetCFUserResp> callBack) {
        new GetAppUserTask().setCallBack(callBack).setParam(param).execute();
    }



    /**
     * 用户认证申请
     * @param param
     * @param callBack
     */
    public void userCertApply(final UserCertApplyParam param, ManagerCallBack<UserCertApplyResp> callBack) {
        new UserCertApplyTask().setCallBack(callBack).setParam(param).execute();
    }

    /**
     * 收藏项目
     * @param param
     * @param callBack
     */
    public void userCollectProject(final UserCollectProjectParam param, ManagerCallBack<Common.Response> callBack) {
        new UserCollectProject().setCallBack(callBack).setParam(param).execute();
    }

    /**
     * 取消收藏项目
     * @param param
     * @param callBack
     */
    public void userUnCollectProject(final UserUnCollectProjectParam param, ManagerCallBack<Common.Response> callBack) {
        new UserUnCollectProject().setTaskParam(param).setCallBack(callBack).execute();
    }


    /**
     * 融云 （http url: /users/rcToken[post]） 当rcToken 无效时使用，一般情况下忽略，详细内容请查看融云API
     * @param param
     * @param callBack
     */
    public void getRCCFUserToken(final GetRCCFUserTokenParam param, ManagerCallBack<GetRCCFUserTokenResp> callBack) {
        new GetRCCFUserTokenTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 筹币消费 (http url: /users/cb/consume [post])
     * @param param
     * @param callBack
     */
    public void cfUserCBConsume(final CfUserCBConsumeParam param, ManagerCallBack<CfUserCBConsumeResp> callBack) {
        new CfUserCBConsumeTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 筹币兑换
     * @param param
     * @param callBack
     */
    public void cfUserCBExchange(final CfUserCBExchangeParam param, ManagerCallBack<CfUserCBExchangeResp> callBack) {
        new CfUserCBExchangeTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 筹币提现
     * @param param
     * @param callBack
     */
    public void cfUserWithdraw(final CfUserWithdrawParam param, ManagerCallBack<CfUserWithdrawResp> callBack) {
        new CfUserWithdrawTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    /**
     * 筹币明细
     * @param param
     * @param callBack
     */
    public void cfUserHistories(final CfUserHistoriesParam param, ManagerCallBack<CfUserHistoriesResp> callBack) {
        new CfUserHistoriesTask().setCallBack(callBack).setTaskParam(param).execute();
    }


}
