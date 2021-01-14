package xyz.leo.lego.assemble.platform.common.executor.processor;

/**
 * @author xuyangze
 * @date 2019/11/4 7:22 PM
 */
public interface IProcessor<T> {
    /**
     *
     * @param data
     */
    void process(T data);
}
