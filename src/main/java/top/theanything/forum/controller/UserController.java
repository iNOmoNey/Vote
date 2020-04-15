package top.theanything.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import top.theanything.forum.error.BusinessException;
import top.theanything.forum.error.EmBusinessException;
import top.theanything.forum.response.CommonReturnType;
import top.theanything.forum.service.UserService;

import java.util.UUID;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description
 * @createTime 2020年04月13日 17:45:00
 */

@RestController
@RequestMapping("/user")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Jedis jedis;



    @PostMapping("/login")
    public CommonReturnType login(@RequestParam String phone,@RequestParam String password) throws BusinessException {
        Integer integer = userService.userLogin(phone, password);
        if (integer == null) {
            throw new BusinessException(EmBusinessException.USER_LOGIN_FAIL);
        }
        String token = UUID.randomUUID().toString();
        token = token.replace("-","");
        //将用户登录token存入redis
        jedis.set(token,String.valueOf(integer));
        return CommonReturnType.create(token);
    }


    @RequestMapping("/test")
    public CommonReturnType test(){
        return CommonReturnType.create("meiyou");
    }

    @RequestMapping("/test2")
    public String test2(){
        return "eeee";
    }
}
