package xyz.leo.lego.assemble.platform.common.sf;

import java.util.function.Function;

/**
 * @author xuyangze
 * @date 2019/12/5 4:09 PM
 */
public interface SzFunction<T, R> extends Function<T, R>, SerializableFunction {
}