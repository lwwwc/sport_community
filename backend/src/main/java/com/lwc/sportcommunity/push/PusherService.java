package com.lwc.sportcommunity.push;


import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.StringTokenizer;

/**
 * Create by LWC on 2023/5/3 11:12
 */
@Service
public class PusherService {

    private Pusher pusher;

    public PusherService(){
        pusher = new Pusher("1593866", "c958d27ec866b98de0b2", "950f26a3383dddaa8dc4");
        pusher.setCluster("us2");
    }
    public void sendReminder(Integer userId, String content){
        pusher.trigger("private-"+userId, "reminder", content);
    }
    public Pusher getPusher(){
        return this.pusher;
    }
}
