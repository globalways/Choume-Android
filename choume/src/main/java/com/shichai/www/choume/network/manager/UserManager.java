package com.shichai.www.choume.network.manager;

import com.globalways.proto.nano.Common;
import com.globalways.user.nano.UserApp;
import com.shichai.www.choume.network.ManagerCallBack;
import com.shichai.www.choume.network.task.user.*;

/**
 * CfUser是APP User<br />
 * User是通用用户基础信息
 * Created by wyp on 15/12/23.
 */
public class UserManager {
    private static UserManager manager;
    private UserManager(){}

    public static UserManager getInstance(){
        if (manager == null){
            manager = new UserManager();
        }
        return manager;
    }

    /**
     * 修改密码
     * @param param
     * @param callBack
     */
    public void changePassword(final UserApp.ChangePasswordParam param, ManagerCallBack<Common.Response> callBack) {
        new ChangePasswordTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    public void forgetPassword(final UserApp.ForgetPasswordParam param, ManagerCallBack<Common.Response> callBack) {
        new ForgetPasswordTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    public void changeUserAvatar(final UserApp.ChangeUserAvatarParam param, ManagerCallBack<Common.Response> callBack) {
        new ChangeUserAvatarTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    public void changeUserNick(final UserApp.ChangeUserNickParam param, ManagerCallBack<Common.Response> callBack) {
        new ChangeUserNickTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    public void newUserAddr(final UserApp.NewUserAddrParam param, ManagerCallBack<UserApp.NewUserAddrResp> callBack) {
        new NewUserAddrTask().setCallBack(callBack).setTaskParam(param).execute();
    }

    public void GetUser(final UserApp.GetUserParam param, ManagerCallBack<UserApp.GetUserResp> callBack){
        new GetUserTask().setCallBack(callBack).setTaskParam(param).execute();
    }

}
