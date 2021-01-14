package xyz.leo.lego.assemble.platform.service.copier.convert;

import xyz.leo.lego.assemble.platform.common.copy.BeanFieldCopierConvert;
import xyz.leo.lego.assemble.platform.common.util.SafetyUtils;
import xyz.leo.lego.assemble.platform.dao.domain.FloorDO;
import xyz.leo.lego.assemble.platform.service.copier.annotation.BeanConverter;
import xyz.leo.lego.assemble.platform.service.copier.base.LegoBeanCopierConverter;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorDTO;

/**
 * @author xuyangze
 * @date 2019/12/5 8:16 PM
 */
@BeanConverter
public class FloorBeanCopierConverter extends LegoBeanCopierConverter<FloorDO, FloorDTO> {

    @Override
    protected void registerSource2TargetFieldConverter(BeanFieldCopierConvert<FloorDO, FloorDTO> source2Target, FloorDTO target) {
        source2Target.register(target::setParam, value -> SafetyUtils.toMap(value));
    }

    @Override
    protected void registerTarget2SourceFieldConverter(BeanFieldCopierConvert<FloorDTO, FloorDO> target2Source, FloorDO source) {
        target2Source.register(source::setParam, SafetyUtils::toJSON);
    }

    @Override
    protected FloorDO generateSourceInstance() {
        return new FloorDO();
    }

    @Override
    protected FloorDTO generateTargetInstance() {
        return new FloorDTO();
    }
}
