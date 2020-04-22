package top.theanything.forum.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import top.theanything.forum.error.BusinessException;
import top.theanything.forum.error.EmBusinessException;
import top.theanything.forum.pojo.User;
import top.theanything.forum.response.CommonReturnType;
import top.theanything.forum.service.UserService;
import top.theanything.forum.utils.JwtUtils;
import top.theanything.forum.utils.QQConnectionUtil;

import java.io.IOException;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description
 * @createTime 2020年04月13日 17:45:00
 */


@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login_base")
    @ResponseBody
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

    @RequestMapping("/login")
    public String qqLogin(@RequestParam("code")String code , @RequestParam("state") String state, ModelMap model) throws IOException {
        System.out.println("返回的Authorization code="+code);
        System.out.println("返回的state"+state);
//        第三步 获取access token
        String accessToken = QQConnectionUtil.getAccessToken(code);
        System.out.println("返回的accessToken="+accessToken);
//        第四步 获取登陆后返回的 openid、appid 以JSON对象形式返回
        JSONObject userInfo = QQConnectionUtil.getUserOpenID(accessToken);
//        第五步获取用户有效(昵称、头像等）信息  以JSON对象形式返回
        String client_id = userInfo.getString("client_id");
        String openid = userInfo.getString("openid");
        System.out.println("openid:"+openid);
        String token = generatorToken(openid);
        //获取用户头像 什么什么之类的qq信息
//        JSONObject userRealInfo = QQConnectionUtil.getUserInfo(accessToken,client_id,openid);
        System.out.println("加密后的为："+token);
        model.addAttribute("token",token);
        return "redirect:vote";
    }


    @RequestMapping("/")
    public String main(){
        return "login";
    }

    private String generatorToken(String phone){
        return JwtUtils.create(phone);
    }



}
