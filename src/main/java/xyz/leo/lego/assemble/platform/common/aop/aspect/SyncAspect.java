package xyz.leo.lego.assemble.platform.common.aop.aspect;

import xyz.leo.lego.assemble.platform.common.annotations.Sync;
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
@Order(500)
public class SyncAspect {

    @Autowired
    @Qualifier("syncAspectProcessor")
    private AspectProcessor<Sync> syncAspectProcessor;

    @Around("@annotation(sync)")
    public Object around(ProceedingJoinPoint pjp, Sync sync) throws Throwable {
        return syncAspectProcessor.process(pjp, sync);
    }
}
