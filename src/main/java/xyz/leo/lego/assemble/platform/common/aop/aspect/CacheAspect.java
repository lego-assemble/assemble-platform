package xyz.leo.lego.assemble.platform.common.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.leo.lego.assemble.platform.common.annotations.Cache;
import xyz.leo.lego.assemble.platform.common.aop.process.AspectProcessor;

/**
 * @author xuyangze
 * @date 2018/9/6 下午2:32
 */
@Aspect
@Component
@Order(50)
public class CacheAspect {
    @Autowired
    @Qualifier("cacheAspectProcessor")
    private AspectProcessor<Cache> cacheAspectProcessor;

    @Around("@annotation(cache)")
    public Object around(ProceedingJoinPoint pjp, Cache cache) throws Throwable {
        return cacheAspectProcessor.process(pjp, cache);
    }
}
