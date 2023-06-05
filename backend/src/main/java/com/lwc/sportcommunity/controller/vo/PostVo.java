package com.lwc.sportcommunity.controller.vo;

import lombok.Data;

import java.util.Date;

/**
 * Create by LWC on 2023/4/7 17:50
 */
@Data
public class PostVo {
    private Integer postId;
    private String title;
    private String content;
    private Integer viewCount;
    private String createTime;
    private String nickname;
    private Boolean isLiked;
    private Integer likeCnt;
}
