package xyz.leo.lego.assemble.platform.common.executor.executor;

import org.apache.commons.collections4.CollectionUtils;
import xyz.leo.lego.assemble.platform.common.executor.factory.OrderedAnnotationBeanFactory;
import xyz.leo.lego.assemble.platform.common.executor.util.OrderedExecutor;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/4 8:14 PM
 */
public abstract class AbstractOrderedExecuteService<A extends Annotation, B, T, D> implements IExecuteService<T, D> {
    @Override
    public void execute(T type, D data) {
        OrderedExecutor orderedExecutor = new OrderedExecutor();
        submit(getBeanFactory().getBean(type), data, orderedExecutor);
        orderedExecutor.executeAndWaitDone();
    }

    @Override
    public void execute(List<ExecuteInfo<T, D>> executeInfos) {
        OrderedExecutor orderedExecutor = new OrderedExecutor();
        for (ExecuteInfo<T, D> executeInfo : executeInfos) {
            submit(getBeanFactory().getBean(executeInfo.getType()), executeInfo.getData(), orderedExecutor);
        }

        orderedExecutor.executeAndWaitDone();
    }

    private void submit(List<OrderedAnnotationBeanFactory.OrderedAnnotationBean<A, B>> beans, D data, OrderedExecutor orderedExecutor) {
        if (CollectionUtils.isEmpty(beans)) {
            return;
        }

        for (OrderedAnnotationBeanFactory.OrderedAnnotationBean<A, B> orderedAnnotationBean : beans) {
            B beanInfo = orderedAnnotationBean.getBeanInfo();
            A annotationInfo = orderedAnnotationBean.getAnnotationInfo();

            orderedExecutor.submit(getOrder(annotationInfo), () -> {
                doExecute(beanInfo, data);
            });
        }
    }

    protected abstract OrderedAnnotationBeanFactory<A, B, T> getBeanFactory();

    protected abstract int getOrder(A processorAnnotation);

    protected abstract void doExecute(B executor, D data);
}
