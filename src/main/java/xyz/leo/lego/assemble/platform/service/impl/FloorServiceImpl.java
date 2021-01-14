package xyz.leo.lego.assemble.platform.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.dao.domain.FloorDO;
import xyz.leo.lego.assemble.platform.dao.domain.FloorDOExample;
import xyz.leo.lego.assemble.platform.dao.mapper.FloorDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.FloorService;
import xyz.leo.lego.assemble.platform.service.FunctionService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FunctionDTO;

import java.util.Arrays;
import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/12 8:15 PM
 */
@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorDOMapper floorDOMapper;

    @Autowired
    private FunctionService functionService;

    @Override
    public Page<FloorDTO> queryTemplates(Long operateUserId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(FloorDTO.class, searchAO.getPage(), searchAO.getPageSize(), floorDOMapper, floorDOExample -> {
            floorDOExample.createCriteria().
                    andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).
                    andUserIdIn(Arrays.asList(0L, operateUserId)).
                    andIsDeletedEqualTo(false);
        });
    }

    @Override
    @Transactional
    public boolean insert(Long operateUserId, FloorDTO floorDTO) {
        FloorDO floorDO = BeanUtils.copy(floorDTO, FloorDO.class);
        floorDO.setUserId(operateUserId);
        floorDO.setId(null);
        floorDO.setGmtCreate(null);
        floorDO.setGmtModified(null);
        floorDO.setModifier(null);
        floorDO.setCreator(null);

        boolean success = MybatisExampleUtils.insert(floorDO, floorDOMapper);
        functionService.removeByBindIdAndType(operateUserId, floorDO.getId(), "floor");
        if (success && null != floorDTO.getFunction()) {
            functionService.bindByBindIdAndType(operateUserId, floorDO.getId(), "floor", floorDTO.getFunction());
        }

        return success;
    }

    @Override
    public boolean update(Long operateUserId, FloorDTO floorDTO) {
        if (null == floorDTO || null == floorDTO.getId()) {
            return false;
        }

        FloorDO floorDO = BeanUtils.copy(floorDTO, FloorDO.class);
        floorDO.setGmtCreate(null);
        floorDO.setGmtModified(null);
        floorDO.setModifier(null);
        floorDO.setCreator(null);

        boolean success = MybatisExampleUtils.update(floorDO, floorDOMapper, floorDOExample -> {
            floorDOExample.createCriteria().andIdEqualTo(floorDO.getId());
        });

        functionService.removeByBindIdAndType(operateUserId, floorDO.getId(), "floor");
        if (success && null != floorDTO.getFunction()) {
            functionService.bindByBindIdAndType(operateUserId, floorDO.getId(), "floor", floorDTO.getFunction());
        }

        return success;
    }

    @Override
    public boolean remove(Long operateUserId, Long floorId) {
        FloorDO floorDO = new FloorDO();
        floorDO.setIsDeleted(true);

        FloorDOExample floorDOExample = new FloorDOExample();
        floorDOExample.createCriteria().andIdEqualTo(floorId).andUserIdEqualTo(operateUserId);

        return floorDOMapper.updateByExampleSelective(floorDO, floorDOExample) > 0;
    }

    @Override
    public boolean removeByActivityId(Long operateUserId, Long activityId) {
        FloorDO floorDO = new FloorDO();
        floorDO.setIsDeleted(true);

        FloorDOExample floorDOExample = new FloorDOExample();
        floorDOExample.createCriteria().andActivityIdEqualTo(activityId).andUserIdEqualTo(operateUserId);

        return floorDOMapper.updateByExampleSelective(floorDO, floorDOExample) > 0;
    }

    @Override
    public List<FloorDTO> queryByActivityId(Long activityId) {
        List<FloorDTO> floorDTOS = MybatisExampleUtils.queryList(FloorDTO.class, floorDOMapper, floorDOExample -> {
            floorDOExample.createCriteria().andActivityIdEqualTo(activityId).
                    andIsDeletedEqualTo(false);
        });

        if (CollectionUtils.isNotEmpty(floorDTOS)) {
            for (FloorDTO floorDTO : floorDTOS) {
                List<FunctionDTO> functionDTOList = functionService.queryByBindIdAndType(floorDTO.getId(), "floor");
                if (CollectionUtils.isEmpty(functionDTOList)) {
                    continue;
                }

                floorDTO.setFunction(functionDTOList.get(0));
            }
        }

        return floorDTOS;
    }
}
