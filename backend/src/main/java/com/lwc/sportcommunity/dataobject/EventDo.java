package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class EventDo implements Serializable {
    private Integer eventId;

    private String eventName;

    private Date eventDatetime;

    private String location;

    private Integer clubId;

    private Integer userId;

    private String eventDesc;

    private static final long serialVersionUID = 1L;

    public EventDo(Integer eventId, String eventName, Date eventDatetime, String location, Integer clubId, Integer userId, String eventDesc) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDatetime = eventDatetime;
        this.location = location;
        this.clubId = clubId;
        this.userId = userId;
        this.eventDesc = eventDesc;
    }

    public EventDo() {
        super();
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public Date getEventDatetime() {
        return eventDatetime;
    }

    public void setEventDatetime(Date eventDatetime) {
        this.eventDatetime = eventDatetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc == null ? null : eventDesc.trim();
    }
}