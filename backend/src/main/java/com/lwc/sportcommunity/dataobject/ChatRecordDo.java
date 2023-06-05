package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class ChatRecordDo implements Serializable {
    private Integer chatId;

    private Integer clubId;

    private Integer userId;

    private Date sendTime;

    private String content;

    private static final long serialVersionUID = 1L;

    public ChatRecordDo(Integer chatId, Integer clubId, Integer userId, Date sendTime, String content) {
        this.chatId = chatId;
        this.clubId = clubId;
        this.userId = userId;
        this.sendTime = sendTime;
        this.content = content;
    }

    public ChatRecordDo() {
        super();
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}