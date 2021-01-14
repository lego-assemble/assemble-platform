package xyz.leo.lego.assemble.platform.common.aop.process;

import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.annotation.Annotation;

/**
 * @author xuyangze
 * @date 2018/9/10 下午8:16
 */
public interface AspectProcessor<T extends Annotation> {
    AspectProcessorInvoker DEFAULT_INVOKE = new DefaultAspectProcessorInvoker();

    default Object process(ProceedingJoinPoint proceedingJoinPoint, T annotation) throws Throwable {
        return process(proceedingJoinPoint, DEFAULT_INVOKE, annotation);
    }

    Object process(ProceedingJoinPoint proceedingJoinPoint, AspectProcessorInvoker aspectProcessorInvoker, T annotation) throws Throwable;

    class DefaultAspectProcessorInvoker implements AspectProcessorInvoker {
        @Override
        public Object invoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            return proceedingJoinPoint.proceed();
        }
    }
}