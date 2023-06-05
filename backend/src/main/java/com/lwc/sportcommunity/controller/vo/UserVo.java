package com.lwc.sportcommunity.controller.vo;

import lombok.Data;

/**
 * Create by LWC on 2023/3/28 15:49
 */
@Data
public class UserVo {
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telephone;
    private String faceImage;
    private String faceImageBig;
    private String signature;
}
