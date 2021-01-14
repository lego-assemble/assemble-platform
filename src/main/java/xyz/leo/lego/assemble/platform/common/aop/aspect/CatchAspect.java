package xyz.leo.lego.assemble.platform.common.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.leo.lego.assemble.platform.common.annotations.Catch;
import xyz.leo.lego.assemble.platform.common.aop.process.AspectProcessor;

/**
 * @author xuyangze
 * @date 2018/9/6 下午3:14
 */
@Aspect
@Component
@Order(11)
public class CatchAspect {

    @Autowired
    @Qualifier("catchAspectProcessor")
    private AspectProcessor<Catch> catchAspectProcessor;

    @Around("@annotation(aCatch)")
    public Object around(ProceedingJoinPoint pjp, Catch aCatch) throws Throwable {
        return catchAspectProcessor.process(pjp, aCatch);
    }
}
