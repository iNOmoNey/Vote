package top.theanything.forum.utils;

import javafx.application.Application;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName SpringContextUtil.java
 * @Description
 * @createTime 2020年04月16日 16:22:00
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    private static final ThreadLocal<String> USERID = new ThreadLocal<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.context = applicationContext;
    }

    public HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    public void setUserid(String id){
        SpringContextUtil.USERID.set(id);
    }

    public String getUserid(){
        return SpringContextUtil.USERID.get();
    }

    public ApplicationContext getContext(){
        return context;
    }

    public Object getBeanByName(String name) {
        return context.getBean(name);
    }

    public <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }
}
