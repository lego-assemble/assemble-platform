package xyz.leo.lego.assemble.platform.common.executor.factory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2019/11/4 8:23 PM
 */
public abstract class AbstractOrderedAnnotationBeanFactory<A extends Annotation, B, T> implements OrderedAnnotationBeanFactory<A, B, T>, ApplicationListener<ContextRefreshedEvent> {
    private volatile Map<String, List<OrderedAnnotationBean<A, B>>> orderedAnnotationBeanMap = Maps.newHashMap();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(annotationClass());
        if (MapUtils.isEmpty(beanMap)) {
            return;
        }

        Map<String, List<OrderedAnnotationBean<A, B>>> tempOrderedAnnotationBeanMap = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            B bean = (B)entry.getValue();
            A annotationInfo = getAnnotation(bean);

            OrderedAnnotationBean orderedAnnotationBean = new OrderedAnnotationBean(annotationInfo, bean);
            String type = getBeanTypeByAnnotation(annotationInfo);
            List<OrderedAnnotationBean<A, B>> orderedAnnotationBeans = tempOrderedAnnotationBeanMap.get(type);
            if (null == orderedAnnotationBeans) {
                orderedAnnotationBeans = Lists.newArrayList();
                tempOrderedAnnotationBeanMap.put(type, orderedAnnotationBeans);
            }

            orderedAnnotationBeans.add(orderedAnnotationBean);
        }

        if (MapUtils.isNotEmpty(tempOrderedAnnotationBeanMap)) {
            orderedAnnotationBeanMap = tempOrderedAnnotationBeanMap;
        }
    }

    @Override
    public List<OrderedAnnotationBean<A, B>> allBean() {
        List<OrderedAnnotationBean<A, B>> orderedAnnotationBeans = Lists.newArrayList();
        for (Map.Entry<String, List<OrderedAnnotationBean<A, B>>> entry : orderedAnnotationBeanMap.entrySet()) {
            orderedAnnotationBeans.addAll(entry.getValue());
        }

        return orderedAnnotationBeans;
    }

    @Override
    public List<OrderedAnnotationBean<A, B>> getBean(T type) {
        return orderedAnnotationBeanMap.get(getBeanType(type));
    }

    protected abstract String getBeanTypeByAnnotation(A annotationInfo);

    protected abstract String getBeanType(T type);

    protected abstract Class<? extends A> annotationClass();

    private A getAnnotation(B bean) {
        A annotationInfo = bean.getClass().getAnnotation(annotationClass());
        return annotationInfo;
    }
}
