package com.lwc.sportcommunity.error;

/**
 * Create by LWC on 2023/3/17 23:36
 */
public enum EmSportError implements CommonError {
    //    通用错误类型00001
    PARAMETER_VALIDATION_ERROR(00001,"参数不合法"),
    UNKNOWN_ERROR(00002,"未知错误"),

    //    10000开头为用户信息相关错误定义
    USER_NOT_EXIST(10001,"用户不存在"),
    USER_LOGIN_FAIL(10002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(10003,"用户还未登录"),
    USER_OTP_REPEAT(10004,"验证码尚未过期"),

    EVENT_NOT_EXIST(10005,"活动不存在"),
    USER_NOT_IN_CLUB(10006, "用户不在该俱乐部"),
    CLUB_NOT_EXIST(10007, "该俱乐部不存在")
    ;
    private EmSportError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
