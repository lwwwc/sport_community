package com.lwc.sportcommunity.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Create by LWC on 2023/3/18 0:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telephone;

    private String registerMode;

    private String thirdPartyId;

    private String encrpyPassword;

    private String faceImage;

    private String faceImageBig;

    private String signature;
}
