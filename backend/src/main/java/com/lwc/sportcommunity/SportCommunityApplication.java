package com.lwc.sportcommunity;

import com.lwc.sportcommunity.dao.UserMapper;
import com.lwc.sportcommunity.dataobject.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by LWC on 2023/3/17 15:58
 */
@SpringBootApplication(scanBasePackages = {"com.lwc.sportcommunity"})
@MapperScan("com.lwc.sportcommunity.dao")
@RestController
@EnableScheduling
public class SportCommunityApplication {

//    @Autowired
//    private UserMapper userMapper;
//
//    @RequestMapping("/index")
//    public String home(){
//        User userDo = userMapper.selectByPrimaryKey(1);
//        if (null == userDo){
//            return "用户对象不存在";
//        }else {
//            return userDo.getName();
//        }
////        return "hello";
//    }

    public static void main(String[] args) {
//        System.out.println("hello");
        SpringApplication.run(SportCommunityApplication.class, args);
    }
}
