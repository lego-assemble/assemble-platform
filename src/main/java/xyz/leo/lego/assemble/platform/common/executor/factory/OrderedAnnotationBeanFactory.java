package xyz.leo.lego.assemble.platform.common.executor.factory;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/5 11:09 AM
 */
public interface OrderedAnnotationBeanFactory<A extends Annotation, B, T> {

    /**
     * 获取bean
     * @param type
     * @return
     */
    List<OrderedAnnotationBean<A, B>> getBean(T type);

    /**
     * 获取bean
     * @return
     */
    List<OrderedAnnotationBean<A, B>> allBean();

    /**
     * 配置信息
     * @param <A>
     * @param <B>
     */
    class OrderedAnnotationBean<A, B> {
        /**
         * 注解信息
         */
        A annotationInfo;

        /**
         * bean
         */
        B beanInfo;

        public OrderedAnnotationBean(A annotationInfo, B beanInfo) {
            this.annotationInfo = annotationInfo;
            this.beanInfo = beanInfo;
        }

        public A getAnnotationInfo() {
            return annotationInfo;
        }

        public B getBeanInfo() {
            return beanInfo;
        }
    }
}
