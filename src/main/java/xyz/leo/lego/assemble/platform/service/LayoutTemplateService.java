package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.LayoutTemplateDTO;

/**
 * @author xuyangze
 * @date 2019/12/4 4:49 PM
 */
public interface LayoutTemplateService {
    /**
     * 创建
     * @param operateUserId
     * @param layoutTemplateDTO
     * @return
     */
    boolean createLayoutTemplate(long operateUserId, LayoutTemplateDTO layoutTemplateDTO);

    /**
     * 更新
     * @param operateUserId
     * @param layoutTemplateDTO
     * @return
     */
    boolean updateLayoutTemplate(long operateUserId, LayoutTemplateDTO layoutTemplateDTO);

    /**
     * 删除
     * @param operateUserId
     * @param layoutTemplateId
     * @return
     */
    boolean removeLayoutTemplate(long operateUserId, Long layoutTemplateId);

    /**
     * 查询页面信息
     * @param operateUserId
     * @param searchAO
     * @return
     */
    Page<LayoutTemplateDTO> queryLayoutTemplates(long operateUserId, SearchAO searchAO);
}
