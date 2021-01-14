package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.service.domain.dto.LayoutDTO;

/**
 * @author xuyangze
 * @date 2019/12/4 4:49 PM
 */
public interface LayoutService {
    /**
     * 创建
     * @param operateUserId
     * @param layoutDTO
     * @return
     */
    boolean createLayout(long operateUserId, LayoutDTO layoutDTO);

    /**
     * 更新
     * @param operateUserId
     * @param layoutDTO
     * @return
     */
    boolean updateLayout(long operateUserId, LayoutDTO layoutDTO);

    /**
     * 删除
     * @param operateUserId
     * @param controllerId
     * @return
     */
    boolean removeLayout(long operateUserId, Long controllerId);

    /**
     * 查询页面信息
     * @param operateUserId
     * @param controllerId
     * @return
     */
    LayoutDTO queryLayout(long operateUserId, Long controllerId);

    /**
     * 查询页面信息
     * @param operateUserId
     * @param layoutId
     * @return
     */
    LayoutDTO queryByLayoutId(long operateUserId, Long layoutId);
}
