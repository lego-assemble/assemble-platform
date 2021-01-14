package xyz.leo.lego.assemble.platform.common.sf;

import lombok.extern.slf4j.Slf4j;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @author xuyangze
 * @date 2019/12/5 4:36 PM
 */
@Slf4j
public class SerializableFunctionUtils {
    public static <R> String getMethodName(SzSupplier<R> szSupplier) {
        return innerGetMethodName(szSupplier);
    }

    public static <T> String getMethodName(SzConsumer<T> szConsumer) {
        return innerGetMethodName(szConsumer);
    }

    public static <T, R> String getMethodName(SzFunction<T, R> szFunction) {
        return innerGetMethodName(szFunction);
    }

    private static String innerGetMethodName(SerializableFunction serializableFunction) {
        try {
            Method[] methods = serializableFunction.getClass().getDeclaredMethods();
            if (methods.length < 2) {
                return null;
            }

            Method method = methods[1];
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda)method.invoke(serializableFunction);
            return serializedLambda.getImplMethodName();
        } catch (Exception e) {
            log.error("innerGetMethodName error", e);
        }

        return null;
    }

}
