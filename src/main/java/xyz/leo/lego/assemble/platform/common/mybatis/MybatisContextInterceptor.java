package xyz.leo.lego.assemble.platform.common.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import xyz.leo.lego.assemble.platform.common.util.ReflectionUtils;

import java.util.*;

/**
 * @author xuyangze
 * @date 2019/5/13 2:08 PM
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MybatisContextInterceptor implements Interceptor {
    public static final String INSERT = "insert";
    public static final String SAVE = "save";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String DO_SUFFIX = "DO";
    public static final String DO_BLOBS_SUFFIX = "DOWithBLOBs";

    public final static String CREATOR = "creator";
    public final static String GMT_CREATE = "gmtCreate";
    public final static String MODIFIER = "modifier";
    public final static String GMT_MODIFIED = "gmtModified";
    public final static String DELETED = "isDeleted";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 取得方法名
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        String[] split = mappedStatement.getId().split("\\.");
        String methodName = split[split.length - 1];

        // 判断方法类型
        OperatorTypeEnum operatorType = null;
        if (methodName.startsWith(INSERT) || methodName.startsWith(SAVE)) {
            operatorType = OperatorTypeEnum.INSERT;
        } else if (methodName.startsWith(UPDATE)) {
            operatorType = OperatorTypeEnum.UPDATE;
        }

        if (operatorType != null) {
            trySetUserInfo(operatorType, invocation.getArgs()[1]);
        }

        return invocation.proceed();
    }

    private void trySetUserInfo(OperatorTypeEnum operatorType, Object o) {
        // 获取所有可设置用户信息的Map的DO
        List<Map> maps = new ArrayList<>();
        List<Object> dos = new ArrayList<>();
        if (o instanceof Map) {
            maps.add((Map)o);
            Set set = new HashSet(((Map)o).values());
            set.forEach(o1 -> {
                if (o1 instanceof Map) {
                    maps.add((Map)o1);
                } else {
                    String className = o1.getClass().toString();
                    if (isDataObject(className)) {
                        dos.add(o1);
                    } else if (o1 instanceof Collection) {
                        Collection collection = (Collection)o1;
                        if (!CollectionUtils.isEmpty(collection)) {
                            Class<?> parameterizedType = collection.iterator().next().getClass();
                            //getParameterizedType(o1);
                            if (isDataObject(parameterizedType.toString())) {
                                dos.addAll(collection);
                            }
                        }

                    }
                }
            });
        } else {
            String className = o.getClass().toString();
            if (isDataObject(className)) {
                dos.add(o);
            }
        }

        String operator = "system";
        switch (operatorType) {
            case INSERT: {
                maps.forEach(map -> setContextForMapForInsert(map, operator));
                dos.forEach(obj -> setContextForObjForInsert(obj, operator));
                break;
            }
            case UPDATE: {
                maps.forEach(map -> setContextForMapForUpdate(map, operator));
                dos.forEach(obj -> setContextForObjForUpdate(obj, operator));
                break;
            }
            default:
                break;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private boolean isDataObject(String className) {
        return className.endsWith(DO_SUFFIX) || className.endsWith(DO_BLOBS_SUFFIX);
    }

    public enum OperatorTypeEnum {
        /**
         * 插入
         */
        INSERT,
        /**
         * 修改
         */
        UPDATE,
    }

    private void setContextForObjForUpdate(Object obj, String operator) {
        if (ReflectionUtils.invokeGetter(obj, MODIFIER) == null) {
            ReflectionUtils.invokeSetter(obj, MODIFIER, operator);
        }

        ReflectionUtils.invokeSetter(obj, GMT_MODIFIED, new Date());
    }

    private void setContextForMapForUpdate(Map map, String operator) {
        if (!map.containsKey(MODIFIER)) {
            map.put(MODIFIER, operator);
        }

        map.put(GMT_MODIFIED, new Date());
    }

    private void setContextForObjForInsert(Object obj, String operator) {
        if (ReflectionUtils.invokeGetter(obj, CREATOR) == null) {
            ReflectionUtils.invokeSetter(obj, CREATOR, operator);
        }

        if (ReflectionUtils.invokeGetter(obj, MODIFIER) == null) {
            ReflectionUtils.invokeSetter(obj, MODIFIER, operator);
        }

        ReflectionUtils.invokeSetter(obj, GMT_CREATE, new Date());
        ReflectionUtils.invokeSetter(obj, GMT_MODIFIED, new Date());
        ReflectionUtils.invokeSetter(obj, DELETED, false);
    }

    private void setContextForMapForInsert(Map map, String operator) {
        if (!map.containsKey(CREATOR)) {
            map.put(CREATOR, operator);
        }

        if (!map.containsKey(MODIFIER)) {
            map.put(MODIFIER, operator);
        }

        map.put(GMT_CREATE, operator);
        map.put(GMT_MODIFIED, new Date());
        map.put(DELETED, false);
    }
}