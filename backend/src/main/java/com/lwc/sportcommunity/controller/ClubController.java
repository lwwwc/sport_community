package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.dataobject.ClubDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by LWC on 2023/4/12 15:54
 */
@RestController
@RequestMapping("/club")
public class ClubController extends BaseController {

    @Autowired
    private ClubService clubService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType create(@RequestParam("clubName")String clubName,
                                   @RequestParam("clubDescription")String clubDescription,
                                   @RequestParam("telephone")String telephone) throws SportException {
        clubService.add(clubName, clubDescription, telephone);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/join", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType join(@RequestParam("clubId")Integer clubId,
                                 @RequestParam("telephone")String telephone) throws SportException {
        clubService.join(clubId, telephone);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
     public CommonReturnType list(){
        List<ClubDo> list = clubService.list();
        return CommonReturnType.create(list);
    }

    @RequestMapping(value = "/listMyClub", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listMyClub(@RequestParam("telephone")String telephone) throws SportException {
        List<ClubDo> list = clubService.listMyClubs(telephone);
        return CommonReturnType.create(list);
    }

    @RequestMapping(value = "/look", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType look(@RequestParam("clubId")Integer clubId) throws SportException {
        ClubDo clubDo = clubService.look(clubId);
        return CommonReturnType.create(clubDo);
    }

}
