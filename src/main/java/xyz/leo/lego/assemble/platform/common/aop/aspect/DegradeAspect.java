package xyz.leo.lego.assemble.platform.common.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.leo.lego.assemble.platform.common.annotations.Cache;
import xyz.leo.lego.assemble.platform.common.annotations.Catch;
import xyz.leo.lego.assemble.platform.common.annotations.Degrade;
import xyz.leo.lego.assemble.platform.common.aop.process.AspectProcessor;
import xyz.leo.lego.assemble.platform.common.context.CacheContext;

/**
 * @author xuyangze
 * @date 2018/9/6 下午3:14
 */
@Aspect
@Component
@Order(499)
@Slf4j
public class DegradeAspect {

    @Autowired
    @Qualifier("cacheAspectProcessor")
    private AspectProcessor<Cache> cacheAspectProcessor;

    @Autowired
    @Qualifier("catchAspectProcessor")
    private AspectProcessor<Catch> catchAspectProcessor;

    @Around("@annotation(degrade)")
    public Object around(ProceedingJoinPoint pjp, Degrade degrade) throws Throwable {
        Object data = catchAspectProcessor.process(pjp, degrade.error());

        if (null == data) {
            // 只读取缓存信息，不调用接口
            CacheContext.setReadOnly(true);
            return cacheAspectProcessor.process(pjp, degrade.cache());
        }

        return data;
    }
}
