package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class PrivateMessageDo implements Serializable {
    private Integer chatId;

    private Integer fromUserId;

    private Integer toUserId;

    private String message;

    private Date createdAt;

    private static final long serialVersionUID = 1L;

    public PrivateMessageDo(Integer chatId, Integer fromUserId, Integer toUserId, String message, Date createdAt) {
        this.chatId = chatId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public PrivateMessageDo() {
        super();
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}