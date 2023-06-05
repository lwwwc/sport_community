package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.dataobject.ChatRecordDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by LWC on 2023/4/17 19:38
 */
@RestController
@RequestMapping("/chat")
public class ChatController extends BaseController {

    @Autowired
    private ChatService chatService;

    @RequestMapping(value = "/send", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType send(@RequestParam("telephone")String telephone,
                                 @RequestParam("clubId")Integer clubId,
                                 @RequestParam("content")String content) throws SportException{
        chatService.send(telephone, clubId, content);
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType list(@RequestParam("telephone")String telephone){
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/getUnReadMsgList", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getUnReadMsgList(@RequestParam("msgId")Integer msgId,
                                             @RequestParam("clubId")Integer clubId)throws SportException{
        List<ChatRecordDo> chatRecordDos = chatService.listUnReadMsgList(msgId, clubId);
        return CommonReturnType.create(chatRecordDos);
    }



}
