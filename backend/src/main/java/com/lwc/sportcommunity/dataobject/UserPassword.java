package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;

public class UserPassword implements Serializable {
    private Integer id;

    private String encrptPassword;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public UserPassword(Integer id, String encrptPassword, Integer userId) {
        this.id = id;
        this.encrptPassword = encrptPassword;
        this.userId = userId;
    }

    public UserPassword() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword == null ? null : encrptPassword.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}