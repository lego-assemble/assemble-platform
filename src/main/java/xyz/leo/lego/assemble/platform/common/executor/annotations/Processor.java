package xyz.leo.lego.assemble.platform.common.executor.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author xuyangze
 * @date 2019/11/4 7:29 PM
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Processor {
    /**
     * 名称
     * @return
     */
    String value();

    /**
     * 顺序，越小优先级越高
     * @return
     */
    int order() default 0;
}
