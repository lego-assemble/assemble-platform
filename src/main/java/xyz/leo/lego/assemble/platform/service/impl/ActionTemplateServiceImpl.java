package xyz.leo.lego.assemble.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.common.util.rest.BizException;
import xyz.leo.lego.assemble.platform.common.util.rest.ResponseCodeEnum;
import xyz.leo.lego.assemble.platform.dao.domain.ActionTemplateDO;
import xyz.leo.lego.assemble.platform.dao.enums.FloorTypeEnum;
import xyz.leo.lego.assemble.platform.dao.mapper.ActionTemplateDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.ActionTemplateService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActionTemplateDTO;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/12 8:15 PM
 */
@Service
public class ActionTemplateServiceImpl implements ActionTemplateService {

    @Autowired
    private ActionTemplateDOMapper actionTemplateDOMapper;

    @Override
    public Page<ActionTemplateDTO> queryActionTemplates(Long operateUserId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(ActionTemplateDTO.class, searchAO.getPage(), searchAO.getPageSize(), actionTemplateDOMapper, actionTemplateDOExample -> {
            actionTemplateDOExample.createCriteria().
                    andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).
                    andTypeEqualTo(FloorTypeEnum.TEMPLATE.getType()).
                    andIsDeletedEqualTo(false);
        });
    }

    @Override
    public boolean insert(Long operateUserId, ActionTemplateDTO actionTemplateDTO) {
        ActionTemplateDO existDO = MybatisExampleUtils.query(actionTemplateDOMapper, actionTemplateDOExample -> {
            actionTemplateDOExample.createCriteria().
                    andTypeEqualTo(FloorTypeEnum.TEMPLATE.getType()).
                    andCodeEqualTo(actionTemplateDTO.getCode());
        });

        if (null != existDO) {
            throw new BizException(ResponseCodeEnum.ACTION_CODE_EXIST);
        }

        ActionTemplateDO actionTemplateDO = BeanUtils.copy(actionTemplateDTO, ActionTemplateDO.class);
        actionTemplateDO.setId(null);
        actionTemplateDO.setGmtCreate(null);
        actionTemplateDO.setGmtModified(null);
        actionTemplateDO.setModifier(null);
        actionTemplateDO.setCreator(null);

        return MybatisExampleUtils.insert(actionTemplateDO, actionTemplateDOMapper);
    }

    @Override
    public boolean update(Long operateUserId, ActionTemplateDTO actionTemplateDTO) {
        if (null == actionTemplateDTO.getId()) {
            return false;
        }

        ActionTemplateDO actionTemplateDO = BeanUtils.copy(actionTemplateDTO, ActionTemplateDO.class);
        return MybatisExampleUtils.update(actionTemplateDO, actionTemplateDOMapper, actionTemplateDOExample -> {
            actionTemplateDOExample.createCriteria().andIdEqualTo(actionTemplateDO.getId());
        });
    }

    @Override
    public boolean remove(Long operateUserId, Long actionId) {
        ActionTemplateDO actionTemplateDO = new ActionTemplateDO();
        actionTemplateDO.setIsDeleted(true);

        return MybatisExampleUtils.update(actionTemplateDO, actionTemplateDOMapper, actionTemplateDOExample -> {
            actionTemplateDOExample.createCriteria().andIdEqualTo(actionId);
        });
    }

    @Override
    public List<ActionTemplateDTO> query(List<Long> actionTemplateIds) {
        return MybatisExampleUtils.queryList(ActionTemplateDTO.class, actionTemplateDOMapper, actionTemplateDOExample -> {
            actionTemplateDOExample.createCriteria().
                    andIdIn(actionTemplateIds);
        });
    }
}
