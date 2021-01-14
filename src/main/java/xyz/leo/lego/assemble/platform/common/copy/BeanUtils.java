package xyz.leo.lego.assemble.platform.common.copy;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author xuyangze
 * @date 2019/5/9 11:49 AM
 */
@Slf4j
public class BeanUtils {
    private static Map<Class, Map<Class, BeanCopierGroup>> sourceTarget2CopierMap = Maps.newIdentityHashMap();
    private static Map<Class, Map<Class, Converter>> source2Target2ConvertMap = Maps.newConcurrentMap();

    /**
     * 尝试转化为另一个对象，并将数据copy到其他对象
     * @param sources
     * @param targetClass
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E, T> List<T> toBeanAsList(Collection<E> sources, Class<? extends T> targetClass) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }

        List<T> targets = sources.stream().map(source -> toBean(source, targetClass)).collect(Collectors.toList());
        return targets;
    }

    /**
     * 尝试转化为另一个对象，并将数据copy到其他对象
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T toBean(Object source, Class<? extends T> targetClass) {
        return toBeanOrCopy(source, targetClass, false);
    }

    /**
     * 将数据copy到其他对象
     * @param sources
     * @param targetClass
     * @param <E>
     * @param <T>
     * @return
     */
    public static <E, T> List<T> copyAsList(Collection<E> sources, Class<? extends T> targetClass) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }

        List<T> targets = sources.stream().map(source -> copy(source, targetClass)).collect(Collectors.toList());
        return targets;
    }

    /**
     * 将数据copy到其他对象
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, Class<? extends T> targetClass) {
        return toBeanOrCopy(source, targetClass, true);
    }

    /**
     * copy对象到另一个对象
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, T target) {
        return innerCopy(source, target);
    }

    /**
     * 创建对象
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<? extends T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (Exception e) {
            log.error("error newInstance : {}", targetClass.getName(), e);
            return null;
        }
    }

    /**
     * 注册转化器
     * @param beanCopierConvert
     */
    public static void registerConvert(BeanCopierConvert beanCopierConvert) {
        Map<Class, Converter> target2ConvertMap = source2Target2ConvertMap.get(beanCopierConvert.sourceClass());
        if (null == target2ConvertMap) {
            target2ConvertMap = Maps.newHashMap();
            source2Target2ConvertMap.put(beanCopierConvert.sourceClass(), target2ConvertMap);
        }

        target2ConvertMap.put(beanCopierConvert.targetClass(), ((value, target, context) -> beanCopierConvert.covert(value, String.valueOf(context))));
    }

    private static Converter queryConverter(Class source, Class target) {
        Map<Class, Converter> target2ConvertMap = source2Target2ConvertMap.get(source);
        if (null == target2ConvertMap) {
            return null;
        }

        return target2ConvertMap.get(target);
    }

    /**
     * 转为其他bean，如果类型一致则返回当前对象
     * @param source
     * @param targetClass
     * @param forceCopy 是否强制copy
     * @param <T>
     * @return
     */
    private static <T> T toBeanOrCopy(Object source, Class<? extends T> targetClass, boolean forceCopy) {
        if (null == source) {
            return null;
        }

        T target = null;
        if (forceCopy) {
            target = newInstance(targetClass);
        } else if (Objects.equals(source.getClass(), targetClass)) {
            return (T) source;
        } else {
            target = newInstance(targetClass);
        }

        return copy(source, target);
    }

    private static <T> T innerCopy(Object source, T target) {
        Converter converter = queryConverter(source.getClass(), target.getClass());
        BeanCopier beanCopier = generate(source.getClass(), target.getClass(), null != converter);

        if (null == target) {
            return null;
        }

        beanCopier.copy(source, target, converter);
        return target;
    }

    private static BeanCopier generate(Class source, Class target, boolean convert) {
        BeanCopier beanCopier = read(() -> get(source, target, convert));
        if (null != beanCopier) {
            return beanCopier;
        }

        beanCopier = write(() -> {
            BeanCopier targetCopier = get(source, target, convert);
            if (null != targetCopier) {
                return targetCopier;
            }

            targetCopier = BeanCopier.create(source, target, convert);
            set(source, target, targetCopier, convert);

            return targetCopier;
        }, source);

        return beanCopier;
    }

    private static <T> T read(Supplier<T> supplier) {
        return supplier.get();
    }

    private static BeanCopier get(Class source, Class target, boolean convert) {
        Map<Class, BeanCopierGroup> beanCopierMap = sourceTarget2CopierMap.get(source);
        if (MapUtils.isEmpty(beanCopierMap)) {
            return null;
        }

        BeanCopierGroup beanCopierGroup = beanCopierMap.get(target);
        if (null == beanCopierGroup) {
            return null;
        }

        return beanCopierGroup.get(convert);
    }

    private static void set(Class source, Class target, BeanCopier beanCopier, boolean convert) {
        Map<Class, BeanCopierGroup> beanCopierMap = sourceTarget2CopierMap.get(source);
        if (MapUtils.isEmpty(beanCopierMap)) {
            beanCopierMap = Maps.newIdentityHashMap();
            sourceTarget2CopierMap.put(source, beanCopierMap);
        }

        BeanCopierGroup beanCopierGroup = beanCopierMap.get(target);
        if (null == beanCopierGroup) {
            beanCopierGroup = new BeanCopierGroup();
            beanCopierMap.put(target, beanCopierGroup);
        }

        beanCopierGroup.put(convert, beanCopier);
    }

    private static <T> T write(Supplier<T> supplier, Object lockKey) {
        synchronized (lockKey) {
            return supplier.get();
        }
    }
}

