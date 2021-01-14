package xyz.leo.lego.assemble.platform.service.copier.convert;

import xyz.leo.lego.assemble.platform.common.copy.BeanFieldCopierConvert;
import xyz.leo.lego.assemble.platform.common.util.SafetyUtils;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutDO;
import xyz.leo.lego.assemble.platform.service.copier.annotation.BeanConverter;
import xyz.leo.lego.assemble.platform.service.copier.base.LegoBeanCopierConverter;
import xyz.leo.lego.assemble.platform.service.domain.dto.LayoutDTO;

/**
 * @author xuyangze
 * @date 2019/12/5 8:16 PM
 */
@BeanConverter
public class LayoutBeanCopierConverter extends LegoBeanCopierConverter<LayoutDO, LayoutDTO> {

    @Override
    protected void registerSource2TargetFieldConverter(BeanFieldCopierConvert<LayoutDO, LayoutDTO> source2Target, LayoutDTO target) {
        source2Target.register(target::setParam, value -> SafetyUtils.toMap(value));
    }

    @Override
    protected void registerTarget2SourceFieldConverter(BeanFieldCopierConvert<LayoutDTO, LayoutDO> target2Source, LayoutDO source) {
        target2Source.register(source::setParam, SafetyUtils::toJSON);
    }

    @Override
    protected LayoutDO generateSourceInstance() {
        return new LayoutDO();
    }

    @Override
    protected LayoutDTO generateTargetInstance() {
        return new LayoutDTO();
    }
}
