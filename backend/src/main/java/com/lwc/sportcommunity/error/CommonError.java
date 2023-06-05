package com.lwc.sportcommunity.error;

/**
 * Create by LWC on 2023/3/17 23:14
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);

}
