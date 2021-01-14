package xyz.leo.lego.assemble.platform.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.dao.domain.ActivityDO;
import xyz.leo.lego.assemble.platform.dao.enums.ActionTypeEnum;
import xyz.leo.lego.assemble.platform.dao.enums.FloorTypeEnum;
import xyz.leo.lego.assemble.platform.dao.mapper.ActivityDOMapper;
import xyz.leo.lego.assemble.platform.dao.mapper.FloorDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.ActivityService;
import xyz.leo.lego.assemble.platform.service.FloorService;
import xyz.leo.lego.assemble.platform.service.FunctionService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActivityDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FunctionDTO;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/12 7:09 PM
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDOMapper activityDOMapper;

    @Autowired
    private FloorDOMapper floorDOMapper;

    @Autowired
    private FloorService floorService;

    @Autowired
    private FunctionService functionService;

    @Override
    public Page<ActivityDTO> queryActivities(long operateUserId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(ActivityDTO.class, searchAO.getPage(), searchAO.getPageSize(), activityDOMapper, activityDOExample -> {
            activityDOExample.createCriteria().andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    @Transactional
    public long upsertActivity(long operateUserId, ActivityDTO activityDTO) {
        ActivityDO activityDO = BeanUtils.copy(activityDTO, ActivityDO.class);
        activityDO.setUserId(operateUserId);
        activityDO.setIsActive(true);
        boolean success = false;
        if (activityDO.getId() != null) {
            success = MybatisExampleUtils.update(activityDO, activityDOMapper, activityDOExample -> {
                activityDOExample.createCriteria().andIdEqualTo(activityDO.getId());
            });
        } else {
            success = MybatisExampleUtils.insert(activityDO, activityDOMapper);
        }

        if (success && CollectionUtils.isNotEmpty(activityDTO.getFloors())) {
            saveActivityFloors(operateUserId, activityDO.getId(), activityDTO.getFloors());
        }

        if (success && CollectionUtils.isNotEmpty(activityDTO.getFunctions())) {
            saveActivityFunctions(operateUserId, activityDO.getId(), activityDTO.getFunctions());
        }

        return null == activityDO.getId() ? 0 : activityDO.getId();
    }

    @Transactional
    @Override
    public boolean saveActivityFloors(long operateUserId, Long activityId, List<FloorDTO> floorDTOS) {
        // 保存页面信息，删除历史楼层，楼层事件进行复制，然后存储到关联表中
        floorService.removeByActivityId(operateUserId, activityId);
        if (CollectionUtils.isEmpty(floorDTOS)) {
            return true;
        }

        floorDTOS.forEach(floorDTO -> {
            floorDTO.setActivityId(activityId);
            floorService.insert(operateUserId, floorDTO);
        });

        return true;
    }

    @Override
    public ActivityDTO queryDetail(Long activityId) {
        ActivityDTO activityDTO = MybatisExampleUtils.query(ActivityDTO.class, activityDOMapper, activityDOExample -> {
            activityDOExample.createCriteria().
                    andIdEqualTo(activityId).
                    andIsDeletedEqualTo(false);
        });

        if (null == activityDTO) {
            return null;
        }

        List<FloorDTO> floorDTOS = floorService.queryByActivityId(activityId);
        activityDTO.setFloors(floorDTOS);
        activityDTO.setFunctions(functionService.queryByBindIdAndType(activityId, "activity"));

        return activityDTO;
    }

    @Override
    public ActivityDTO queryBasic(long operateUserId, Long activityId) {
        return MybatisExampleUtils.query(ActivityDTO.class, activityDOMapper, activityDOExample -> {
            activityDOExample.createCriteria().
                    andIdEqualTo(activityId).
                    andIsDeletedEqualTo(false).
                    andUserIdEqualTo(operateUserId);
        });
    }

    public boolean saveActivityFunctions(long operateUserId, Long activityId, List<FunctionDTO> functionDTOS) {
        // 保存页面信息，删除历史楼层，楼层事件进行复制，然后存储到关联表中
        functionService.removeByBindIdAndType(operateUserId, activityId, "activity");
        if (CollectionUtils.isEmpty(functionDTOS)) {
            return true;
        }

        functionDTOS.forEach(functionDTO -> {
            functionService.bindByBindIdAndType(operateUserId, activityId, "activity", functionDTO);
        });

        return true;
    }
}
