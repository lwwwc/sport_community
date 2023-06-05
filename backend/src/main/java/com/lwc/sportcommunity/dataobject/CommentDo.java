package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class CommentDo implements Serializable {
    private Integer id;

    private Integer postId;

    private Integer userId;

    private Date createTime;

    private String content;

    private static final long serialVersionUID = 1L;

    public CommentDo(Integer id, Integer postId, Integer userId, Date createTime, String content) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.createTime = createTime;
        this.content = content;
    }

    public CommentDo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}