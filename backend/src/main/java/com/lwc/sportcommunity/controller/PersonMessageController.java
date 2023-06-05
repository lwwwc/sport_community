package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.dataobject.ChatRecordDo;
import com.lwc.sportcommunity.dataobject.PrivateMessageDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.PersonMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by LWC on 2023/4/29 22:47
 */
@RestController
@RequestMapping("/ps")
public class PersonMessageController extends BaseController {

    @Autowired
    private PersonMessageService personMessageService;


    @RequestMapping(value = "/getUnReadMsgList", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getUnReadMsgList(@RequestParam("msgId")Integer msgId,
                                             @RequestParam("myId")Integer myId,
                                             @RequestParam("friendId")Integer friendId)throws SportException {
        List<PrivateMessageDo> privateMessageDos = personMessageService.listUnReadMsgList(msgId, myId, friendId);
        return CommonReturnType.create(privateMessageDos);
    }

}
