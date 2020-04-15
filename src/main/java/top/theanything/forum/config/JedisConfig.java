package top.theanything.forum.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import sun.nio.ch.SelChImpl;

import javax.sound.sampled.Port;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName JedisConfig.java
 * @Description
 * @createTime 2020年04月15日 19:37:00
 */
@Configuration
@ConfigurationProperties(prefix = "top.jedis")
@Getter
@Setter
public class JedisConfig {


    private String host;
    private int port;
    private int select;



    @Bean
    public Jedis initJedis(){
        Jedis jedis = new Jedis(host, port);
        jedis.select(select);
        return jedis;
    }

}
