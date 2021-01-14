package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ApplicationDTO;

/**
 * @author xuyangze
 * @date 2019/12/2 11:09 AM
 */
public interface ApplicationService {
    /**
     * 创建
     * @param operateUserId
     * @param applicationDTO
     * @return
     */
    boolean createApplication(long operateUserId, ApplicationDTO applicationDTO);

    /**
     * 更新
     * @param operateUserId
     * @param applicationDTO
     * @return
     */
    boolean updateApplication(long operateUserId, ApplicationDTO applicationDTO);

    /**
     * 删除
     * @param operateUserId
     * @param applicationId
     * @return
     */
    boolean removeApplication(long operateUserId, Long applicationId);

    /**
     * 分页查询页面信息
     * @param operateUserId
     * @param searchAO
     * @return
     */
    Page<ApplicationDTO> queryApplications(long operateUserId, SearchAO searchAO);
}
