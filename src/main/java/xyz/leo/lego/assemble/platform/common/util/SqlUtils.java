package xyz.leo.lego.assemble.platform.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xuyangze
 * @date 2019/5/13 11:18 AM
 */
public class SqlUtils {
    private static final String CONCAT = "%";

    /**
     * 模糊搜索中关键字转义通配符
     *
     * @param keyword 关键字
     * @return 转义后的字符串
     */
    public static String escapeSql(String keyword) {
        if(StringUtils.isBlank(keyword)) {
            return keyword;
        }
        keyword = keyword.replace("\\","\\\\");
        keyword = keyword.replace("%", "\\%");
        keyword = keyword.replace("_", "\\_");
        return keyword;
    }

    /**
     * 右模糊匹配
     * @param target
     * @return
     */
    public static String rightConcat(String target) {
        return StringUtils.join(escapeSql(target), CONCAT);
    }

    /**
     * 左模糊匹配
     * @param target
     * @return
     */
    public static String leftConcat(String target) {
        return StringUtils.join(CONCAT, escapeSql(target));
    }

    /**
     * 左右模糊匹配
     * @param target
     * @return
     */
    public static String bothConcat(String target) {
        return StringUtils.join(CONCAT, escapeSql(target), CONCAT);
    }
}
