package xyz.leo.lego.assemble.platform.common.copy;

/**
 * @author xuyangze
 * @date 2019/12/5 3:22 PM
 */
public interface BeanCopierConvert<S, T> {
    /**
     * 来源class
     * @return
     */
    Class<? extends S> sourceClass();

    /**
     * 目标class
     * @return
     */
    Class<? extends T> targetClass();

    /**
     * 转化器
     * @param value
     * @param setterMethod
     * @return
     */
    Object covert(Object value, String setterMethod);
}
