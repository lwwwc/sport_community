package com.lwc.sportcommunity.service.impl;

import com.lwc.sportcommunity.controller.vo.UserVo;
import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dao.UserPasswordMapper;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.dataobject.UserPassword;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.service.UserService;
import com.lwc.sportcommunity.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.beans.Beans;

/**
 * Create by LWC on 2023/3/18 0:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserPasswordMapper userPasswordMapper;

    @Override
    public UserModel validateLogin(String telephone, String encrptPassword) throws SportException {
        User userDo = userMapper.selectByTelephone(telephone);

        if (null == userDo){
            throw new SportException(EmSportError.USER_LOGIN_FAIL);
        }

        UserPassword userPasswordDo = userPasswordMapper.selectByUserId(userDo.getId());

        UserModel userModel = convertFromDataObject(userDo, userPasswordDo);

        if (!StringUtils.equals(encrptPassword, userModel.getEncrpyPassword())){
            throw new SportException(EmSportError.USER_LOGIN_FAIL);
        }
        return userModel;
    }

    @Override
    public void register(UserModel userModel) throws SportException {
        if (null == userModel){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User userDo = convertFromModel(userModel);
        try {
            userMapper.insertSelective(userDo);
        }catch (DuplicateKeyException ex){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "手机号已重复注册");
        }

        userModel.setId(userDo.getId());

        UserPassword userPasswordDo = convertPassWordFromModel(userModel);
        userPasswordMapper.insertSelective(userPasswordDo);
        return;
    }

    @Override
    public UserModel update(User user) {
        int cnt = userMapper.updateByTelephoneSelective(user);
        if (cnt > 0){
            User userDo = userMapper.selectByTelephone(user.getTelephone());
            UserPassword userPasswordDo = userPasswordMapper.selectByUserId(userDo.getId());
            UserModel userModel = convertFromDataObject(userDo, userPasswordDo);
            return userModel;
        }else {
            return  null;
        }
    }

    @Override
    public UserModel updateUserInfo(User user) {
        int cnt = userMapper.updateByPrimaryKeySelective(user);
        if (cnt > 0){
            User users = userMapper.selectByPrimaryKey(user.getId());
            UserModel userModel = convertFromDataObject(users, null);
            return userModel;
        }else {
            return null;
        }
    }

    @Override
    public boolean updatePassword(String telephone, String oldPassword, String newPassword) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        UserPassword userPassword = userPasswordMapper.selectByUserId(user.getId());
        if (!userPassword.getEncrptPassword().equals(oldPassword)){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"旧密码不正确");
        }
        userPassword.setEncrptPassword(newPassword);
        int cnt = userPasswordMapper.updateByPrimaryKeySelective(userPassword);
        if (cnt > 0){
            return true;
        }
        return false;
    }

    @Override
    public User getFace(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null){
            return null;
        }
        return user;
    }

    private UserPassword convertPassWordFromModel(UserModel userModel){
        if (null == userModel){
            return null;
        }
        UserPassword userPasswordDo = new UserPassword();
        userPasswordDo.setEncrptPassword(userModel.getEncrpyPassword());
        userPasswordDo.setUserId(userModel.getId());
        return userPasswordDo;
    }


    private User convertFromModel(UserModel userModel){
        if (null == userModel){
            return null;
        }
        User userDo = new User();
        BeanUtils.copyProperties(userModel, userDo);
        return userDo;
    }

    private UserModel convertFromDataObject(User userDo, UserPassword userPasswordDo){
        if (userDo == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDo,userModel);

        if (userPasswordDo != null){
            userModel.setEncrpyPassword(userPasswordDo.getEncrptPassword());
        }
        return userModel;
    }
}


