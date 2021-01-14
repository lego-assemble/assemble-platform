package xyz.leo.lego.assemble.platform.service.copier.convert;

import xyz.leo.lego.assemble.platform.common.copy.BeanFieldCopierConvert;
import xyz.leo.lego.assemble.platform.common.util.SafetyUtils;
import xyz.leo.lego.assemble.platform.dao.domain.FloorTemplateDO;
import xyz.leo.lego.assemble.platform.service.copier.annotation.BeanConverter;
import xyz.leo.lego.assemble.platform.service.copier.base.LegoBeanCopierConverter;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorTemplateDTO;

/**
 * @author xuyangze
 * @date 2019/12/5 8:16 PM
 */
@BeanConverter
public class FloorTemplateBeanCopierConverter extends LegoBeanCopierConverter<FloorTemplateDO, FloorTemplateDTO> {

    @Override
    protected void registerSource2TargetFieldConverter(BeanFieldCopierConvert<FloorTemplateDO, FloorTemplateDTO> source2Target, FloorTemplateDTO target) {
        source2Target.register(target::setParam, value -> SafetyUtils.toMap(value));
    }

    @Override
    protected void registerTarget2SourceFieldConverter(BeanFieldCopierConvert<FloorTemplateDTO, FloorTemplateDO> target2Source, FloorTemplateDO source) {
        target2Source.register(source::setParam, SafetyUtils::toJSON);
    }

    @Override
    protected FloorTemplateDO generateSourceInstance() {
        return new FloorTemplateDO();
    }

    @Override
    protected FloorTemplateDTO generateTargetInstance() {
        return new FloorTemplateDTO();
    }
}
