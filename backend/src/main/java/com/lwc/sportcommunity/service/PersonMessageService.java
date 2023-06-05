package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.dataobject.ChatRecordDo;
import com.lwc.sportcommunity.dataobject.PrivateMessageDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.netty.ChatMsg;

import java.util.List;

/**
 * Create by LWC on 2023/4/29 17:33
 */
public interface PersonMessageService {
    Integer saveMsg(ChatMsg chatMsg);

    List<PrivateMessageDo> listUnReadMsgList(Integer msgId,Integer myId, Integer friendId) throws SportException;
}
