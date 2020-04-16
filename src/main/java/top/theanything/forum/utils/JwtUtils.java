package top.theanything.forum.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    /**
     * 签发token
     * @param userName 用户名
     * @return token
     */
    public static String create(String userName) {
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        return token;
    }

    /**
     * 解析token
     * @param token token
     * @return 用户名
     */
    public static String parse(String token) {
        String username = null;
        try {
            username = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw  e;
        }
        return username;
    }


    public static void main(String[] args) {
         String name = "zhou";
        String s = JwtUtils.create(name);
        System.out.println(s);
        String parse = JwtUtils.parse(s);
        System.out.println(parse);
    }
}
