package xyz.leo.lego.assemble.platform.common.aop.process;

import com.alibaba.fastjson.JSON;
import xyz.leo.lego.assemble.platform.common.annotations.Catch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author xuyangze
 * @date 2018/9/10 下午8:26
 */
@Component("catchAspectProcessor")
public class CatchAspectProcessor implements AspectProcessor<Catch> {

    private final static Logger LOGGER = LoggerFactory.getLogger(CatchAspectProcessor.class);

    @Override
    public Object process(ProceedingJoinPoint pjp, AspectProcessorInvoker aspectProcessorInvoker, Catch aCatch) throws Throwable {
        try {
            return aspectProcessorInvoker.invoke(pjp);
        } catch (Throwable e) {
            String errorMsg = resolve(pjp);
            switch (aCatch.type()) {
                case INFO: {
                    LOGGER.info("an error occurred, {}", errorMsg, e);
                    break;
                }
                case WARN: {
                    LOGGER.warn("an error occurred, {}", errorMsg, e);
                    break;
                }
                case ERROR: {
                    LOGGER.error("an error occurred, {}", errorMsg, e);
                    break;
                }
                case DEBUG: {
                    LOGGER.debug("an error occurred, {}", errorMsg, e);
                    break;
                }
            }

            if (aCatch.throwable()) {
                throw e;
            }
        }

        return null;
    }

    private String resolve(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method method = methodSignature.getMethod();
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(" method: ").append(method.toString()).
                append(" args: ").append(JSON.toJSONString(pjp.getArgs()));

        return messageBuilder.toString();
    }
}
