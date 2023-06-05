package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class LikeDo implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer postId;

    private Date createdAt;

    private static final long serialVersionUID = 1L;

    public LikeDo(Integer id, Integer userId, Integer postId, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.postId = postId;
        this.createdAt = createdAt;
    }

    public LikeDo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}