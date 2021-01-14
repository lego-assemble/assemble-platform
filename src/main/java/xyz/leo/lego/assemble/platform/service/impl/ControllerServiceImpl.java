package xyz.leo.lego.assemble.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.dao.domain.ControllerDO;
import xyz.leo.lego.assemble.platform.dao.enums.ControllerTypeEnum;
import xyz.leo.lego.assemble.platform.dao.mapper.ControllerDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.ActivityService;
import xyz.leo.lego.assemble.platform.service.ControllerService;
import xyz.leo.lego.assemble.platform.service.LayoutService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActivityDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ControllerDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.LayoutDTO;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/12/2 10:40 AM
 */
@Service
public class ControllerServiceImpl implements ControllerService {

    @Autowired
    private ControllerDOMapper controllerDOMapper;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LayoutService layoutService;


    @Override
    public boolean createController(long operateUserId, ControllerDTO controllerDTO) {
        if (null != controllerDTO.getId()) {
            return false;
        }

        controllerDTO.setUserId(operateUserId);
        ControllerDO controllerDO = BeanUtils.copy(controllerDTO, ControllerDO.class);
        return MybatisExampleUtils.insert(controllerDO, controllerDOMapper);
    }

    @Override
    public boolean updateController(long operateUserId, ControllerDTO controllerDTO) {
        if (null == controllerDTO.getId()) {
            return false;
        }

        Long controllerId = controllerDTO.getId();
        ControllerDO controllerDO = BeanUtils.copy(controllerDTO, ControllerDO.class);
        controllerDO.setId(null);

        return MybatisExampleUtils.update(controllerDO, controllerDOMapper, example -> {
            example.createCriteria().andIdEqualTo(controllerId).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    public boolean removeController(long operateUserId, Long controllerId) {
        ControllerDO controllerDO = new ControllerDO();
        controllerDO.setIsDeleted(true);
        return MybatisExampleUtils.update(controllerDO, controllerDOMapper, controllerDOExample -> {
            controllerDOExample.createCriteria().andIdEqualTo(controllerId).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    public Page<ControllerDTO> queryControllers(long operateUserId, Long applicationId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(ControllerDTO.class, searchAO.getPage(), searchAO.getPageSize(), controllerDOMapper, controllerDOExample -> {
            controllerDOExample.createCriteria().
                    andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).
                    andUserIdEqualTo(operateUserId).
                    andApplicationIdEqualTo(applicationId);
        });
    }

    @Override
    public List<ControllerDTO> queryControllersByControllerId(long operateUserId, Long controllerId) {
        ControllerDO controllerDO = MybatisExampleUtils.query(controllerDOMapper, controllerDOExample -> {
            controllerDOExample.createCriteria().andIdEqualTo(controllerId).andUserIdEqualTo(operateUserId);
        });

        if (null == controllerDO || null == controllerDO.getApplicationId()) {
            return null;
        }

        return MybatisExampleUtils.queryList(ControllerDTO.class, controllerDOMapper, controllerDOExample -> {
            controllerDOExample.createCriteria().andApplicationIdEqualTo(controllerDO.getApplicationId()).andUserIdEqualTo(operateUserId);
        });
    }
}
