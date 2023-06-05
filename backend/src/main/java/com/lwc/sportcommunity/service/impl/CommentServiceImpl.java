package com.lwc.sportcommunity.service.impl;

import com.lwc.sportcommunity.dao.CommentDoMapper;
import com.lwc.sportcommunity.dao.PostDoMapper;
import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dataobject.CommentDo;
import com.lwc.sportcommunity.dataobject.PostDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.spi.SelectorProvider;
import java.util.Date;
import java.util.List;

/**
 * Create by LWC on 2023/4/2 14:28
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentDoMapper commentDoMapper;

    @Autowired
    private PostDoMapper postDoMapper;

    @Override
    public void publish(String telephone, Integer postId, String content) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (user == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "该手机号用户不存在,不能评论");
        }
        PostDo postDo = postDoMapper.selectByPrimaryKey(postId);
        if (null == postDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该文章不存在,不能评论");
        }
        CommentDo commentDo = new CommentDo();
        commentDo.setContent(content);
        commentDo.setCreateTime(new Date());
        commentDo.setPostId(postId);
        commentDo.setUserId(user.getId());
        commentDoMapper.insertSelective(commentDo);
    }

    @Override
    public boolean deleteComment(String telephone, Integer commentId) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        CommentDo commentDo = commentDoMapper.selectByPrimaryKey(commentId);
        if (commentDo == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该评论不存在");
        }
        if (!commentDo.getUserId().equals(user.getId())){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该评论不是你发布的");
        }
        int flag = commentDoMapper.deleteByPrimaryKey(commentId);
        if (flag > 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<CommentDo> listComments(Integer postId) throws SportException {
        if (null == postId){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该文章不存在");
        }
        List<CommentDo> commentDos = commentDoMapper.selectAllByPostId(postId);
        return commentDos;
    }

    @Override
    public List<CommentDo> listMyComments(String telephone) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<CommentDo> commentDos = commentDoMapper.selectMyComments(user.getId());
        return commentDos;
    }

    @Override
    public void update(String telephone, Integer commentId, String content) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        CommentDo commentDo = commentDoMapper.selectByPrimaryKey(commentId);
        if (null == commentDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "该评论不存在");
        }
        if (!commentDo.getUserId().equals(user.getId())){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该评论不是你发表的");
        }

        commentDo.setContent(content);
        commentDoMapper.updateByPrimaryKeySelective(commentDo);
    }
}
