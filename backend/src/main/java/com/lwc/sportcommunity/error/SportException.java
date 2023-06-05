package com.lwc.sportcommunity.error;

/**
 * Create by LWC on 2023/3/17 23:27
 */
public class SportException extends Exception implements CommonError {

    private CommonError commonError;

    //直接接受EmSportException用于构造业务异常
    public SportException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //接受自定义errMsg的方式构造业务异常
    public SportException(CommonError commonError,String  errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
