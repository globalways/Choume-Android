package com.shichai.www.choume.network.protoenum;

import com.globalways.user.nano.UserCommon;

/**
 * Created by wyp on 15/12/26.
 */
public enum  UserSex {
    UNKNOWN_Sex(UserCommon.UNKNOWN_Sex), MALE(UserCommon.MALE), FAMALE(UserCommon.FAMALE);
    private int code;
    private String desc;

    private UserSex(int code){
        this.code = code;
    }

    public String getDesc() {

        switch (this) {
            case UNKNOWN_Sex: return "未知";
            case MALE:
                return "男";
            case FAMALE:
                return "女";
            default:return "未知";
        }
    }

    public static UserSex codeOf(int code) {
        switch (code) {
            case UserCommon.UNKNOWN_Sex: return UNKNOWN_Sex;
            case UserCommon.MALE:return MALE;
            case UserCommon.FAMALE:return FAMALE;
            default:return UNKNOWN_Sex;
        }
    }

}
