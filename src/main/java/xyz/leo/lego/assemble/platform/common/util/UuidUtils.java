package xyz.leo.lego.assemble.platform.common.util;

import java.util.UUID;

/**
 * @author xuyangze
 * @date 2019/5/13 4:28 PM
 */
public class UuidUtils {
    private static final String JOIN = "-";
    public static String generate() {
        String uuid = UUID.randomUUID().toString();

        return uuid.replaceAll(JOIN, "");
    }
}
