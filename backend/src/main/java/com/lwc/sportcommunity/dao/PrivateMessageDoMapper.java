package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.PrivateMessageDo;
import org.apache.catalina.LifecycleState;

import java.util.List;

public interface PrivateMessageDoMapper {
    int deleteByPrimaryKey(Integer chatId);

    int insert(PrivateMessageDo record);

    int insertSelective(PrivateMessageDo record);

    PrivateMessageDo selectByPrimaryKey(Integer chatId);

    List<PrivateMessageDo> selectUnReadMsgList(Integer msgId, Integer myId, Integer friendId);

    int updateByPrimaryKeySelective(PrivateMessageDo record);

    int updateByPrimaryKey(PrivateMessageDo record);
}