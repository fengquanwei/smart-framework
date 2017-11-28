package com.kilogate.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @author fengquanwei
 * @create 2017/11/22 17:23
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
