package xyz.leo.lego.assemble.platform.service.copier.convert;

import xyz.leo.lego.assemble.platform.common.copy.BeanFieldCopierConvert;
import xyz.leo.lego.assemble.platform.common.util.SafetyUtils;
import xyz.leo.lego.assemble.platform.dao.domain.ActionTemplateDO;
import xyz.leo.lego.assemble.platform.service.copier.annotation.BeanConverter;
import xyz.leo.lego.assemble.platform.service.copier.base.LegoBeanCopierConverter;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActionTemplateDTO;

/**
 * @author xuyangze
 * @date 2019/12/5 8:16 PM
 */
@BeanConverter
public class ActionTemplateBeanCopierConverter extends LegoBeanCopierConverter<ActionTemplateDO, ActionTemplateDTO> {

    @Override
    protected void registerSource2TargetFieldConverter(BeanFieldCopierConvert<ActionTemplateDO, ActionTemplateDTO> source2Target, ActionTemplateDTO target) {
        source2Target.register(target::setParam, value -> SafetyUtils.toMap(value));
    }

    @Override
    protected void registerTarget2SourceFieldConverter(BeanFieldCopierConvert<ActionTemplateDTO, ActionTemplateDO> target2Source, ActionTemplateDO source) {
        target2Source.register(source::setParam, SafetyUtils::toJSON);
    }

    @Override
    protected ActionTemplateDO generateSourceInstance() {
        return new ActionTemplateDO();
    }

    @Override
    protected ActionTemplateDTO generateTargetInstance() {
        return new ActionTemplateDTO();
    }
}
