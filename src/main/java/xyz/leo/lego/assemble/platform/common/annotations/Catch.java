package xyz.leo.lego.assemble.platform.common.annotations;

import xyz.leo.lego.assemble.platform.common.enums.LogLevel;

import java.lang.annotation.*;

/**
 * 捕获异常，并且打印日志
 * @author xuyangze
 * @date 2018/9/7 下午3:24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Catch {
    /**
     * 捕获到异常打印的日志信息
     * @return
     */
    LogLevel type() default LogLevel.ERROR;

    /**
     * 是否抛出异常
     * @return
     */
    boolean throwable() default true;
}
