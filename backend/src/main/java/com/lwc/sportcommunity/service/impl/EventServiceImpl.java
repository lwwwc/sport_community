package com.lwc.sportcommunity.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.lwc.sportcommunity.controller.vo.DoneEventVo;
import com.lwc.sportcommunity.dao.*;
import com.lwc.sportcommunity.dataobject.*;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.beans.Beans;
import java.nio.channels.spi.SelectorProvider;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Create by LWC on 2023/4/12 21:49
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDoMapper eventDoMapper;

    @Autowired
    private ClubMemberDoMapper clubMemberDoMapper;

    @Autowired
    private ClubDoMapper clubDoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EventAttendenDoMapper eventAttendenDoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void addEvent(String telephone, Integer clubId, String eventName, String eventDesc, String eventDate, String location) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if(null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }

        ClubMemberDo clubMemberDo = clubMemberDoMapper.selectByUserIdAndClubId(user.getId(), clubId);

        if (null == clubMemberDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"你不在这个俱乐部内不能发布活动");
        }
        EventDo eventDo = new EventDo();
        eventDo.setClubId(clubId);
        eventDo.setEventDesc(eventDesc);
        eventDo.setEventName(eventName);
        eventDo.setLocation(location);
        eventDo.setUserId(user.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            System.out.println(eventDate);
            date = sdf.parse(eventDate);
        }catch (ParseException e){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"活动时间规格不符");
        }
        eventDo.setEventDatetime(date);
        eventDoMapper.insertSelective(eventDo);
    }

    @Override
    public List<EventDo> list(Integer clubId) throws SportException {
        ClubDo clubDo = clubDoMapper.selectByPrimaryKey(clubId);
        if (null == clubDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"该俱乐部不存在");
        }
        List<EventDo> eventDos = eventDoMapper.selectAllByClubId(clubId);
        return eventDos;
    }

    @Override
    public List<EventDo> listNow(Integer clubId) throws SportException {
        Date date = new Date();
        List<EventDo> list = list(clubId);
        List<EventDo> ans = new ArrayList<>();
        for (EventDo eventDo : list){
            if (date.before(eventDo.getEventDatetime())){
                ans.add(eventDo);
            }
        }
        return ans;
    }

    @Override
    public List<EventDo> listMyJoin(Integer clubId, String telephone) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<EventAttendenDo> eventAttendenDos = eventAttendenDoMapper.selectByUserId(user.getId());
        List<EventDo> ans = new ArrayList<>();
        for (EventAttendenDo eventAttendenDo : eventAttendenDos){
            if (eventAttendenDo.getCheckInTime() != null){
                continue;
            }
            EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventAttendenDo.getEventId());
            if (eventDo.getClubId().equals(clubId)){
                ans.add(eventDo);
            }
        }
        return ans;
    }

    @Override
    public List<DoneEventVo> listMyDone(Integer clubId, String telephone) throws SportException {

        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<EventAttendenDo> eventAttendenDos = eventAttendenDoMapper.selectByUserId(user.getId());
        List<DoneEventVo> ans = new ArrayList<>();
        for (EventAttendenDo eventAttendenDo : eventAttendenDos){
            if (eventAttendenDo.getCheckInTime() == null){
                continue;
            }
            EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventAttendenDo.getEventId());
            if (eventDo.getClubId().equals(clubId)){
                DoneEventVo doneEventVo = new DoneEventVo();
                BeanUtils.copyProperties(eventDo, doneEventVo);
                doneEventVo.setRate(eventAttendenDo.getRating());
                ans.add(doneEventVo);
            }
        }
        return ans;
    }

    @Override
    public List<EventDo> listMyPublish(Integer clubId, String telephone) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if(null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<EventDo> eventDos = eventDoMapper.selectAllByClubId(clubId);
        List<EventDo> ans = new ArrayList<>();
        for (int i = eventDos.size()-1; i >=0; i--){
            EventDo eventDo = eventDos.get(i);
            if (eventDo.getUserId().equals(user.getId())){
                ans.add(eventDo);
            }
        }
        return ans;
    }

    @Override
    public void join(String telephone, Integer eventId) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }

        EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventId);
        if (null == eventDo){
            throw new SportException(EmSportError.EVENT_NOT_EXIST);
        }

        ClubMemberDo clubMemberDo = clubMemberDoMapper.selectByUserIdAndClubId(user.getId(), eventDo.getClubId());
        if (null == clubMemberDo){
            throw  new SportException(EmSportError.USER_NOT_IN_CLUB);
        }

        EventAttendenDo eventAttendenDo = new EventAttendenDo();
        eventAttendenDo.setEventId(eventId);
        eventAttendenDo.setUserId(user.getId());
        try {
            eventAttendenDoMapper.insertSelective(eventAttendenDo);
        }catch (DuplicateKeyException e) {
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "你已经报名该活动");
        }
    }

    @Override
    public String getOtp(String telephone, Integer eventId) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventId);
        if (null == eventDo){
            throw new SportException(EmSportError.EVENT_NOT_EXIST);
        }

        ClubMemberDo clubMemberDo = clubMemberDoMapper.selectByUserIdAndClubId(user.getId(), eventDo.getClubId());
        if (null == clubMemberDo){
            throw  new SportException(EmSportError.USER_NOT_IN_CLUB);
        }

        if (!eventDo.getUserId().equals(user.getId())){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"您不是活动发起者，不能发起签到");
        }


        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        String key = eventId + "";

        if (redisTemplate.hasKey(key)){
            throw new SportException(EmSportError.USER_OTP_REPEAT);
        }
        //存Redis,设置过期时间
        redisTemplate.opsForValue().set(key,otpCode);
        redisTemplate.expire(key,1, TimeUnit.MINUTES);

        //otp验证码通过短信通道发送给用户
        System.out.println("eventId:"+eventId+"  otpCode:"+otpCode);

        return otpCode;
    }

    @Override
    public void check(String telephone,Integer clubId, String otpCode) throws SportException {
        List<EventDo> eventDos = listMyJoin(clubId, telephone);
        User user = userMapper.selectByTelephone(telephone);


        ClubMemberDo clubMemberDo = clubMemberDoMapper.selectByUserIdAndClubId(user.getId(), clubId);
        if (null == clubMemberDo){
            throw  new SportException(EmSportError.USER_NOT_IN_CLUB);
        }

        for (EventDo eventDo : eventDos){
            String redisOtpCode = (String)redisTemplate.opsForValue().get(eventDo.getEventId()+"");
            if (StringUtils.equals(otpCode, redisOtpCode)) {
                EventAttendenDo eventAttendenDo = eventAttendenDoMapper.selectByEventIdAndUserId(eventDo.getEventId(), user.getId());
                eventAttendenDo.setCheckInTime(new Date());
                eventAttendenDoMapper.updateByPrimaryKeySelective(eventAttendenDo);
                return;
            }
        }
        throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"签到验证码不存在");
    }

    @Override
    public void rate(String telephone, Integer eventId, Integer content) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventId);
        if (null == eventDo){
            throw new SportException(EmSportError.EVENT_NOT_EXIST);
        }

        ClubMemberDo clubMemberDo = clubMemberDoMapper.selectByUserIdAndClubId(user.getId(), eventDo.getClubId());
        if (null == clubMemberDo){
            throw  new SportException(EmSportError.USER_NOT_IN_CLUB);
        }

        EventAttendenDo eventAttendenDo = eventAttendenDoMapper.selectByEventIdAndUserId(eventDo.getEventId(), user.getId());
        if (null == eventAttendenDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"你未参加该活动");
        }
        eventAttendenDo.setRating(content);
        eventAttendenDoMapper.updateByPrimaryKeySelective(eventAttendenDo);
    }

    @Override
    public EventDo look(Integer eventId) throws SportException {
        if (null == eventId){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventId);
        return eventDo;
    }

    @Override
    public void cancelJoin(Integer eventId, String telephone) throws SportException {
        if (null == eventId){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        EventAttendenDo eventAttendenDo = eventAttendenDoMapper.selectByEventIdAndUserId(eventId, user.getId());
        if (null == eventAttendenDo){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR, "您未参加该活动");
        }
        eventAttendenDoMapper.deleteByPrimaryKey(eventAttendenDo.getJoinId());
    }

    @Override
    public List<User> listAttend(Integer eventId) throws SportException {
        EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventId);
        if (null == eventDo){
            throw new SportException(EmSportError.EVENT_NOT_EXIST);
        }
        List<EventAttendenDo> eventAttendenDos = eventAttendenDoMapper.selectAttend(eventId);
        List<User> ans = new ArrayList<>();
        for (EventAttendenDo eventAttendenDo : eventAttendenDos){
            User user = userMapper.selectByPrimaryKey(eventAttendenDo.getUserId());
            if (null != user){
                ans.add(user);
            }
        }
        return ans;
    }

    @Override
    public List<User> listUnAttend(Integer eventId) throws SportException {
        EventDo eventDo = eventDoMapper.selectByPrimaryKey(eventId);
        List<User> attendUsers = listAttend(eventId);
        if (null == eventDo){
            throw new SportException(EmSportError.EVENT_NOT_EXIST);
        }
        List<ClubMemberDo> allUsers = clubMemberDoMapper.selectByClubId(eventDo.getClubId());
        Set<Integer> userIds = new HashSet<>();
        for (User user : attendUsers){
            userIds.add(user.getId());
        }
        List<User> unAttenden = new ArrayList<>();
        for (ClubMemberDo clubMemberDo : allUsers){
            if(!userIds.contains(clubMemberDo.getUserId())){
                User user = userMapper.selectByPrimaryKey(clubMemberDo.getUserId());
                unAttenden.add(user);
            }
        }
        return unAttenden;
    }
}
