package xyz.leo.lego.assemble.platform.common.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import xyz.leo.lego.assemble.platform.common.annotations.Cache;
import xyz.leo.lego.assemble.platform.common.annotations.CacheSync;
import xyz.leo.lego.assemble.platform.common.annotations.Sync;
import xyz.leo.lego.assemble.platform.common.aop.process.AspectProcessor;
import xyz.leo.lego.assemble.platform.common.aop.process.AspectProcessorInvoker;
import xyz.leo.lego.assemble.platform.common.context.CacheContext;

/**
 * 当@Cache和@Sync两个共存的时候
 * 优先读取缓存
 * 缓存失效读取真实服务，真实服务使用锁机制，每次读取一次，进入后先读取缓存信息，缓存无效则调用接口，然后写入缓存
 * @author xuyangze
 * @date 2018/9/6 下午3:14
 */
@Aspect
@Component
@Order(501)
public class CacheSyncAspect {

    @Autowired
    @Qualifier("cacheAspectProcessor")
    private AspectProcessor<Cache> cacheAspectProcessor;

    @Autowired
    @Qualifier("syncAspectProcessor")
    private AspectProcessor<Sync> syncAspectProcessor;

    @Around("@annotation(cacheSync)")
    public Object around(ProceedingJoinPoint pjp, CacheSync cacheSync) throws Throwable {

        // 先读取缓存信息
        CacheContext.setReadOnly(true);
        Object result = cacheAspectProcessor.process(pjp, cacheSync.cache());
        if (null == result) {
            AspectProcessorInvoker aspectProcessorInvoker = new CacheSyncAspectProcessorInvoker(cacheAspectProcessor, cacheSync.cache());
            result = syncAspectProcessor.process(pjp, aspectProcessorInvoker, cacheSync.sync());
        }

        return result;
    }

    class CacheSyncAspectProcessorInvoker implements AspectProcessorInvoker {
        private AspectProcessor<Cache> cacheAspectProcessor;
        private Cache cache;

        public CacheSyncAspectProcessorInvoker(AspectProcessor<Cache> cacheAspectProcessor, Cache cache) {
            this.cacheAspectProcessor = cacheAspectProcessor;
            this.cache = cache;
        }

        @Override
        public Object invoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            return cacheAspectProcessor.process(proceedingJoinPoint, cache);
        }
    }
}
