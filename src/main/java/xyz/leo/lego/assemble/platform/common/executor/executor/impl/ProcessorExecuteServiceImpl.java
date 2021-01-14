package xyz.leo.lego.assemble.platform.common.executor.executor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.leo.lego.assemble.platform.common.executor.annotations.Processor;
import xyz.leo.lego.assemble.platform.common.executor.executor.AbstractOrderedExecuteService;
import xyz.leo.lego.assemble.platform.common.executor.factory.OrderedAnnotationBeanFactory;
import xyz.leo.lego.assemble.platform.common.executor.processor.IProcessor;

/**
 * @author xuyangze
 * @date 2019/11/4 8:14 PM
 */
@Service("processorExecuteService")
public class ProcessorExecuteServiceImpl extends AbstractOrderedExecuteService<Processor, IProcessor, String, Object> {

    @Autowired
    @Qualifier("processorOrderedAnnotationBeanFactory")
    protected OrderedAnnotationBeanFactory<Processor, IProcessor, String> orderedAnnotationBeanFactory;

    @Override
    protected int getOrder(Processor processorAnnotation) {
        return processorAnnotation.order();
    }

    @Override
    protected void doExecute(IProcessor executor, Object data) {
        executor.process(data);
    }

    @Override
    protected OrderedAnnotationBeanFactory<Processor, IProcessor, String> getBeanFactory() {
        return orderedAnnotationBeanFactory;
    }
}
