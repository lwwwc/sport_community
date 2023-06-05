package com.lwc.sportcommunity.service.impl;

import com.lwc.sportcommunity.dao.ClubDoMapper;
import com.lwc.sportcommunity.dao.ClubMemberDoMapper;
import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dataobject.ClubDo;
import com.lwc.sportcommunity.dataobject.ClubMemberDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by LWC on 2023/4/12 16:03
 */
@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClubDoMapper clubDoMapper;

    @Autowired
    private ClubMemberDoMapper clubMemberDoMapper;

    @Override
    @Transactional
    public void add(String clubName, String clubDescription, String telephone) throws SportException {
        if (null == clubName){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        ClubDo clubDo = new ClubDo();
        clubDo.setClubDesc(clubDescription);
        clubDo.setClubName(clubName);
        try {
            clubDoMapper.insertSelective(clubDo);
            ClubMemberDo clubMemberDo = new ClubMemberDo();
            clubMemberDo.setClubId(clubDo.getClubId());
            clubMemberDo.setUserId(user.getId());
            clubMemberDoMapper.insertSelective(clubMemberDo);
        }catch (DuplicateKeyException e){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"重复添加俱乐部");
        }
    }

    @Override
    public void join(Integer clubId, String telephone) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        ClubMemberDo clubMemberDo = new ClubMemberDo();
        clubMemberDo.setUserId(user.getId());
        clubMemberDo.setClubId(clubId);
        try {
            clubMemberDoMapper.insertSelective(clubMemberDo);
        }catch (DuplicateKeyException e){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"你已添加该俱乐部");
        }
    }

    @Override
    public List<ClubDo> list() {
        List<ClubDo> clubDos = clubDoMapper.selectAll();
        return clubDos;
    }

    @Override
    public List<ClubDo> listMyClubs(String telephone) throws SportException {
        User user = userMapper.selectByTelephone(telephone);
        if (null == user){
            throw new SportException(EmSportError.USER_NOT_EXIST);
        }
        List<ClubMemberDo> clubMemberDos = clubMemberDoMapper.selectByUserId(user.getId());
        List<ClubDo> list = new ArrayList<>(clubMemberDos.size());
        for (ClubMemberDo clubMemberDo : clubMemberDos){
            ClubDo clubDo = clubDoMapper.selectByPrimaryKey(clubMemberDo.getClubId());
            list.add(clubDo);
        }
        return list;
    }

    @Override
    public ClubDo look(Integer clubId) throws SportException {
        if (clubId == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        ClubDo clubDo = clubDoMapper.selectByPrimaryKey(clubId);
        return clubDo;
    }
}
