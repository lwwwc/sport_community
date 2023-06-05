package com.lwc.sportcommunity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwc.sportcommunity.controller.vo.PostVo;
import com.lwc.sportcommunity.dao.LikeDoMapper;
import com.lwc.sportcommunity.dao.PostDoMapper;
import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dataobject.LikeDo;
import com.lwc.sportcommunity.dataobject.PostDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.pagehelper.PageRequest;
import com.lwc.sportcommunity.pagehelper.PageResult;
import com.lwc.sportcommunity.pagehelper.PageUtils;
import com.lwc.sportcommunity.service.PostService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.ELManager;
import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by LWC on 2023/3/31 13:04
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDoMapper postDoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LikeDoMapper likeDoMapper;

    @Override
    public void publish(String telephone, String title, String content) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (user == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "该手机号用户不存在");
        }
        PostDo postDo = new PostDo();
        postDo.setUserId(user.getId());
        postDo.setTitle(title);
        postDo.setContent(content);
        System.out.println(content);
        postDo.setCreateTime(new Date());
        postDoMapper.insertSelective(postDo);
    }

    @Override
    public List<PostDo> listMyPost(String telephone) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<PostDo> postDos = postDoMapper.selectMyPosts(user.getId());
        return postDos;
    }


    @Override
    public boolean deletePost(String telephone, Integer postId) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<PostDo> postDos = postDoMapper.selectMyPosts(user.getId());
        for(PostDo postDo : postDos){
            if (postDo.getId().equals(postId)){
                postDoMapper.deleteByPrimaryKey(postId);
                return true;
            }
        }
        throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该账号并无该篇论文");
    }

    @Override
    public void updatePost(String telephone, Integer postId, String title, String content) throws SportException {
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<PostDo> postDos = postDoMapper.selectMyPosts(user.getId());
        boolean flag = false;
        for (PostDo postDo : postDos){
            if (postDo.getId().equals(postId)){
                flag = true;
                break;
            }
        }
        if (!flag){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该账号没有这篇文章");
        }
        PostDo postDo = new PostDo();
        postDo.setId(postId);
        postDo.setTitle(title);
        postDo.setContent(content);
        postDoMapper.updateByPrimaryKeySelective(postDo);
    }

    @Override
    public PostDo selectOne(Integer postId) throws SportException {
        if (null == postId){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        PostDo postDo = postDoMapper.selectByPrimaryKey(postId);
        //浏览量加一
        postDoMapper.incrementViewCount(postId);
        return postDo;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) throws SportException {
        return PageUtils.getPageResult(pageRequest,getPageInfo(pageRequest));
    }

    private PageInfo<PostVo> getPageInfo(PageRequest pageRequest) throws SportException {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        int method = pageRequest.getMethod();
        PageHelper.startPage(pageNum, pageSize);

        Page<PostDo> postPage;
        if (method == 1){
             postPage = postDoMapper.selectPageOrderByTime();
        }else if (method == 2){
            postPage = postDoMapper.selectOrderByViewCount();
        }else {
            postPage = postDoMapper.selectAll();
        }
        List<PostDo> postDos = postPage.getResult();
        List<PostVo> postVos = convertFromPostDo(postDos, pageRequest.getTelephone());
        PageInfo<PostVo> result = new PageInfo<>();
        result.setPageNum(pageRequest.getPageNum());
        result.setPageSize(pageRequest.getPageSize());
        result.setTotal(postPage.getTotal());
        result.setPages(postPage.getPages());
        result.setList(postVos);
        return result;
    }

    private List<PostVo> convertFromPostDo(List<PostDo> list, String telephone) throws SportException {
        if (null == list){
            return null;
        }
        if (null == telephone){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"手机号为空");
        }
        User  looker = userMapper.selectByTelephone(telephone);
        if (null == looker){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }


        List<PostVo> postVos = new ArrayList<>();
        for (PostDo postDo : list){
            PostVo postVo = new PostVo();
            postVo.setContent(postDo.getContent());
            postVo.setCreateTime(postDo.getCreateTime().toString());
            postVo.setTitle(postDo.getTitle());
            postVo.setViewCount(postDo.getViewCount());
            User user = userMapper.selectByPrimaryKey(postDo.getUserId());
            postVo.setNickname(user.getName());
            postVo.setPostId(postDo.getId());
            LikeDo likeDo = likeDoMapper.selectByUserIdAndPostId(looker.getId(), postDo.getId());
            if (null == likeDo){
                postVo.setIsLiked(false);
            }else {
                postVo.setIsLiked(true);

            }
            Integer cnt = likeDoMapper.selectCntByPostId(postDo.getId());
            postVo.setLikeCnt(cnt);

            postVos.add(postVo);
        }
        return postVos;
    }
}
