package xyz.leo.lego.assemble.platform.service.copier.convert;

import xyz.leo.lego.assemble.platform.common.copy.BeanFieldCopierConvert;
import xyz.leo.lego.assemble.platform.common.util.SafetyUtils;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutTemplateDO;
import xyz.leo.lego.assemble.platform.service.copier.annotation.BeanConverter;
import xyz.leo.lego.assemble.platform.service.copier.base.LegoBeanCopierConverter;
import xyz.leo.lego.assemble.platform.service.domain.dto.LayoutTemplateDTO;

/**
 * @author xuyangze
 * @date 2019/12/5 8:16 PM
 */
@BeanConverter
public class LayoutTemplateBeanCopierConverter extends LegoBeanCopierConverter<LayoutTemplateDO, LayoutTemplateDTO> {

    @Override
    protected void registerSource2TargetFieldConverter(BeanFieldCopierConvert<LayoutTemplateDO, LayoutTemplateDTO> source2Target, LayoutTemplateDTO target) {
        source2Target.register(target::setParam, value -> SafetyUtils.toMap(value));
    }

    @Override
    protected void registerTarget2SourceFieldConverter(BeanFieldCopierConvert<LayoutTemplateDTO, LayoutTemplateDO> target2Source, LayoutTemplateDO source) {
        target2Source.register(source::setParam, SafetyUtils::toJSON);
    }

    @Override
    protected LayoutTemplateDO generateSourceInstance() {
        return new LayoutTemplateDO();
    }

    @Override
    protected LayoutTemplateDTO generateTargetInstance() {
        return new LayoutTemplateDTO();
    }
}
