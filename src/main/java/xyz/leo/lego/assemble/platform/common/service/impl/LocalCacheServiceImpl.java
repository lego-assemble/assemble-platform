package xyz.leo.lego.assemble.platform.common.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import xyz.leo.lego.assemble.platform.common.service.CacheService;
import xyz.leo.lego.assemble.platform.common.util.Stringify;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service(value = "localCacheService")
public class LocalCacheServiceImpl implements CacheService {
    class CacheObject{
        private int expire;
        private String value;
        private int startTime;
    }

    LoadingCache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build(
            new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    return new CacheObject();
                }
            }
    );

    @Override
    public <T> boolean set(String key, T value, int second) {
        return setInner(key, value, second);
    }

    @Override
    public boolean del(String key) {
        cache.invalidate(key);
        return true;
    }

    @Override
    public <T> T get(String key) {
        if(key == null) {
            return null;
        }

        CacheObject cacheObject = getCacheObject(key);
        if(null != cacheObject) {
            return Stringify.toObject(cacheObject.value);
        }

        return null;
    }

    private  <T> boolean setInner(String key, T value, int expire) {
        if(key == null || value == null) {
            return false;
        }

        CacheObject cacheObject = new CacheObject();
        cacheObject.value = Stringify.toString(value);
        cacheObject.expire = expire;
        cacheObject.startTime = (int)(System.currentTimeMillis() / 1000);

        cache.put(key, cacheObject);

        return true;
    }

    private CacheObject getCacheObject(String key) {
        try {
            Object object = cache.get(key);
            if(object == null) {
                cache.invalidate(key);
                return null;
            }

            CacheObject cacheObject = (CacheObject)object;
            if((cacheObject.startTime + cacheObject.expire) < (System.currentTimeMillis() / 1000)) {
                cache.invalidate(key);
                return null;
            }

            return cacheObject;
        } catch (Exception e) {
            log.error("getCacheObject error", e);
        }

        return null;
    }
}
