package xyz.leo.lego.assemble.platform.common.annotations;

import java.lang.annotation.*;

/**
 * 降级模块，优先使用接口，接口失败后使用缓存信息
 * @author xuyangze
 * @date 2018/9/7 下午3:59
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Degrade {
    Cache cache() default @Cache;
    Catch error() default @Catch(throwable = false);
}
