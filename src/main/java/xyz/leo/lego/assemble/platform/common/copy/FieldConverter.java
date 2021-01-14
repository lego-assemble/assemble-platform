package xyz.leo.lego.assemble.platform.common.copy;

/**
 * @author xuyangze
 * @date 2019/12/5 4:24 PM
 */
@FunctionalInterface
public interface FieldConverter {
    /**
     * 转化
     * @param value
     * @return
     */
    Object convert(Object value);
}
