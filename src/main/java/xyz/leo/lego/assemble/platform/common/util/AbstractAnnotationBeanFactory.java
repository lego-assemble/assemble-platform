package xyz.leo.lego.assemble.platform.common.util;

import java.lang.annotation.Annotation;

public abstract class AbstractAnnotationBeanFactory<T> extends AbstractCustomAnnotationBeanFactory<T, T> {
    @Override
    protected T createBean(T bean, Annotation annotation) {
        return bean;
    }
}