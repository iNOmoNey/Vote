package top.theanything.forum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.theanything.forum.error.BusinessException;
import top.theanything.forum.error.EmBusinessException;
import top.theanything.forum.pojo.User;
import top.theanything.forum.response.CommonReturnType;
import top.theanything.forum.service.UserService;
import top.theanything.forum.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public CommonReturnType login(@Validated User user) throws BusinessException {
        Integer integer = userService.userLogin(user.getPhone(), user.getPassword());
        if (integer == null) {
            throw new BusinessException(EmBusinessException.USER_LOGIN_FAIL);
        }
       /*(1) 使用redis
       String token = UUID.randomUUID().toString();
       token = token.replace("-","");
       try {
            Jedis jedis = jedisPool.getResource();
            //将用户登录token存入redis
            jedis.set(token,String.valueOf(integer));
        }catch (Exception e){
            log.warn("错误信息{}",e.getMessage());
        }*/

//        (2)使用jwt
        String token = generatorToken(user.getPhone());
        return CommonReturnType.create(token);
    }

    private String generatorToken(String phone){
        return JwtUtils.create(phone);
    }

}
