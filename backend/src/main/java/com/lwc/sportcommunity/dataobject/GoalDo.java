package com.lwc.sportcommunity.dataobject;

import java.io.Serializable;
import java.util.Date;

public class GoalDo implements Serializable {
    private Integer goalId;

    private Integer userId;

    private String goalName;

    private Date reminderTime;

    private Boolean isCompleted;

    private static final long serialVersionUID = 1L;

    public GoalDo(Integer goalId, Integer userId, String goalName, Date reminderTime, Boolean isCompleted) {
        this.goalId = goalId;
        this.userId = userId;
        this.goalName = goalName;
        this.reminderTime = reminderTime;
        this.isCompleted = isCompleted;
    }

    public GoalDo() {
        super();
    }

    public Integer getGoalId() {
        return goalId;
    }

    public void setGoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName == null ? null : goalName.trim();
    }

    public Date getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}