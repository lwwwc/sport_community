package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.dataobject.ChatRecordDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.netty.ChatMsg;

import java.util.List;

/**
 * Create by LWC on 2023/4/17 19:39
 */
public interface ChatService {

    void send(String telephone, Integer clubId, String content) throws SportException;

    Integer saveMsg(ChatMsg chatMsg);

    List<ChatRecordDo> listUnReadMsgList(Integer msgId, Integer clubId) throws SportException;
}
