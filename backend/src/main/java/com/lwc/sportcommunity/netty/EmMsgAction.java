package com.lwc.sportcommunity.netty;

/**
 * Create by LWC on 2023/4/25 17:46
 */
public enum EmMsgAction {

    CONNECT(1,"第一次(或重连)初始化连接"),
    CHAT(2,"聊天消息"),
    SIGN(3,"签收消息"),
    KEEPALIVE(4, "客户端保持心跳"),
    PCONNECT(5,"私聊第一次(或重连)初始化连接"),
    PCHAT(6,"聊天消息");

    private final Integer type;
    private final String content;

    EmMsgAction(Integer type, String content){
        this.type = type;
        this.content = content;
    }

    Integer getType(){
        return type;
    }
}
