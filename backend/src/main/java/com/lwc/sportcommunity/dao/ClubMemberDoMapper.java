package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.ClubMemberDo;

import java.util.List;

public interface ClubMemberDoMapper {
    int deleteByPrimaryKey(Integer cmId);

    int insert(ClubMemberDo record);

    int insertSelective(ClubMemberDo record);

    ClubMemberDo selectByPrimaryKey(Integer cmId);

    List<ClubMemberDo> selectByUserId(Integer userId);

    List<ClubMemberDo> selectByClubId(Integer clubId);

    ClubMemberDo selectByUserIdAndClubId(Integer userId, Integer clubId);

    int updateByPrimaryKeySelective(ClubMemberDo record);

    int updateByPrimaryKey(ClubMemberDo record);
}