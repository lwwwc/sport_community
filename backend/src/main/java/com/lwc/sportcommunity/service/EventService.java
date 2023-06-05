package com.lwc.sportcommunity.service;

import com.lwc.sportcommunity.controller.vo.DoneEventVo;
import com.lwc.sportcommunity.dataobject.EventDo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.error.SportException;
import com.sun.xml.internal.xsom.impl.util.SchemaTreeTraverser;
import org.omg.PortableInterceptor.INACTIVE;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.UUID;

/**
 * Create by LWC on 2023/4/12 21:49
 */
public interface EventService {
    void addEvent(String telephone, Integer clubId,
                  String eventName, String eventDesc,
                  String eventDate, String location) throws SportException;

    List<EventDo> list(Integer clubId) throws SportException;

    List<EventDo> listNow(Integer clubId) throws SportException;

    List<EventDo> listMyPublish(Integer clubId, String telephone) throws SportException;

    List<EventDo> listMyJoin(Integer clubId, String telephone) throws SportException;

    List<DoneEventVo> listMyDone(Integer clubId, String telephone) throws SportException;

    void join(String telephone, Integer eventId) throws SportException;

    String getOtp(String telephone, Integer eventId) throws SportException;

    void check(String telephone, Integer clubId, String otpCode) throws SportException;

    void rate(String telephone, Integer eventId, Integer content) throws SportException;

    EventDo look(Integer eventId) throws SportException;

    void cancelJoin(Integer eventId, String telephone) throws SportException;

    List<User> listAttend(Integer eventId) throws SportException;

    List<User> listUnAttend(Integer eventId) throws SportException;

}
