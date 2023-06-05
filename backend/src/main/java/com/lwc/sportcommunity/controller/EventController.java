package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.controller.vo.DoneEventVo;
import com.lwc.sportcommunity.dataobject.EventDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.EventService;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by LWC on 2023/4/12 21:44
 */
@RestController
@RequestMapping("/event")
public class EventController extends  BaseController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType create(@RequestParam("telephone")String telephone,
                                   @RequestParam("clubId")Integer clubId,
                                   @RequestParam("eventName")String eventName,
                                   @RequestParam("eventDesc")String eventDesc,
                                   @RequestParam("eventDate")String eventDate,
                                   @RequestParam("location")String location) throws SportException {
        eventService.addEvent(telephone, clubId, eventName, eventDesc, eventDate, location);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType list(@RequestParam("clubId")Integer clubId) throws SportException {
        List<EventDo> list = eventService.list(clubId);
        return CommonReturnType.create(list);
    }

    @RequestMapping(value = "/listNow", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listNow(@RequestParam("clubId")Integer clubId) throws SportException {
        List<EventDo> list = eventService.listNow(clubId);
        return CommonReturnType.create(list);
    }

    @RequestMapping(value = "/listMyPublish", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listMyPublish(@RequestParam("clubId")Integer clubId,
                                          @RequestParam("telephone")String telephone) throws SportException {
        List<EventDo> eventDos = eventService.listMyPublish(clubId, telephone);
        return CommonReturnType.create(eventDos);
    }

    //展示正在报名的活动
    @RequestMapping(value = "/listMyJoin", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listMyJoin(@RequestParam("clubId")Integer clubId,
                                       @RequestParam("telephone")String telephone) throws SportException {
        List<EventDo> eventDos = eventService.listMyJoin(clubId, telephone);
        return CommonReturnType.create(eventDos);
    }

    //展示已签到 待评价的活动
    @RequestMapping(value = "/listMyDone", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listMyDone(@RequestParam("clubId")Integer clubId,
                                       @RequestParam("telephone")String telephone)throws SportException{
        List<DoneEventVo> eventDos = eventService.listMyDone(clubId, telephone);
        return CommonReturnType.create(eventDos);
    }


    @RequestMapping(value = "/join", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType join(@RequestParam("telephone")String telephone,
                                 @RequestParam("eventId")Integer eventId) throws SportException {

        eventService.join(telephone, eventId);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/cancel", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType cancelJoin(@RequestParam("eventId")Integer eventId,
                                       @RequestParam("telephone")String telephone) throws SportException {
        eventService.cancelJoin(eventId, telephone);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/getOtp", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getOtp(@RequestParam("telephone")String telephone,
                                   @RequestParam("eventId")Integer eventId) throws SportException{
        String otp = eventService.getOtp(telephone, eventId);
        return CommonReturnType.create(otp);
    }

    @RequestMapping(value = "/check", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType check(@RequestParam("telephone")String telephone,
                                  @RequestParam("clubId")Integer clubId,
                                  @RequestParam("otpCode")String otpCode) throws SportException{
        eventService.check(telephone, clubId, otpCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/rate", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType rate(@RequestParam("telephone")String telephone,
                                 @RequestParam("eventId")Integer eventId,
                                 @RequestParam("content")Integer content) throws SportException {
        eventService.rate(telephone, eventId, content);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/look", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType look(@RequestParam("eventId")Integer eventId) throws SportException{
        EventDo eventDo = eventService.look(eventId);
        return CommonReturnType.create(eventDo);
    }


    @RequestMapping(value = "/listAttend", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listAttend(@RequestParam("eventId")Integer eventId) throws SportException{
        List<User> users = eventService.listAttend(eventId);
        return CommonReturnType.create(users);
    }

    @RequestMapping(value = "/listUnAttend", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType listUnAttend(@RequestParam("eventId")Integer eventId) throws SportException{
        List<User> users = eventService.listUnAttend(eventId);
        return CommonReturnType.create(users);
    }







}
