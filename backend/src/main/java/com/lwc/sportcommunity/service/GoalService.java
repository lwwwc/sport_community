package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.dataobject.GoalDo;
import com.lwc.sportcommunity.error.SportException;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * Create by LWC on 2023/5/2 16:56
 */
public interface GoalService {
    public void add(Integer userId,String goalName, String reminderTime) throws SportException;
    public List<GoalDo> list(Integer userId);
    public void delete(Integer goalId);
}
