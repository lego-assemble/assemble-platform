package xyz.leo.lego.assemble.platform.common.aop.process;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author xuyangze
 * @date 2018/9/10 下午8:16
 */
public interface AspectProcessorInvoker {
    Object invoke(ProceedingJoinPoint proceedingJoinPoint) throws Throwable;
}