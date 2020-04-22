package top.theanything.forum.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import top.theanything.forum.annotations.TokenValidation;
import top.theanything.forum.error.BusinessException;
import top.theanything.forum.error.EmBusinessException;
import top.theanything.forum.response.CommonReturnType;
import top.theanything.forum.utils.JwtUtils;
import top.theanything.forum.utils.SpringContextUtil;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationEvent;
import java.lang.annotation.Annotation;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName TokenValidationHandler.java
 * @Description
 * @createTime 2020年04月15日 23:33:00
 */
@Aspect
@Component
@Slf4j
public class TokenValidationHandler {





    @Pointcut("execution(* top.theanything.forum.controller.*.*(..))")
    public void validation(){
    }

    @Around("validation()")
    public Object hander(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        TokenValidation annotation = signature.getMethod().getAnnotation(TokenValidation.class);
        if (null != annotation && !annotation.value() || null == annotation){
            return pjp.proceed();
        }
        HttpServletRequest request = SpringContextUtil.getRequest();

        String userId;
        try {
             String token = request.getParameterMap().get("token")[0];
             userId = JwtUtils.parse(token);
        }catch (Exception e){
            log.warn("有个人没登录在乱搞，他的ip{}",SpringContextUtil.getRequest().getRemoteHost());
            return CommonReturnType.create("请登录","fail");
        }
        SpringContextUtil.setUserid(userId);
        return pjp.proceed();
    }

}
