package top.theanything.forum.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
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

    private static final int MAX_IDLE = 10;
    private static final long MAX_WAITMILLIS = 1000;
    private static final int MAX_TOTAL = 10;
    private static final int MIN_IDLE = 2;


    private String HOST;
    private int PORT;
    private int SELECT;


    @Bean
    public JedisPool initJedis(){
         GenericObjectPoolConfig config = new GenericObjectPoolConfig();
         config.setMaxIdle(MAX_IDLE);
         config.setMaxWaitMillis(MAX_WAITMILLIS);
         config.setMaxTotal(MAX_TOTAL);
         config.setMinIdle(MIN_IDLE);



        JedisPool jedisPool= new JedisPool(config,HOST,PORT);

        return jedisPool;
    }

}
