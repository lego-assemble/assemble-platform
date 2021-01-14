package xyz.leo.lego.assemble.platform.common.annotations;

import java.lang.annotation.*;

/**
 * 切面会将基本类型装箱，如果返回的是null，则拆箱失败
 * 给null的值添加默认值
 * @author xuyangze
 * @date 2018/9/6 下午9:59
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface UnBox {
    /**
     * boolean为空的默认值
     * @return
     */
    boolean booleanValue() default false;

    /**
     * int为空的默认值
     * @return
     */
    int intValue() default 0;

    /**
     * char为空的默认值
     * @return
     */
    char charValue() default '0';

    /**
     * byte为空的默认值
     * @return
     */
    byte byteValue() default 0;

    /**
     * double为空的默认值
     * @return
     */
    double doubleValue() default 0;

    /**
     * float为空的默认值
     * @return
     */
    float floatValue() default 0;

    /**
     * long为空的默认值
     * @return
     */
    long longValue() default 0;

    /**
     * short为空的默认值
     * @return
     */
    short shortValue() default 0;
}
