package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by LWC on 2023/4/1 23:59
 */
@RestController
@RequestMapping(value ="/like")
public class LikeController extends BaseController {

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType create(@RequestParam("postId")Integer postId,
                                   @RequestParam("telephone")String telephone) throws SportException {
        likeService.create(telephone, postId);

        return CommonReturnType.create(null);

    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType delete(@RequestParam("postId")Integer postId,
                                   @RequestParam("telephone")String telephone) throws SportException {

        likeService.delete(telephone, postId);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/getCnt", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getCnt(@RequestParam("postId")Integer postId){
        int count = likeService.getCount(postId);
        return CommonReturnType.create(count);
    }
}
