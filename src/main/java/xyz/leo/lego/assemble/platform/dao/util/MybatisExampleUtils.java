package xyz.leo.lego.assemble.platform.dao.util;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author xuyangze
 * @date 2019/5/9 7:40 PM
 */
@Slf4j
public class MybatisExampleUtils {

    private static Map<BaseMapper, Class> mapper2ExampleMap = Maps.newHashMap();

    private static Map<BaseMapper, Class> mapper2DoClassMap = Maps.newHashMap();

    private static final String EXAMPLE_METHOD_NAME = "selectByExample";

    private static final String DO_METHOD_NAME = "selectByPrimaryKey";

    /**
     * 统计数量
     * 
     * @param mapper
     * @param consumer
     * @param <D>
     * @param <E>
     * @return
     */
    public static <D, E> long count(BaseMapper<D, E> mapper, Consumer<E> consumer) {
        E example = getExample(mapper);
        consumer.accept(example);

        return mapper.countByExample(example);
    }

    /**
     * 查询单个数据
     * 
     * @param targetClass
     * @param mapper
     * @param consumer
     * @param <T>
     * @param <D>
     * @param <E>
     * @return
     */
    public static <T, D, E> T query(Class<? extends T> targetClass, BaseMapper<D, E> mapper, Consumer<E> consumer) {
        Object value = query(mapper, consumer);
        return BeanUtils.toBean(value, targetClass);
    }

    /**
     * 查询单条数据
     * 
     * @param mapper
     * @param consumer
     * @param <D>
     * @param <E>
     * @return
     */
    public static <D, E> D query(BaseMapper<D, E> mapper, Consumer<E> consumer) {
        E example = getExample(mapper);
        consumer.accept(example);

        List<D> values = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }

        return values.get(0);
    }

    /**
     * 查询列表数据
     * 
     * @param targetClass
     * @param mapper
     * @param consumer
     * @param <T>
     * @param <D>
     * @param <E>
     * @return
     */
    public static <T, D, E> List<T> queryList(Class<? extends T> targetClass, BaseMapper<D, E> mapper,
            Consumer<E> consumer) {
        List<D> values = queryList(mapper, consumer);
        return (List<T>) BeanUtils.toBeanAsList(values, targetClass);
    }

    /**
     * 查询列表数据
     * 
     * @param mapper
     * @param consumer
     * @param <D>
     * @param <E>
     * @return
     */
    public static <D, E> List<D> queryList(BaseMapper<D, E> mapper, Consumer<E> consumer) {
        E example = getExample(mapper);
        consumer.accept(example);

        List<D> values = mapper.selectByExample(example);
        return values;
    }

    /**
     * 分页查询
     * 
     * @param targetClass
     * @param page
     * @param pageSize
     * @param baseMapper
     * @param consumer
     * @param <D>
     * @param <E>
     * @param <T>
     * @return
     */
    public static <D, E, T> Page<T> pageQuery(Class<? extends T> targetClass, int page, int pageSize,
            BaseMapper<D, E> baseMapper, Consumer<E> consumer) {
        PageHelper.startPage(page, pageSize);

        List<T> dos = queryList(targetClass, baseMapper, consumer);
        if (CollectionUtils.isEmpty(dos)) {
            return new Page<>(null, false, page);
        }

        long total = MybatisExampleUtils.count(baseMapper, consumer);
        boolean hasMore = total > (pageSize * page);
        return new Page<>(dos, hasMore, page);
    }

    /**
     * 分页查询
     * 
     * @param page
     * @param pageSize
     * @param baseMapper
     * @param consumer
     * @param <D>
     * @param <E>
     * @return
     */
    public static <D, E> Page<D> pageQuery(int page, int pageSize, BaseMapper<D, E> baseMapper, Consumer<E> consumer) {
        PageHelper.startPage(page, pageSize);

        List<D> dos = queryList(baseMapper, consumer);
        if (CollectionUtils.isEmpty(dos)) {
            return new Page<>(null, false, page);
        }

        long total = MybatisExampleUtils.count(baseMapper, consumer);
        boolean hasMore = total / pageSize > page;
        return new Page<>(dos, hasMore, page);
    }

    /**
     * 写入数据
     * 
     * @param target
     * @param mapper
     * @param <T>
     * @param <D>
     * @param <E>
     * @return
     */
    public static <T, D, E> boolean insert(T target, BaseMapper<D, E> mapper) {
        D doValue = BeanUtils.toBean(target, getDoClass(mapper));
        return mapper.insertSelective(doValue) > 0;
    }

    /**
     * 批量写入数据
     * 
     * @param targets
     * @param mapper
     * @param <T>
     * @param <D>
     * @param <E>
     * @return
     */
    public static <T, D, E> boolean insertList(List<T> targets, BaseMapper<D, E> mapper) {
        targets.stream().forEach(target -> insert(target, mapper));
        return true;
    }

    /**
     * 写入数据
     * 
     * @param target
     * @param mapper
     * @param <T>
     * @param <D>
     * @param <E>
     * @return
     */
    public static <T, D, E> boolean update(T target, BaseMapper<D, E> mapper, Consumer<E> consumer) {
        D doValue = BeanUtils.toBean(target, getDoClass(mapper));
        E example = getExample(mapper);
        consumer.accept(example);
        return mapper.updateByExampleSelective(doValue, example) > 0;
    }

    private static <D, E> E getExample(BaseMapper<D, E> mapper) {
        Class exampleClass = mapper2ExampleMap.get(mapper);
        if (null != exampleClass) {
            return (E) BeanUtils.newInstance(exampleClass);
        }

        synchronized (mapper) {
            exampleClass = createExample(mapper);
            return (E) BeanUtils.newInstance(exampleClass);
        }
    }

    private static <D, E> Class<? extends E> createExample(BaseMapper<D, E> mapper) {
        return mapper.getExampleClass();
//        Method []methods = mapper.getClass().getDeclaredMethods();
//        Class exampleClass = null;
//        for (Method method : methods) {
//            if (method.getName().equals(EXAMPLE_METHOD_NAME)) {
//                log.info("method: " + mapper.getClass());
//                log.info("method param: " + JSON.toJSONString(mapper.getClass()));
//                exampleClass = method.getParameters()[0].getType();
//                break;
//            }
//        }
//
//        if ("Object".equals(exampleClass.getSimpleName())) {
//            return null;
//        }
//
//        mapper2ExampleMap.put(mapper, exampleClass);
//        log.info("mapper: {} register example: {}",
//                mapper.getClass().getSimpleName(), exampleClass.getSimpleName());
//        return (Class<? extends E>) exampleClass;
    }

    private static <D, E> Class<? extends D> getDoClass(BaseMapper<D, E> mapper) {
        Class doClass = mapper2DoClassMap.get(mapper);
        if (null != doClass) {
            return (Class<? extends D>) doClass;
        }

        synchronized (mapper) {
            doClass = createDoClass(mapper);
            return (Class<? extends D>) doClass;
        }
    }

    private static <D, E> Class<? extends D> createDoClass(BaseMapper<D, E> mapper) {
        Method[] methods = mapper.getClass().getDeclaredMethods();
        Class doClass = null;
        for (Method method : methods) {
            if (method.getName().equals(DO_METHOD_NAME)) {
                doClass = method.getReturnType();
                break;
            }
        }

        mapper2DoClassMap.put(mapper, doClass);
        return (Class<? extends D>) doClass;
    }
}
