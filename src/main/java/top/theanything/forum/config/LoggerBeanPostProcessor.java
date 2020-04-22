package top.theanything.forum.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName LoggerBeanPostProcessor.java
 * @Description
 *
 * 试一下BeanPostProcessor
 *
 * @createTime 2020年04月19日 10:06:00
 */
@Component
public class LoggerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化之前"+beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("初始化之后"+beanName);
        return bean;
    }
}
