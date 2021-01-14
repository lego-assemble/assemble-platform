package xyz.leo.lego.assemble.platform.service.copier.convert;

import xyz.leo.lego.assemble.platform.common.copy.BeanFieldCopierConvert;
import xyz.leo.lego.assemble.platform.common.util.SafetyUtils;
import xyz.leo.lego.assemble.platform.dao.domain.ActivityDO;
import xyz.leo.lego.assemble.platform.service.copier.annotation.BeanConverter;
import xyz.leo.lego.assemble.platform.service.copier.base.LegoBeanCopierConverter;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActivityDTO;

/**
 * @author xuyangze
 * @date 2019/12/5 8:16 PM
 */
@BeanConverter
public class ActivityBeanCopierConverter extends LegoBeanCopierConverter<ActivityDO, ActivityDTO> {

    @Override
    protected void registerSource2TargetFieldConverter(BeanFieldCopierConvert<ActivityDO, ActivityDTO> source2Target,
            ActivityDTO target) {
        source2Target.register(target::setParams, value -> SafetyUtils.toMap(value));
    }

    @Override
    protected void registerTarget2SourceFieldConverter(BeanFieldCopierConvert<ActivityDTO, ActivityDO> target2Source,
            ActivityDO source) {
        target2Source.register(source::setParams, SafetyUtils::toJSON);
    }

    @Override
    protected ActivityDO generateSourceInstance() {
        return new ActivityDO();
    }

    @Override
    protected ActivityDTO generateTargetInstance() {
        return new ActivityDTO();
    }
}
