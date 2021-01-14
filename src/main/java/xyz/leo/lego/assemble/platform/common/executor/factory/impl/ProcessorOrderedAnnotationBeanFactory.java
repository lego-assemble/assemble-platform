package xyz.leo.lego.assemble.platform.common.executor.factory.impl;

import org.springframework.stereotype.Component;
import xyz.leo.lego.assemble.platform.common.executor.annotations.Processor;
import xyz.leo.lego.assemble.platform.common.executor.factory.AbstractOrderedAnnotationBeanFactory;
import xyz.leo.lego.assemble.platform.common.executor.processor.IProcessor;

/**
 * @author xuyangze
 * @date 2019/11/5 11:11 AM
 */
@Component("processorOrderedAnnotationBeanFactory")
public class ProcessorOrderedAnnotationBeanFactory extends AbstractOrderedAnnotationBeanFactory<Processor, IProcessor, String> {
    @Override
    protected String getBeanTypeByAnnotation(Processor annotationInfo) {
        return getBeanType(annotationInfo.value());
    }

    @Override
    protected String getBeanType(String type) {
        return type;
    }

    @Override
    protected Class<? extends Processor> annotationClass() {
        return Processor.class;
    }
}
