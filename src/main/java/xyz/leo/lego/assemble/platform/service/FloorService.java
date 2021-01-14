package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorDTO;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/12 7:09 PM
 */
public interface FloorService {
    /**
     * 分页查询模版信息
     * @param operateUserId
     * @param searchAO
     * @return
     */
    Page<FloorDTO> queryTemplates(Long operateUserId, SearchAO searchAO);

    /**
     * 写入楼层信息
     * @param operateUserId
     * @param floorDTO
     * @return
     */
    boolean insert(Long operateUserId, FloorDTO floorDTO);

    /**
     * 保存楼层信息
     * @param operateUserId
     * @param floorDTO
     * @return
     */
    boolean update(Long operateUserId, FloorDTO floorDTO);

    /**
     * 移除楼层信息
     * @param operateUserId
     * @param floorId
     * @return
     */
    boolean remove(Long operateUserId, Long floorId);

    /**
     * 移除楼层信息
     * @param operateUserId
     * @param activityId
     * @return
     */
    boolean removeByActivityId(Long operateUserId, Long activityId);

    /**
     * 查询楼层信息
     * @param activityId
     * @return
     */
    List<FloorDTO> queryByActivityId(Long activityId);
}
