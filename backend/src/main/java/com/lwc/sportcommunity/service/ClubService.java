package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.dataobject.ClubDo;
import com.lwc.sportcommunity.error.SportException;

import java.util.List;

/**
 * Create by LWC on 2023/4/12 16:03
 */
public interface ClubService {

    void add(String clubName, String clubDescription, String telephone) throws SportException;
    void  join(Integer clubId,String telephone) throws SportException;
    List<ClubDo> list();
    List<ClubDo> listMyClubs(String telephone) throws SportException;
    ClubDo look(Integer clubId) throws SportException;
}
