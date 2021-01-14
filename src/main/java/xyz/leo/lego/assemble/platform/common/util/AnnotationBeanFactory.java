package xyz.leo.lego.assemble.platform.common.util;

public interface AnnotationBeanFactory<T> {
    T getBean(String name);
}
