package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByTelephone(String telephone);

    int updateByPrimaryKeySelective(User record);

    int updateByTelephoneSelective(User record);

    int updateByPrimaryKey(User record);
}