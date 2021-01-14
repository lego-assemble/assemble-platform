package xyz.leo.lego.assemble.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutTemplateDO;
import xyz.leo.lego.assemble.platform.dao.mapper.LayoutTemplateDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.LayoutTemplateService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.LayoutTemplateDTO;

/**
 * @author xuyangze
 * @date 2019/12/4 4:56 PM
 */
@Service
public class LayoutTemplateServiceImpl implements LayoutTemplateService {

    @Autowired
    private LayoutTemplateDOMapper layoutTemplateDOMapper;

    @Override
    public boolean createLayoutTemplate(long operateUserId, LayoutTemplateDTO layoutTemplateDTO) {
        LayoutTemplateDO layoutTemplateDO = BeanUtils.copy(layoutTemplateDTO, LayoutTemplateDO.class);
        return MybatisExampleUtils.insert(layoutTemplateDO, layoutTemplateDOMapper);
    }

    @Override
    public boolean updateLayoutTemplate(long operateUserId, LayoutTemplateDTO layoutTemplateDTO) {
        if (null == layoutTemplateDTO.getId()) {
            return false;
        }

        Long layoutTemplateId = layoutTemplateDTO.getId();
        LayoutTemplateDO layoutTemplateDO = BeanUtils.copy(layoutTemplateDTO, LayoutTemplateDO.class);
        layoutTemplateDO.setId(null);

        return MybatisExampleUtils.update(layoutTemplateDO, layoutTemplateDOMapper, layoutTemplateDOExample -> {
            layoutTemplateDOExample.createCriteria().andIdEqualTo(layoutTemplateId);
        });
    }

    @Override
    public boolean removeLayoutTemplate(long operateUserId, Long layoutTemplateId) {
        LayoutTemplateDO layoutTemplateDO = new LayoutTemplateDO();
        layoutTemplateDO.setIsDeleted(true);

        return MybatisExampleUtils.update(layoutTemplateDO, layoutTemplateDOMapper, layoutTemplateDOExample -> {
            layoutTemplateDOExample.createCriteria().andIdEqualTo(layoutTemplateId);
        });
    }

    @Override
    public Page<LayoutTemplateDTO> queryLayoutTemplates(long operateUserId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(LayoutTemplateDTO.class, searchAO.getPage(), searchAO.getPageSize(), layoutTemplateDOMapper, layoutTemplateDOExample -> {
            layoutTemplateDOExample.createCriteria().
                    andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).
                    andIsDeletedEqualTo(false);
        });
    }
}
