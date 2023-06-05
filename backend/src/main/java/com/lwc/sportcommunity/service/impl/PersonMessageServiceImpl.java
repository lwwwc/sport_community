package com.lwc.sportcommunity.service.impl;

import com.lwc.sportcommunity.dao.PrivateMessageDoMapper;
import com.lwc.sportcommunity.dataobject.ChatRecordDo;
import com.lwc.sportcommunity.dataobject.PrivateMessageDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.netty.ChatMsg;
import com.lwc.sportcommunity.service.PersonMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Create by LWC on 2023/4/29 17:34
 */
@Service
public class PersonMessageServiceImpl implements PersonMessageService {
    @Autowired
    private PrivateMessageDoMapper privateMessageDoMapper;

    @Override
    public Integer saveMsg(ChatMsg chatMsg) {
        PrivateMessageDo privateMessageDo = new PrivateMessageDo();
        privateMessageDo.setCreatedAt(new Date());
        privateMessageDo.setFromUserId(chatMsg.getUserId());
        privateMessageDo.setMessage(chatMsg.getMsg());
        privateMessageDo.setToUserId(chatMsg.getClubId());

        privateMessageDoMapper.insertSelective(privateMessageDo);
        return privateMessageDo.getChatId();
    }


    @Override
    public List<PrivateMessageDo> listUnReadMsgList(Integer msgId, Integer myId, Integer friendId) throws SportException {
        List<PrivateMessageDo> privateMessageDos = privateMessageDoMapper.selectUnReadMsgList(msgId, myId, friendId);
        return privateMessageDos;
    }
}
