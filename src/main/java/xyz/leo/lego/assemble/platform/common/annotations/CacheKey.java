package xyz.leo.lego.assemble.platform.common.annotations;

import java.lang.annotation.*;

/**
 * @author xuyangze
 * @date 2018/9/6 下午2:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CacheKey {
    String value() default "";
}