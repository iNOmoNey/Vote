package top;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.theanything.forum.App;
import top.theanything.forum.dao.UserDOMapper;
import top.theanything.forum.utils.JwtUtils;

import java.util.Date;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName InsertTest.java
 * @Description
 * @createTime 2020年04月13日 22:49:00
 */

public class InsertTest {

    public static void main(String[] args) {
//
        String parse = JwtUtils.parse("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTEiLCJleHAiOjE1ODczODUwODV9.beyWZdsHpFunB3q2MbIKHxmVeFTmJLSLDVRjqflslaVPaqchyGiatJ3SCL58dYrOLzgmDvHjLE_l9ptczBzFDw");
        System.out.println(parse);
    }



}
