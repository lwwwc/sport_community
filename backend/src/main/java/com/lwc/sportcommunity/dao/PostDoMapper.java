package com.lwc.sportcommunity.dao;

import com.github.pagehelper.Page;
import com.lwc.sportcommunity.dataobject.PostDo;

import java.util.List;

public interface PostDoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostDo record);

    int insertSelective(PostDo record);

    PostDo selectByPrimaryKey(Integer id);

    List<PostDo> selectMyPosts(Integer userId);

    int updateByPrimaryKeySelective(PostDo record);

    int updateByPrimaryKeyWithBLOBs(PostDo record);

    int updateByPrimaryKey(PostDo record);

    int incrementViewCount(Integer postId);

    Page<PostDo> selectPageOrderByTime();

    Page<PostDo> selectOrderByViewCount();

    Page<PostDo> selectAll();

}