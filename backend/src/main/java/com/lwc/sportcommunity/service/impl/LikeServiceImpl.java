package com.lwc.sportcommunity.service.impl;

import com.lwc.sportcommunity.dao.LikeDoMapper;
import com.lwc.sportcommunity.dao.PostDoMapper;
import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dataobject.LikeDo;
import com.lwc.sportcommunity.dataobject.PostDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Create by LWC on 2023/4/2 18:36
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDoMapper likeDoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostDoMapper postDoMapper;

    @Override
    public void create(String telephone, Integer postId) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (user == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "该手机号用户不存在");
        }
        PostDo postDo = postDoMapper.selectByPrimaryKey(postId);
        if (null == postDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该文章不存在");
        }

        LikeDo likeDo = new LikeDo();
        likeDo.setCreatedAt(new Date());
        likeDo.setUserId(user.getId());
        likeDo.setPostId(postId);

        try {
            likeDoMapper.insertSelective(likeDo);
        }catch (DuplicateKeyException ex){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "重复点赞");
        }
    }

    @Override
    public void delete(String telephone, Integer postId) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (user == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "该手机号用户不存在");
        }
        PostDo postDo = postDoMapper.selectByPrimaryKey(postId);
        if (null == postDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该文章不存在");
        }
        likeDoMapper.deleteByPostIdAndUserId(user.getId(), postId);
    }

    @Override
    public int getCount(Integer postId) {
        int cnt = likeDoMapper.selectCntByPostId(postId);
        return cnt;
    }
}
