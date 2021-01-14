package xyz.leo.lego.assemble.platform.service.copier.annotation;

import org.springframework.stereotype.Component;
import java.lang.annotation.*;

/**
 * @author xuyangze
 * @date 2019/12/5 8:43 PM
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface BeanConverter {
}
