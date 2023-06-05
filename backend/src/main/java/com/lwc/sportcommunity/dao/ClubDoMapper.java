package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.ClubDo;

import java.util.List;

public interface ClubDoMapper {
    int deleteByPrimaryKey(Integer clubId);

    int insert(ClubDo record);

    int insertSelective(ClubDo record);

    ClubDo selectByPrimaryKey(Integer clubId);

    List<ClubDo> selectAll();

    int updateByPrimaryKeySelective(ClubDo record);

    int updateByPrimaryKeyWithBLOBs(ClubDo record);

    int updateByPrimaryKey(ClubDo record);
}