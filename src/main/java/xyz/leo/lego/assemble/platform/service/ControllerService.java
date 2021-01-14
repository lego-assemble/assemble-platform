package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActivityDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ControllerDTO;

import java.util.List;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2019/12/2 10:38 AM
 */
public interface ControllerService {
    /**
     * 创建
     * @param operateUserId
     * @param controllerDTO
     * @return
     */
    boolean createController(long operateUserId, ControllerDTO controllerDTO);

    /**
     * 更新
     * @param operateUserId
     * @param controllerDTO
     * @return
     */
    boolean updateController(long operateUserId, ControllerDTO controllerDTO);

    /**
     * 删除
     * @param operateUserId
     * @param controllerId
     * @return
     */
    boolean removeController(long operateUserId, Long controllerId);

    /**
     * 分页查询页面信息
     * @param operateUserId
     * @param applicationId
     * @param searchAO
     * @return
     */
    Page<ControllerDTO> queryControllers(long operateUserId, Long applicationId, SearchAO searchAO);

    /**
     * 通过controllerId查询页面列表
     * @param operateUserId
     * @param controllerId
     * @return
     */
    List<ControllerDTO> queryControllersByControllerId(long operateUserId, Long controllerId);
}
