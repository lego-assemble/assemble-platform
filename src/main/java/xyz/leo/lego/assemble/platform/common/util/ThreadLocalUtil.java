package xyz.leo.lego.assemble.platform.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2018/9/7 下午4:43
 */
public class ThreadLocalUtil {
    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }

    };

    @SuppressWarnings("unchecked")
    public static <T> T get(final String key) {
        Map<String, Object> map = threadLocal.get();
        return (T) map.get(key);
    }

    public static Map<String, Object> get() {
        return threadLocal.get();
    }

    public static Map<String, Object> removeThreadLocalMap() {
        Map<String, Object> map = threadLocal.get();
        threadLocal.remove();
        return map;
    }

    public static void set(final String key, final Object value) {
        Map<String, Object> map = threadLocal.get();
        map.put(key, value);
    }

    public static void set(final Map<String, Object> keyValueMap) {
        Map<String, Object> map = threadLocal.get();
        map.putAll(keyValueMap);
    }

    public static void remove() {
        threadLocal.get().clear();
    }

    @SuppressWarnings("unchecked")
    public static <T> T remove(final String key) {
        Map<String, Object> map = threadLocal.get();
        return (T) map.remove(key);
    }
}
