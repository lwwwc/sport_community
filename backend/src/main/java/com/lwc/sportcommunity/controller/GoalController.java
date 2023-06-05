package com.lwc.sportcommunity.controller;

import com.lwc.sportcommunity.dataobject.GoalDo;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.GoalService;
import com.sun.jdi.request.StepRequest;
import com.sun.prism.shader.DrawEllipse_LinearGradient_REFLECT_AlphaTest_Loader;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lwc.sportcommunity.controller.BaseController.CONTENT_TYPE_FORMED;

/**
 * Create by LWC on 2023/5/2 16:57
 */
@RestController
@RequestMapping("/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

   @RequestMapping(value = "/add", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType addGoal(@RequestParam("userId")Integer userId,
                                    @RequestParam("goalName")String goalName,
                                    @RequestParam("reminderTime")String reminderTime) throws SportException {
       goalService.add(userId, goalName, reminderTime);
       return CommonReturnType.create(null);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType list(@RequestParam("userId")Integer userId){
        List<GoalDo> list = goalService.list(userId);
        return CommonReturnType.create(list);
    }

    @RequestMapping(value = "/remove", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType remove(@RequestParam("goalId")Integer goalId){
        goalService.delete(goalId);
        return CommonReturnType.create(null);
    }

}
