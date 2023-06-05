package com.lwc.sportcommunity.service.impl;

import com.lwc.sportcommunity.dao.GoalDoMapper;
import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dataobject.GoalDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXParseException;
import sun.net.smtp.SmtpProtocolException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Create by LWC on 2023/5/2 16:56
 */
@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalDoMapper goalRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Integer userId, String goalName, String reminderTime) throws SportException {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        GoalDo goalDo = new GoalDo();
        goalDo.setGoalName(goalName);
        goalDo.setUserId(userId);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            System.out.println(reminderTime);
            date = sdf.parse(reminderTime);
        }catch (ParseException e){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"活动时间规格不符");
        }
        goalDo.setReminderTime(date);
        goalRepository.insertSelective(goalDo);
    }

    @Override
    public List<GoalDo> list(Integer userId) {
        List<GoalDo> goalDos = goalRepository.selectByUserId(userId);
        return goalDos;
    }

    @Override
    public void delete(Integer goalId) {
        goalRepository.deleteByPrimaryKey(goalId);
    }
}
