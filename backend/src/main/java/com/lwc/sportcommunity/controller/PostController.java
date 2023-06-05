package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.dataobject.PostDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.pagehelper.PageRequest;
import com.lwc.sportcommunity.pagehelper.PageResult;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lwc.sportcommunity.controller.BaseController.CONTENT_TYPE_FORMED;

/**
 * Create by LWC on 2023/3/31 13:03
 */
@RestController
@RequestMapping("/post")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    //发帖
    @RequestMapping(value = "/create", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType updatePassword(@RequestParam(name = "telephone")String telephone,
                                           @RequestParam(name = "title")String title,
                                           @RequestParam(name = "content")String content) throws SportException {
        postService.publish(telephone, title, content);
        return CommonReturnType.create(null);
    }

    //查看单个博客
    @RequestMapping(value = "/look", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType lookup(@RequestParam(name = "postId")String postId) throws SportException {

        PostDo postDo = postService.selectOne(Integer.parseInt(postId));
        return CommonReturnType.create(postDo);
    }

    //删除我的某个博文
    @RequestMapping(value = "/delete", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType deletePost(@RequestParam("telephone")String telephone,
                                       @RequestParam("postId")Integer postId) throws SportException {
        boolean flag = postService.deletePost(telephone, postId);
        if (flag){
            return CommonReturnType.create(null);
        }else {
            return CommonReturnType.create(null,"fail");
        }
    }


    //展示我发布的博文
    @RequestMapping(value = "/listMyPost", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType list(@RequestParam(name = "telephone")String telephone) throws SportException {
        List<PostDo> postDos = postService.listMyPost(telephone);
        return CommonReturnType.create(postDos);
    }

    //修改我的谋篇博文
    @RequestMapping(value = "/updatePost", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType updatePost(@RequestParam(name = "telephone")String telephone,
                                       @RequestParam(name = "postId")Integer postId,
                                       @RequestParam(name = "title")String title,
                                       @RequestParam(name = "content")String content) throws SportException {
        postService.updatePost(telephone, postId, title, content);
        return CommonReturnType.create(null);
    }

    //别人查看校内知识库可按时间,可按浏览量排序，分页
    @PostMapping(value = "/listAll")
    public CommonReturnType listAll(@RequestBody PageRequest pageQuery) throws SportException {
        PageResult page = postService.findPage(pageQuery);
        return CommonReturnType.create(page);
    }


}
