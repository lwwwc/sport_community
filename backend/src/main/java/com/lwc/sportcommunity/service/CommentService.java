package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.dataobject.CommentDo;
import com.lwc.sportcommunity.error.SportException;

import java.util.List;

/**
 * Create by LWC on 2023/4/2 14:28
 */
public interface CommentService {
    void publish(String telephone, Integer postId, String content) throws SportException;
    boolean deleteComment(String telephone, Integer commentId) throws SportException;
    List<CommentDo> listComments(Integer postId) throws SportException;
    List<CommentDo> listMyComments(String telephone) throws SportException;
    void update(String telephone, Integer commentId, String content) throws SportException;
}
