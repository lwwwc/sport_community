package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.CommentDo;

import java.util.List;

public interface CommentDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentDo record);

    int insertSelective(CommentDo record);

    CommentDo selectByPrimaryKey(Integer id);

    List<CommentDo> selectAllByPostId(Integer postId);

    List<CommentDo> selectMyComments(Integer userId);


    int updateByPrimaryKeySelective(CommentDo record);

    int updateByPrimaryKeyWithBLOBs(CommentDo record);

    int updateByPrimaryKey(CommentDo record);
}