package com.lwc.sportcommunity.push;

import com.pusher.rest.Pusher;
import com.pusher.rest.data.PresenceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

/**
 * Create by LWC on 2023/5/3 13:12
 */
@Controller
@RequestMapping("/pusher/auth")
public class PusherController {

    @Autowired
    private PusherService pusherService;

//    private Pusher pusher = pusherService.getPusher();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> authenticate(HttpServletRequest request) {
        // 获取请求参数
        String socketId = request.getParameter("socket_id");
        String channelName = request.getParameter("channel_name");

        // 生成 Pusher 访问令牌
        String auth = pusherService.getPusher().authenticate(socketId, channelName);

        // 返回访问令牌
        return ResponseEntity.ok(auth);
    }

}
