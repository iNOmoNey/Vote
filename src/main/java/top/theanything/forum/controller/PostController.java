package top.theanything.forum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.theanything.forum.annotations.TokenValidation;
import top.theanything.forum.error.BusinessException;
import top.theanything.forum.error.EmBusinessException;
import top.theanything.forum.pojo.Article;
import top.theanything.forum.response.CommonReturnType;
import top.theanything.forum.utils.JwtUtils;
import top.theanything.forum.utils.SpringContextUtil;

import javax.annotation.PostConstruct;
import javax.swing.*;
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
@Slf4j
@Controller
@RequestMapping("/post")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class PostController {

    private static final String ARTICLE_PREFIX = "article:";
    //每天文章增加200票才会被认为是有趣的。
    private static final int INCR_SCORE = 432;
    private  AtomicInteger id;
    @Autowired
    private JedisPool jedisPool;

//    @Autowired
//    private SpringContextUtil contextUtil;

    @PostConstruct
    public void init(){
       id = new AtomicInteger(1);
    }

    @GetMapping("/getAllbyTime")
    @TokenValidation()
    @ResponseBody
    public CommonReturnType getAllByTime(){
        String token = SpringContextUtil.getRequest().getParameter("token");
        String userId = JwtUtils.parse(token);
        HashMap<String, Article> article_list = new HashMap<>();
        Jedis jedis = null;
       try {
           jedis = jedisPool.getResource();
           jedis.select(10);
           Set<String> articles = jedis.zrange("time", 0, -1);
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
       }catch (Exception e){
           log.warn("出错啦{}",e.getMessage());
           throw  e;
       }finally {
           jedis.close();
       }
        return CommonReturnType.create(article_list);
    }

    //发表文章
    @PostMapping("/public")
    @TokenValidation()
    @ResponseBody
    public CommonReturnType publish(Article article){

        //todo  增加分类
        Jedis jedis = null;
       try {
            jedis = jedisPool.getResource();
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
           log.warn("出错啦{}",e.getMessage());
           throw  e;
       }finally {
           jedis.close();
       }
       return CommonReturnType.create(null);
    }


    @PostMapping("incr")
    @TokenValidation
    @ResponseBody
    public CommonReturnType up(@RequestParam("article") String articleId) throws BusinessException {
        String id = articleId.split(":")[1];
        String userid = SpringContextUtil.getUserid();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.select(10);
            //在文章点赞的用户中添加该用户
            if (jedis.sadd("voted:" + id, "user:" + userid) == 0)
                return CommonReturnType.create("滚，点过就不要点了","fail");
            //如果添加成功
            // (1)给文章加分数(86400/200)
            // (2)在文章散列加上投票数
            jedis.zincrby("score",INCR_SCORE,articleId);
            jedis.hincrBy(articleId,"votes",1);
        }catch (Exception e){
            throw  new BusinessException(EmBusinessException.REDIS_BUSY);
        }finally {
            jedis.close();
        }
       return getAllByTime();

    }

    @GetMapping("/vote")
    public String vote(){

        return "vote";
    }
    @GetMapping("/vote2")
    public String vote2(ModelMap modelMap){
        modelMap.addAttribute("token","123123123");
        return "test";

    }
    // todo 踩
}
