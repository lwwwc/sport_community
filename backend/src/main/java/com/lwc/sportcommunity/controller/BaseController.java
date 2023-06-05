package com.lwc.sportcommunity.controller;


import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * Create by LWC on 2023/3/17 23:44
 */
public class BaseController {

    public static final   String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";

    //    定义exceptionhandler解决未被controller吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String,Object> responseData = new HashMap<>();


        if (ex instanceof SportException){
            SportException sportException = (SportException) ex;
            responseData.put("errCode",sportException.getErrCode());
            responseData.put("errMsg",sportException.getErrMsg());
        }else {
            responseData.put("errCode", EmSportError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmSportError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }

}
