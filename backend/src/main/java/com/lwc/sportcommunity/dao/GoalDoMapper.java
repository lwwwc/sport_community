package com.lwc.sportcommunity.dao;

import com.lwc.sportcommunity.dataobject.GoalDo;

import java.time.LocalDateTime;
import java.util.List;

public interface GoalDoMapper {
    int deleteByPrimaryKey(Integer goalId);

    int insert(GoalDo record);

    int insertSelective(GoalDo record);

    GoalDo selectByPrimaryKey(Integer goalId);

    List<GoalDo> selectByUserId(Integer userId);

    List<GoalDo> selectByReminderTime(LocalDateTime time);

    int updateByPrimaryKeySelective(GoalDo record);

    int updateByPrimaryKey(GoalDo record);
}