package com.lwc.sportcommunity.hwkit;

import com.alibaba.fastjson.JSONObject;
import com.lwc.sportcommunity.controller.BaseController;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by LWC on 2023/5/24 16:28
 */
@Controller
@RequestMapping("/detection")
public class SportDetectionController extends BaseController {

    private HwUtil hwUtil = new HwUtil();

    @RequestMapping("/getCode")
    public String getCode(@RequestParam("code") String code, @RequestParam("state")Integer state){
        if (code == null){
            return "fail";
        }
        hwUtil.getAccessToken(code,state);
        return "show";
    }


    @ResponseBody
    @RequestMapping(value = "/getSportInfo", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getSportInfo(@RequestParam("userId") Integer userId) throws SportException {
        String sportInfo = hwUtil.getSportInfo(userId);
        SportVo sportVo = parseSportVo(sportInfo);
//        System.out.println(sportVo);
        return CommonReturnType.create(sportVo);
    }

    private SportVo parseSportVo(String sportInfo){
        JSONObject jsonObject = JSONObject.parseObject(sportInfo);
        List<JSONObject> group = (List)jsonObject.get("group");
        List<JSONObject> sampleSet = (List)group.get(0).get("sampleSet");
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i<3; i++){
            List<JSONObject> samplePoints = (List)sampleSet.get(i).get("samplePoints");
            List<JSONObject> values = (List)samplePoints.get(0).get("value");
            for (int j = 0; j<values.size(); j++){
                JSONObject value = values.get(j);
                String key = (String)value.get("fieldName");
                Integer val = (Integer) value.get("integerValue");
                if(val != null){
                    map.put(key,val.toString());
                }
                BigDecimal fVal = (BigDecimal) value.get("floatValue");
                if (fVal != null){
                    map.put(key,fVal.toString());
                }
            }
        }
        SportVo sportVo = new SportVo(Integer.parseInt(map.get("steps")),
                Double.parseDouble(map.get("distance")),
                Double.parseDouble(map.get("calories_total")));
        return sportVo;
    }

}
