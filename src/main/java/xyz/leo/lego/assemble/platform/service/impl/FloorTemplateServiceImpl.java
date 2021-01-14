package xyz.leo.lego.assemble.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.dao.domain.FloorTemplateDO;
import xyz.leo.lego.assemble.platform.dao.domain.FloorTemplateDOExample;
import xyz.leo.lego.assemble.platform.dao.mapper.FloorTemplateDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.FloorTemplateService;
import xyz.leo.lego.assemble.platform.service.FunctionService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorTemplateDTO;


/**
 * @author xuyangze
 * @date 2019/11/12 8:15 PM
 */
@Service
public class FloorTemplateServiceImpl implements FloorTemplateService {

    @Autowired
    private FloorTemplateDOMapper floorTemplateDOMapper;

    @Autowired
    private FunctionService functionService;

    @Override
    public Page<FloorTemplateDTO> queryTemplates(Long operateUserId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(FloorTemplateDTO.class, searchAO.getPage(), searchAO.getPageSize(), floorTemplateDOMapper, floorTemplateDOExample -> {
            floorTemplateDOExample.createCriteria().
                    andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).
                    andIsDeletedEqualTo(false);
        });
    }

    @Override
    @Transactional
    public boolean insert(Long operateUserId, FloorTemplateDTO floorTemplateDTO) {
        FloorTemplateDO floorTemplateDO = BeanUtils.copy(floorTemplateDTO, FloorTemplateDO.class);
        floorTemplateDO.setId(null);
        floorTemplateDO.setGmtCreate(null);
        floorTemplateDO.setGmtModified(null);
        floorTemplateDO.setModifier(null);
        floorTemplateDO.setCreator(null);

        boolean success = MybatisExampleUtils.insert(floorTemplateDO, floorTemplateDOMapper);
        return success;
    }

    @Override
    public boolean update(Long operateUserId, FloorTemplateDTO floorTemplateDTO) {
        if (null == floorTemplateDTO || null == floorTemplateDTO.getId()) {
            return false;
        }

        FloorTemplateDO floorTemplateDO = BeanUtils.copy(floorTemplateDTO, FloorTemplateDO.class);
        floorTemplateDO.setGmtCreate(null);
        floorTemplateDO.setGmtModified(null);
        floorTemplateDO.setModifier(null);
        floorTemplateDO.setCreator(null);

        boolean success = MybatisExampleUtils.update(floorTemplateDO, floorTemplateDOMapper, floorTemplateDOExample -> {
            floorTemplateDOExample.createCriteria().andIdEqualTo(floorTemplateDO.getId());
        });

        return success;
    }

    @Override
    public boolean remove(Long operateUserId, Long floorTemplateId) {
        FloorTemplateDO floorTemplateDO = new FloorTemplateDO();
        floorTemplateDO.setIsDeleted(true);

        FloorTemplateDOExample floorTemplateDOExample = new FloorTemplateDOExample();
        floorTemplateDOExample.createCriteria().andIdEqualTo(floorTemplateId);

        return floorTemplateDOMapper.updateByExampleSelective(floorTemplateDO, floorTemplateDOExample) > 0;
    }
}
