package xyz.leo.lego.assemble.platform.common.aop.aspect;

import xyz.leo.lego.assemble.platform.common.annotations.UnBox;
import xyz.leo.lego.assemble.platform.common.aop.process.AspectProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author xuyangze
 * @date 2018/9/6 下午3:14
 */
@Aspect
@Component
@Order(10)
public class UnBoxAspect {
    @Autowired
    @Qualifier("unBoxAspectProcessor")
    private AspectProcessor<UnBox> unBoxAspectProcessor;

    @Around("@annotation(unBox)")
    public Object around(ProceedingJoinPoint pjp, UnBox unBox) throws Throwable {
        return unBoxAspectProcessor.process(pjp, unBox);
    }
}
