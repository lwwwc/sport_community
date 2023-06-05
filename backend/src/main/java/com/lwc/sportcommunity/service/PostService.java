package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.dataobject.PostDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.pagehelper.PageRequest;
import com.lwc.sportcommunity.pagehelper.PageResult;


import java.util.List;

/**
 * Create by LWC on 2023/3/31 13:04
 */
public interface PostService {
    void publish(String telephone, String title, String content) throws SportException;
    PostDo selectOne(Integer postId) throws SportException;
    List<PostDo> listMyPost(String telephone) throws SportException;
    boolean deletePost(String telephone, Integer postId) throws SportException;
    void updatePost(String telephone,Integer postId, String title, String content) throws SportException;
    PageResult findPage(PageRequest pageRequest) throws SportException;
}
