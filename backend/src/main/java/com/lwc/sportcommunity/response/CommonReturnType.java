package com.lwc.sportcommunity.response;

import lombok.Data;

/**
 * Create by LWC on 2023/3/17 16:08
 */
@Data
public class CommonReturnType {
    /*
    处理结果
    success data 返回前端需要的json数据
    fail data 返回通用错误码格式
     */
    private String status;
    private Object data;

    //通用方法
    public static CommonReturnType create(Object result,String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }
}
