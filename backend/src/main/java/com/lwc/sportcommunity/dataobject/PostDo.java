package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class PostDo implements Serializable {
    private Integer id;

    private String title;

    private Integer userId;

    private Integer viewCount;

    private Date createTime;

    private String content;

    private static final long serialVersionUID = 1L;

    public PostDo(Integer id, String title, Integer userId, Integer viewCount, Date createTime, String content) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.viewCount = viewCount;
        this.createTime = createTime;
        this.content = content;
    }

    public PostDo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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