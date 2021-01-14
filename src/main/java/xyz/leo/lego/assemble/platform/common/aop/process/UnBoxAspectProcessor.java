package xyz.leo.lego.assemble.platform.common.aop.process;

import xyz.leo.lego.assemble.platform.common.annotations.UnBox;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2018/9/10 下午8:26
 */
@Component("unBoxAspectProcessor")
public class UnBoxAspectProcessor implements AspectProcessor<UnBox> {

    private final static Logger LOGGER = LoggerFactory.getLogger(UnBoxAspectProcessor.class);

    private Map<String, UnBoxStrategy> name2StrategyMap = new HashMap<String, UnBoxStrategy>(){{
        put("boolean", (unBox -> unBox.booleanValue()));
        put("byte", (unBox -> unBox.byteValue()));
        put("char", (unBox -> unBox.charValue()));
        put("double", (unBox -> unBox.doubleValue()));
        put("int", (unBox -> unBox.intValue()));
        put("long", (unBox -> unBox.longValue()));
        put("short", (unBox -> unBox.shortValue()));
        put("float", (unBox -> unBox.floatValue()));
    }};

    @Override
    public Object process(ProceedingJoinPoint pjp, AspectProcessorInvoker aspectProcessorInvoker, UnBox unBox) throws Throwable {
        Object result = aspectProcessorInvoker.invoke(pjp);
        if (null != result) {
            return result;
        }

        // null值不能拆箱，需要赋予默认值
        UnBoxStrategy strategy = name2StrategyMap.get(resolveSync(pjp));
        if (null == strategy) {
            return result;
        }

        return strategy.resolve(unBox);
    }

    private String resolveSync(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        return method.getReturnType().toString();
    }

    @FunctionalInterface
    private interface UnBoxStrategy<T> {
        T resolve(UnBox unBox);
    }
}
