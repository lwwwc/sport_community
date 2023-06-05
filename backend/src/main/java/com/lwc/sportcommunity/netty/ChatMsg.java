package com.lwc.sportcommunity.netty;

import lombok.Data;

/**
 * Create by LWC on 2023/4/24 22:33
 */
@Data
public class ChatMsg {
    private Integer clubId;
    private String msg;
    //发送者
    private Integer userId;
    private Integer msgId;
}
