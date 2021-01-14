package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActivityDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorDTO;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/12 7:09 PM
 */
public interface ActivityService {
    /**
     * 分页查询页面信息
     * @param operateUserId
     * @param searchAO
     * @return
     */
    Page<ActivityDTO> queryActivities(long operateUserId, SearchAO searchAO);

    /**
     * 创建活动页面
     * @param operateUserId
     * @param activityDTO
     * @return
     */
    long upsertActivity(long operateUserId, ActivityDTO activityDTO);

    /**
     * 更新活动楼层信息
     * @param operateUserId
     * @param activityId
     * @param floorDTOS
     * @return
     */
    boolean saveActivityFloors(long operateUserId, Long activityId, List<FloorDTO> floorDTOS);

    /**
     * 查询详情信息
     * @param activityId
     * @return
     */
    ActivityDTO queryDetail(Long activityId);

    /**
     * 查询基本信息
     * @param operateUserId
     * @param activityId
     * @return
     */
    ActivityDTO queryBasic(long operateUserId, Long activityId);
}
