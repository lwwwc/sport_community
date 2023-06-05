package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.ChatRecordDo;

import java.util.List;

public interface ChatRecordDoMapper {
    int deleteByPrimaryKey(Integer chatId);

    int insert(ChatRecordDo record);

    int insertSelective(ChatRecordDo record);

    ChatRecordDo selectByPrimaryKey(Integer chatId);

    List<ChatRecordDo> selectUnReadMsgList(Integer msgId, Integer clubId);

    int updateByPrimaryKeySelective(ChatRecordDo record);

    int updateByPrimaryKeyWithBLOBs(ChatRecordDo record);

    int updateByPrimaryKey(ChatRecordDo record);
}