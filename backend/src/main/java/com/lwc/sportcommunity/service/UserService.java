package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.controller.bo.UsersBO;
import com.lwc.sportcommunity.controller.vo.UserVo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.service.model.UserModel;

/**
 * Create by LWC on 2023/3/17 16:38
 */
public interface UserService {

    /**
     *
     * @param userModel
     * @throws SportException
     */
    void register(UserModel userModel) throws SportException;

    /**
     *
     * @param telephone 用户注册手机
     * @param encrptPassword  用户加密后密码
     * @throws SportException
     */
    UserModel validateLogin(String telephone,String encrptPassword) throws SportException;

    UserModel update(User user);

    boolean updatePassword(String telephone, String oldPassword, String newPassword) throws SportException;

    User getFace(Integer userId);

    UserModel updateUserInfo(User user);

}
