package xyz.leo.lego.assemble.platform.common.copy;

import com.google.common.collect.Maps;
import xyz.leo.lego.assemble.platform.common.sf.SzConsumer;
import xyz.leo.lego.assemble.platform.common.sf.SerializableFunctionUtils;
import xyz.leo.lego.assemble.platform.common.sf.SzFunction;

import java.util.Map;

/**
 * @author xuyangze
 * @date 2019/12/5 4:23 PM
 */
public class BeanFieldCopierConvert<S, T> implements BeanCopierConvert<S, T> {
    private Map<String, FieldConverter> setterMethod2FieldMap = Maps.newHashMap();

    private Class<? extends S> sourceClass;

    private Class<? extends T> targetClass;

    public BeanFieldCopierConvert(Class<? extends S> sourceClass, Class<? extends T> targetClass) {
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    @Override
    public Class<? extends S> sourceClass() {
        return sourceClass;
    }

    @Override
    public Class<? extends T> targetClass() {
        return targetClass;
    }

    @Override
    public Object covert(Object value, String setterMethod) {
        FieldConverter fieldConverter = setterMethod2FieldMap.get(setterMethod);
        if (null == fieldConverter) {
            return value;
        }

        Object convertValue = fieldConverter.convert(value);
        return convertValue;
    }

    /**
     * 注册字段转化器
     * @param szConsumer
     * @param fieldConverter
     */
    public <A> void register(SzConsumer<A> szConsumer, FieldConverter fieldConverter) {
        setterMethod2FieldMap.put(SerializableFunctionUtils.getMethodName(szConsumer), fieldConverter);
    }
}
