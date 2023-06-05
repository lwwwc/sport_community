package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class EventAttendenDo implements Serializable {
    private Integer joinId;

    private Integer eventId;

    private Integer userId;

    private Date checkInTime;

    private Integer rating;

    private static final long serialVersionUID = 1L;

    public EventAttendenDo(Integer joinId, Integer eventId, Integer userId, Date checkInTime, Integer rating) {
        this.joinId = joinId;
        this.eventId = eventId;
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.rating = rating;
    }

    public EventAttendenDo() {
        super();
    }

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}