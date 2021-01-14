package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FloorTemplateDTO;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/12 7:09 PM
 */
public interface FloorTemplateService {
    /**
     * 分页查询模版信息
     * @param operateUserId
     * @param searchAO
     * @return
     */
    Page<FloorTemplateDTO> queryTemplates(Long operateUserId, SearchAO searchAO);

    /**
     * 写入楼层信息
     * @param operateUserId
     * @param floorTemplateDTO
     * @return
     */
    boolean insert(Long operateUserId, FloorTemplateDTO floorTemplateDTO);

    /**
     * 保存楼层信息
     * @param operateUserId
     * @param floorTemplateDTO
     * @return
     */
    boolean update(Long operateUserId, FloorTemplateDTO floorTemplateDTO);

    /**
     * 移除楼层信息
     * @param operateUserId
     * @param floorTemplateId
     * @return
     */
    boolean remove(Long operateUserId, Long floorTemplateId);
}
