package xyz.leo.lego.assemble.platform.common.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import xyz.leo.lego.assemble.platform.common.enums.CacheLevel;
import xyz.leo.lego.assemble.platform.common.service.CacheService;

/**
 * @author xuyangze
 * @date 2018/12/27 2:36 PM
 */
@Component
public class CacheComponent {
    @Autowired
    @Qualifier("localCacheService")
    private CacheService localCacheService;

    @Autowired
    @Qualifier("remoteCacheService")
    private CacheService remoteCacheService;


    public boolean del(String key, CacheLevel cacheLevel) {
        switch (cacheLevel) {
            case LOCAL: {
                return localCacheService.del(key);
            }
            case REMOTE: {
                return remoteCacheService.del(key);
            }
            case LOCAL_REMOTE:
            default: {
                return localCacheService.del(key) && remoteCacheService.del(key);
            }
        }
    }

    public boolean setLocal(String key, Object data, int timeout) {
        return set(key, data, timeout, 0, CacheLevel.LOCAL);
    }

    public boolean setRemote(String key, Object data, int timeout) {
        return set(key, data, 0, timeout, CacheLevel.REMOTE);
    }

    public boolean setLocalRemote(String key, Object data, int timeout, int remoteTimeout) {
        return set(key, data, timeout, remoteTimeout, CacheLevel.LOCAL_REMOTE);
    }

    private boolean set(String key, Object data, int timeout, int remoteTimeout, CacheLevel cacheLevel) {
        switch (cacheLevel) {
            case LOCAL: {
                return localCacheService.set(key, data, timeout);
            }
            case REMOTE: {
                return remoteCacheService.set(key, data, remoteTimeout);
            }
            case LOCAL_REMOTE:
            default: {
                return localCacheService.set(key, data, timeout) && remoteCacheService.set(key, data, remoteTimeout);
            }
        }
    }

    public <T> T get(String key, CacheLevel cacheLevel) {
        return get(key, cacheLevel, 0);
    }

    public <T> T get(String key, CacheLevel cacheLevel, int equalizeTime) {
        switch (cacheLevel) {
            case LOCAL: {
                return localCacheService.get(key);
            }
            case REMOTE: {
                return remoteCacheService.get(key);
            }
            case LOCAL_REMOTE:
            default: {
                T data = localCacheService.get(key);
                if (null != data) {
                    return data;
                }

                data = remoteCacheService.get(key);
                if (null != data && equalizeTime > 0) {
                    // 补偿本地
                    localCacheService.set(key, data, equalizeTime);
                }

                return data;
            }
        }
    }
}
