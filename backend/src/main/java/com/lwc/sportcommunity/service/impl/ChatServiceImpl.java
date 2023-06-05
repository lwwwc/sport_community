package com.lwc.sportcommunity.service.impl;

import com.lwc.sportcommunity.dao.ChatRecordDoMapper;
import com.lwc.sportcommunity.dao.ClubDoMapper;
import com.lwc.sportcommunity.dao.ClubMemberDoMapper;
import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dataobject.ChatRecordDo;
import com.lwc.sportcommunity.dataobject.ClubDo;
import com.lwc.sportcommunity.dataobject.ClubMemberDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.netty.ChatMsg;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.ChatService;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Create by LWC on 2023/4/17 19:39
 */
@Service("chatServiceImpl")
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRecordDoMapper chatRecordDoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClubDoMapper clubDoMapper;

    @Autowired
    private ClubMemberDoMapper clubMemberDoMapper;

    @Override
    public void send(String telephone, Integer clubId, String content) throws SportException {

        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        ClubDo clubDo = clubDoMapper.selectByPrimaryKey(clubId);
        if (null == clubDo){
            throw new SportException(EmSportError.CLUB_NOT_EXIST);
        }
        if (null == content || content.equals("")){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "发送的内容为空");
        }

        ClubMemberDo clubMemberDo = clubMemberDoMapper.selectByUserIdAndClubId(user.getId(), clubId);
        if (null == clubMemberDo){
            throw new SportException(EmSportError.USER_NOT_IN_CLUB);
        }

        ChatRecordDo chatRecordDo = new ChatRecordDo();
        chatRecordDo.setClubId(clubId);
        chatRecordDo.setContent(content);
        chatRecordDo.setUserId(user.getId());
        chatRecordDo.setSendTime(new Date());

        chatRecordDoMapper.insertSelective(chatRecordDo);
    }

    @Override
    public Integer saveMsg(ChatMsg chatMsg) {
        ChatRecordDo chatRecordDo = new ChatRecordDo();
        chatRecordDo.setClubId(chatMsg.getClubId());
        chatRecordDo.setContent(chatMsg.getMsg());
        chatRecordDo.setUserId(chatMsg.getUserId());
        chatRecordDo.setSendTime(new Date());
        chatRecordDoMapper.insertSelective(chatRecordDo);
        return chatRecordDo.getChatId();
    }

    @Override
    public List<ChatRecordDo> listUnReadMsgList(Integer msgId, Integer clubId) throws SportException {
        List<ChatRecordDo> chatRecordDos = chatRecordDoMapper.selectUnReadMsgList(msgId, clubId);
        return chatRecordDos;
    }
}
