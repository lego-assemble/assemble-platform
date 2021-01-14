package xyz.leo.lego.assemble.platform.common.context;

import xyz.leo.lego.assemble.platform.common.util.ThreadLocalUtil;

/**
 * @author xuyangze
 * @date 2018/9/7 下午4:41
 */
public class SyncContext {

    private static final String SYNC_SKIP_KEY = "sync_skip_key";

    public static void skipSync() {
        ThreadLocalUtil.set(SYNC_SKIP_KEY, true);
    }

    public static boolean isSkipSync() {
        Boolean skip = ThreadLocalUtil.remove(SYNC_SKIP_KEY);
        if (null == skip) {
            return false;
        }

        return skip;
    }
}
