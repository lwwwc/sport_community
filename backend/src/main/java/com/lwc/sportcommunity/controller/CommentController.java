package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.dataobject.CommentDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by LWC on 2023/4/1 23:58
 */
@RestController
@RequestMapping(value ="/comment")
public class CommentController extends BaseController {


    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType create(@RequestParam("telephone")String telephone,
                                   @RequestParam("postId")Integer postId,
                                   @RequestParam("content")String content) throws SportException {
        commentService.publish(telephone,postId, content);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType deleteComment(@RequestParam("telephone")String telephone,
                                          @RequestParam("commentId")Integer commentId) throws SportException {
        boolean flag = commentService.deleteComment(telephone, commentId);
        if (flag){
            return CommonReturnType.create(null);
        }else{
            return CommonReturnType.create(null,"fail");
        }
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listComments(@RequestParam("postId")Integer postId) throws SportException {
        List<CommentDo> commentDos = commentService.listComments(postId);
        return CommonReturnType.create(commentDos);
    }

    @RequestMapping(value = "/listMyComments", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listMyComments(@RequestParam("telephone")String telephone) throws SportException {
        List<CommentDo> commentDos = commentService.listMyComments(telephone);
        return CommonReturnType.create(commentDos);
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType update(@RequestParam("telephone")String telephone,
                                   @RequestParam("content")String content,
                                   @RequestParam("commentId")Integer commentId) throws SportException {
        commentService.update(telephone, commentId, content);
        return CommonReturnType.create(null);
    }






}
