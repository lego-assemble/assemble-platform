package xyz.leo.lego.assemble.platform.common.annotations;

import xyz.leo.lego.assemble.platform.common.enums.LockType;

import java.lang.annotation.*;

/**
 * 同一个key将会同步执行
 * 配合@SyncKey使用
 * @author xuyangze
 * @date 2018/9/6 下午3:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sync {
    /**
     * 锁名称
     * @return
     */
    String value() default "";

    /**
     * 锁的过期时间
     * @return
     */
    int expire() default 80;

    /**
     * 重试次数
     * @return
     */
    int tryTime() default 2;

    /**
     * 尝试获取的时间间隔
     * @return
     */
    int tryWait() default 20;

    /**
     * 锁的类别
     * @return
     */
    LockType type() default LockType.LOCAL;
}
