package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.LikeDo;

public interface LikeDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LikeDo record);

    int insertSelective(LikeDo record);

    LikeDo selectByPrimaryKey(Integer id);

    LikeDo selectByUserIdAndPostId(Integer userId, Integer postId);

    int updateByPrimaryKeySelective(LikeDo record);

    int updateByPrimaryKey(LikeDo record);

    int selectCntByPostId(Integer postId);

    int deleteByPostIdAndUserId(Integer userId, Integer postId);
}