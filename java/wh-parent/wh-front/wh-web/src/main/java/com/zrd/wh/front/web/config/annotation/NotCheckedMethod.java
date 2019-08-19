package com.zrd.wh.front.web.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在方法上添加该注释，执行拦截器时不进行拦截
 * @author kexin
 * @date 2018/11/09
 */
@Retention(RetentionPolicy.RUNTIME)//在运行时可以获取  
@Target({ElementType.METHOD })//作用到方法等  
public @interface NotCheckedMethod {
}
