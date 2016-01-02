package com.shichai.www.choume.network;

/**
 * Created by wyp on 16/1/2.
 */
public enum  HttpStatus {
    UNKNOWN(-1, "未知"),
    OK(1, "ok"),
    APP_ERROR(100, "app错误"),
    SERVER_ERROR(101, "服务器错误"),
    LOGIN_TOKEN_ERROR(102, "登陆token错误"),
    USER_UNAUTHORIZED(103, "用户未被授权"),
    USER_RES_INSUFFICIENT(104, "用户资源不够"),
    USER_REGISTER_ERROR(105, "注册用户错误"),
    NICK_OR_PW_ERROR(106, "用户名密码错误"),
    PARAM_ERROR(107, "请求参数错误"),
    USER_EXISTS(108, "用户已经存在"),
    UNSUPPORTED_SMS(109, "不支持的短信类型"),
    SMS_CODE_ERROR(110, "短信验证码错误"),
    WALLET_NSF(111, "钱包余额不足"),
    RECORD_NOT_FOUND(112, "未找到该记录"),
    THIRD_PARTY_WALLET_ERROR(113, "第三方钱包未验证"),
    USER_LOCKED(114, "用户被锁定"),
    PROJECT_NOT_PUBLISH(115, "众筹项目未发布"),
    PROJECT_HAS_RETURN_FULL(116, "众筹项目的某一回报方式已经投资满额"),
    PROJECT_OUT_DATE(117, "众筹项目过期了");
    public int code;
    public String desc;

    HttpStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static HttpStatus codeOf(int code) {
        switch (code) {
            case 1   : return OK;
            case 100 : return APP_ERROR;
            case 101 : return SERVER_ERROR;
            case 102 : return LOGIN_TOKEN_ERROR;
            case 103 : return USER_UNAUTHORIZED;
            case 104 : return USER_RES_INSUFFICIENT;
            case 105 : return USER_REGISTER_ERROR;
            case 106 : return NICK_OR_PW_ERROR;
            case 107 : return PARAM_ERROR;
            case 108 : return USER_EXISTS;
            case 109 : return UNSUPPORTED_SMS;
            case 110 : return SMS_CODE_ERROR;
            case 111 : return WALLET_NSF;
            case 112 : return RECORD_NOT_FOUND;
            case 113 : return THIRD_PARTY_WALLET_ERROR;
            case 114 : return USER_LOCKED;
            case 115 : return PROJECT_NOT_PUBLISH;
            case 116 : return PROJECT_HAS_RETURN_FULL;
            case 117 : return PROJECT_OUT_DATE;
            default  : return UNKNOWN;
        }
    }
}
