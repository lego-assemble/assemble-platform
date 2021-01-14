package xyz.leo.lego.assemble.platform.common.annotations;

import xyz.leo.lego.assemble.platform.common.enums.CacheLevel;

import java.lang.annotation.*;

/**
 * 构建缓存
 * 配合@CacheKey使用，@CacheKey会将请求参数当作缓存key
 * @author xuyangze
 * @date 2018/9/6 下午2:25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    /**
     * 缓存的key
     * @return
     */
    String value() default "";

    /**
     * 失效时间秒，默认1分钟，收到缓存组建限制，本地缓存时间最多10分钟
     * @return
     */
    int timeout() default 60;

    /**
     * 存放到远端的缓存失效时间，默认3分钟，单位秒
     * @return
     */
    int remoteTimeout() default 180;

    /**
     * 缓存级别
     * @return
     */
    CacheLevel level() default CacheLevel.LOCAL;
}
