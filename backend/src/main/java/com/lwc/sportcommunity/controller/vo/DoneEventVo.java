package com.lwc.sportcommunity.controller.vo;

import lombok.Data;

import java.util.Date;

/**
 * Create by LWC on 2023/4/22 21:00
 */
@Data
public class DoneEventVo {

    private Integer eventId;

    private String eventName;

    private Date eventDatetime;

    private String location;

    private Integer clubId;

    private Integer userId;

    private String eventDesc;

    private Integer rate;
}
