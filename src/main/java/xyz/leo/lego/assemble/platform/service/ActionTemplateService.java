package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ActionTemplateDTO;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/12 7:09 PM
 */
public interface ActionTemplateService {
    /**
     * 分页查询模版信息
     * @param operateUserId
     * @param searchAO
     * @return
     */
    Page<ActionTemplateDTO> queryActionTemplates(Long operateUserId, SearchAO searchAO);

    /**
     * 写入楼层信息
     * @param operateUserId
     * @param actionTemplateDTO
     * @return
     */
    boolean insert(Long operateUserId, ActionTemplateDTO actionTemplateDTO);

    /**
     * 移除楼层信息
     * @param operateUserId
     * @param actionTemplateId
     * @return
     */
    boolean remove(Long operateUserId, Long actionTemplateId);

    /**
     * 写入楼层信息
     * @param operateUserId
     * @param actionTemplateDTO
     * @return
     */
    boolean update(Long operateUserId, ActionTemplateDTO actionTemplateDTO);

    /**
     * 查询楼层信息
     * @param actionTemplateIds
     * @return
     */
    List<ActionTemplateDTO> query(List<Long> actionTemplateIds);
}
