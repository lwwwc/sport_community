package com.lwc.sportcommunity.push;

import com.lwc.sportcommunity.dao.GoalDoMapper;
import com.lwc.sportcommunity.dataobject.GoalDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Create by LWC on 2023/5/3 10:49
 */
@Component
public class DailyReminderTask {
    @Autowired
    private GoalDoMapper goalDoMapper;

    @Autowired
    private PusherService pusherService;

    @Scheduled(cron = "0 * * * * ?")
    public void checkReminders(){
//        LocalDateTime.now()
        List<GoalDo> goalDos =
                goalDoMapper.selectByReminderTime(LocalDateTime.now());
        for (GoalDo goalDo : goalDos){
//            System.out.println("sending...");
            pusherService.sendReminder(goalDo.getUserId(),goalDo.getGoalName());
        }
    }
}
