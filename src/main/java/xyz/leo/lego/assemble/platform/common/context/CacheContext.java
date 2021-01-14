package xyz.leo.lego.assemble.platform.common.context;

import xyz.leo.lego.assemble.platform.common.util.ThreadLocalUtil;

/**
 * @author xuyangze
 * @date 2018/9/7 下午4:41
 */
public class CacheContext {

    private static final String CLEAN_CACHE_KEY = "cache_clean_key";
    private static final String CLEAN_SKIP_KEY = "cache_skip_key";
    private static final String CACHE_READ_ONLY = "cache_read_only_key";

    public static void setCleanCache(boolean cleanCache) {
        ThreadLocalUtil.set(CLEAN_CACHE_KEY, cleanCache);
    }

    public static void setSkipCache(boolean skipCache) {
        ThreadLocalUtil.set(CLEAN_SKIP_KEY, skipCache);
    }

    public static void setReadOnly(boolean readCacheOnly) {
        ThreadLocalUtil.set(CACHE_READ_ONLY, readCacheOnly);
    }

    public static boolean cleanCache() {
        Boolean clean = ThreadLocalUtil.remove(CLEAN_CACHE_KEY);
        if (null == clean) {
            return false;
        }

        return clean;
    }

    public static boolean skipCache() {
        Boolean skip = ThreadLocalUtil.remove(CLEAN_SKIP_KEY);
        if (null == skip) {
            return false;
        }

        return skip;
    }

    public static boolean readOnly() {
        Boolean readOnly = ThreadLocalUtil.remove(CACHE_READ_ONLY);
        if (null == readOnly) {
            return false;
        }

        return readOnly;
    }
}
