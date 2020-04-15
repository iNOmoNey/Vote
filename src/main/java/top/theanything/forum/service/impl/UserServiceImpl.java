package top.theanything.forum.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.theanything.forum.dao.UserDOMapper;
import top.theanything.forum.pojo.User;
import top.theanything.forum.service.UserService;

import java.util.Date;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName UserServiceImpl.java
 * @Description
 * @createTime 2020年04月13日 17:46:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;


    @Override
    public Integer userLogin(String phone, String password) {
        User user = User.builder()
                .password(password)
                .phone(phone)
                .build();
        User login = userDOMapper.login(user);
        return login.getId();

    }

}
