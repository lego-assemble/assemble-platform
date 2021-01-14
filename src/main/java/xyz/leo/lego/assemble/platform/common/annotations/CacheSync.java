package xyz.leo.lego.assemble.platform.common.annotations;

import java.lang.annotation.*;

/**
 * 构建缓存，没有命中则直接请求真实接口，真实接口调用会加锁，加锁后再次去读取缓存，命中则返回，否则调用真实接口
 *
 * @author xuyangze
 * @date 2018/9/6 下午2:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheSync {
    Cache cache() default @Cache;
    Sync sync() default @Sync;
}
