package top.theanything.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import top.theanything.forum.pojo.Article;
import top.theanything.forum.response.CommonReturnType;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName PostController.java
 * @Description
 * @createTime 2020年04月15日 20:37:00
 */
@RestController
@RequestMapping("/post")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class PostController {

    private static final String ARTICLE_PREFIX = "article:";


    @Autowired
    private Jedis jedis;

    private  AtomicInteger id;

    @PostConstruct
    public void init(){
       id = new AtomicInteger(1);
    }

    @GetMapping("/getAllbyTime")
    public CommonReturnType getAllByTime(){
        Set<String> articles = jedis.zrange("time", 0, -1);
        HashMap<String, Article> article_list = new HashMap<>();

        for (String id : articles) {
            Article article = Article.builder()
                    .title(jedis.hget( id, "title"))
                    .poster(jedis.hget( id, "poster"))
                    .link(jedis.hget(id, "link"))
                    .time(jedis.hget( id, "time"))
                    .votes(jedis.hget( id, "votes"))
                    .build();
            article_list.put( id,article);
        }
        return CommonReturnType.create(article_list);
    }


    @PostMapping("/public")
    public CommonReturnType publish(Article article){
       try {
           //article表
           long time = System.currentTimeMillis()/1000;   //获取当前秒数
           String article_name = ARTICLE_PREFIX+id.getAndAdd(1);
           HashMap<String, String> article_map = new HashMap<>();
           article_map.put("title",article.getTitle());
           article_map.put("poster", String.valueOf(article.getPoster()));
           article_map.put("link",article.getLink());
           article_map.put("time",String.valueOf(time));
           article_map.put("votes","0");
           jedis.hmset(article_name,article_map);
           // 给该文章投票的用户

           //在时间排序的表添加该表
           jedis.zadd("time",Double.longBitsToDouble(time),article_name);
           //在分值排序的表添加该表
           jedis.zadd("score",Double.longBitsToDouble(time),article_name);
       }catch (Exception e){
           throw  e;
       }
       return CommonReturnType.create(null);

    }

}
