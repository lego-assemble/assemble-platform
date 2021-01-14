package xyz.leo.lego.assemble.platform.common.util;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author xuyangze
 * @date 2018/9/6 下午3:22
 */
public class AspectUtils {
    private final static String KEY_SEPARATOR = "_";

    public static String resolve(ProceedingJoinPoint pjp, String prefix, Resolver resolver) {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][]paramAnnotations = method.getParameterAnnotations();
        if (StringUtils.isEmpty(prefix)) {
            prefix = method.toString();
        }

        StringBuilder keyStringBuilder = new StringBuilder(prefix);
        Object []args = pjp.getArgs();
        for (int index = 0; index < method.getParameterCount(); index ++) {
            Annotation []annotations = paramAnnotations[index];
            for (Annotation annotation : annotations) {
                String value = resolver.resolve(annotation);
                if (StringUtils.isEmpty(value)) {
                    keyStringBuilder.append(KEY_SEPARATOR).append(args[index].toString());
                } else {
                    keyStringBuilder.append(KEY_SEPARATOR).append(value).append(KEY_SEPARATOR).append(args[index].toString());
                }
            }
        }

        return MD5.encode(keyStringBuilder.toString());
    }

    @FunctionalInterface
    public interface Resolver {
        String resolve(Annotation annotation);
    }
}
