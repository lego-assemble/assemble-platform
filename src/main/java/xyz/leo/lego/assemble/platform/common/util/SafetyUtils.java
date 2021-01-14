package xyz.leo.lego.assemble.platform.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2018/9/10 下午2:37
 */
@Slf4j
public class SafetyUtils {
    public static <T> T firstObject(Collection<T> aCollection) {
        if (CollectionUtils.isEmpty(aCollection)) {
            return null;
        }

        return aCollection.iterator().next();
    }

    public static String toString(Object value) {
        if (null == value) {
            return StringUtils.EMPTY;
        }

        return null == value ? StringUtils.EMPTY : value.toString();
    }

    public static Long toLong(Object value) {
        if (null == value) {
            return 0L;
        }

        if (value instanceof Long) {
            return (Long)value;
        }

        String strValue = toString(value);
        if (StringUtils.isEmpty(strValue)) {
            return 0L;
        }

        try {
            return Long.parseLong(strValue);
        } catch (Exception e) {
            log.error("SafetyUtils toLong error", e);
        }

        return 0L;
    }

    public static Map<String, Object> toMap(Object value) {
        if (null == value) {
            return null;
        }

        String json = String.valueOf(value);
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        if (json.startsWith("[") || json.startsWith("{")) {
            return JSON.parseObject(json);
        }

        return null;
    }

    public static String toJSON(Object value) {
        if (null == value) {
            return null;
        }

        return JSON.toJSONString(value);
    }
}
