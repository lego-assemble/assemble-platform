package xyz.leo.lego.assemble.platform.service.copier.base;

import lombok.extern.slf4j.Slf4j;
import xyz.leo.lego.assemble.platform.common.copy.BeanFieldCopierConvert;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;

/**
 * @author xuyangze
 * @date 2019/12/5 8:21 PM
 */
@Slf4j
public abstract class LegoBeanCopierConverter<S, T> {
    private BeanFieldCopierConvert<S, T> source2Target;
    private BeanFieldCopierConvert<T, S> target2Source;

    private S source;

    private T target;

    public LegoBeanCopierConverter() {
        S sourceInstance = generateSourceInstance();
        T targetInstance = generateTargetInstance();
        this.source2Target = new BeanFieldCopierConvert(sourceInstance.getClass(), targetInstance.getClass());
        this.target2Source = new BeanFieldCopierConvert(targetInstance.getClass(), sourceInstance.getClass());

        this.source = sourceInstance;
        this.target = targetInstance;
    }

    public void doRegister() {
        this.registerSource2TargetFieldConverter(source2Target, target);
        this.registerTarget2SourceFieldConverter(target2Source, source);

        BeanUtils.registerConvert(source2Target);
        BeanUtils.registerConvert(target2Source);
    }

    /**
     * 注册字段转化器
     * @param source2Target
     * @param target
     */
    protected abstract void registerSource2TargetFieldConverter(BeanFieldCopierConvert<S, T> source2Target, T target);

    /**
     * 注册字段转化器
     * @param target2Source
     * @param source
     */
    protected abstract void registerTarget2SourceFieldConverter(BeanFieldCopierConvert<T, S> target2Source, S source);

    /**
     * 来源
     * @return
     */
    protected abstract S generateSourceInstance();

    /**
     * 目标
     * @return
     */
    protected abstract T generateTargetInstance();
}
