package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.EventAttendenDo;

import java.util.List;

public interface EventAttendenDoMapper {
    int deleteByPrimaryKey(Integer joinId);

    int insert(EventAttendenDo record);

    int insertSelective(EventAttendenDo record);

    EventAttendenDo selectByPrimaryKey(Integer joinId);

    List<EventAttendenDo> selectByUserId(Integer userId);

    List<EventAttendenDo> selectAttend(Integer eventId);

    EventAttendenDo selectByEventIdAndUserId(Integer eventId, Integer userId);

    int updateByPrimaryKeySelective(EventAttendenDo record);

    int updateByPrimaryKey(EventAttendenDo record);
}