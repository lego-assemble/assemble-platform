package xyz.leo.lego.assemble.platform.common.aop.process;

import xyz.leo.lego.assemble.platform.common.annotations.Cache;
import xyz.leo.lego.assemble.platform.common.annotations.CacheKey;
import xyz.leo.lego.assemble.platform.common.component.CacheComponent;
import xyz.leo.lego.assemble.platform.common.context.CacheContext;
import xyz.leo.lego.assemble.platform.common.enums.CacheLevel;
import xyz.leo.lego.assemble.platform.common.util.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuyangze
 * @date 2018/9/10 下午8:26
 */
@Component("cacheAspectProcessor")
public class CacheAspectProcessor implements AspectProcessor<Cache> {

    @Autowired
    private CacheComponent cacheComponent;

    private AspectUtils.Resolver resolver = (annotation) -> {
        if (annotation instanceof CacheKey) {
            return ((CacheKey) annotation).value();
        }

        return null;
    };

    @Override
    public Object process(ProceedingJoinPoint pjp, AspectProcessorInvoker aspectProcessorInvoker, Cache cache) throws Throwable {
        String cacheKey = resolveCacheKey(pjp, cache);
        if (CacheContext.cleanCache()) {
            del(cacheKey, cache.level());
            return null;
        }

        Object data = null;
        if (!CacheContext.skipCache()) {
            data = get(cacheKey, cache.level(), cache.timeout());
        }

        if (CacheContext.readOnly()) {
            return data;
        }

        if (null == data) {
            data = aspectProcessorInvoker.invoke(pjp);
            if (null != data) {
                set(cacheKey, data, cache.timeout(), cache.remoteTimeout(), cache.level());
            }
        }

        return data;
    }

    private void del(String key, CacheLevel cacheLevel) {
        cacheComponent.del(key, cacheLevel);
    }

    private void set(String key, Object data, int timeout, int remoteTimeout, CacheLevel cacheLevel) {
        switch (cacheLevel) {
            case LOCAL: {
                cacheComponent.setLocal(key, data, timeout);
                break;
            }
            case REMOTE: {
                cacheComponent.setRemote(key, data, remoteTimeout);
                break;
            }
            case LOCAL_REMOTE:
            default: {
                cacheComponent.setLocalRemote(key, data, timeout, remoteTimeout);
                break;
            }
        }
    }

    private Object get(String key, CacheLevel cacheLevel, int localTimeout) {
        return cacheComponent.get(key, cacheLevel, localTimeout);
    }

    private String resolveCacheKey(ProceedingJoinPoint pjp, Cache cache) {
        return AspectUtils.resolve(pjp, cache.value(), resolver);
    }
}
