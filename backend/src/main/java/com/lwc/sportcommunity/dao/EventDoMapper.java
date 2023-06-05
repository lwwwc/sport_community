package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.EventDo;

import java.util.List;

public interface EventDoMapper {
    int deleteByPrimaryKey(Integer eventId);

    int insert(EventDo record);

    int insertSelective(EventDo record);

    EventDo selectByPrimaryKey(Integer eventId);

    List<EventDo> selectAllByClubId(Integer clubId);

    int updateByPrimaryKeySelective(EventDo record);

    int updateByPrimaryKeyWithBLOBs(EventDo record);

    int updateByPrimaryKey(EventDo record);
}