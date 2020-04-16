package top.theanything.forum.annotations;

import java.lang.annotation.*;

/**
 * @author zhou
 * @version 1.0.0
 * @ClassName TokenValidation.java
 * @Description
 *
 * 在方法上如果有该注解，必须登录才能访问
 * 配合 TokenValidationHandler 使用
 *
 * @createTime 2020年04月15日 23:34:00
 */



@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TokenValidation {

    boolean value() default true;
}
