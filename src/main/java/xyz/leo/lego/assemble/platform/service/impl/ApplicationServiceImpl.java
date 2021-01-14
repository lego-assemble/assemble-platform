package xyz.leo.lego.assemble.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.dao.domain.ApplicationDO;
import xyz.leo.lego.assemble.platform.dao.mapper.ApplicationDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.ApplicationService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.ApplicationDTO;

/**
 * @author xuyangze
 * @date 2019/12/2 11:09 AM
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationDOMapper applicationDOMapper;

    @Override
    public boolean createApplication(long operateUserId, ApplicationDTO applicationDTO) {
        if (null != applicationDTO.getId()) {
            return false;
        }

        ApplicationDO applicationDO = BeanUtils.copy(applicationDTO, ApplicationDO.class);
        applicationDO.setUserId(operateUserId);
        return MybatisExampleUtils.insert(applicationDO, applicationDOMapper);
    }

    @Override
    public boolean updateApplication(long operateUserId, ApplicationDTO applicationDTO) {
        if (null == applicationDTO.getId()) {
            return false;
        }

        Long applicationId = applicationDTO.getId();
        ApplicationDO applicationDO = BeanUtils.copy(applicationDTO, ApplicationDO.class);
        applicationDO.setId(null);
        return MybatisExampleUtils.update(applicationDO, applicationDOMapper, applicationDOExample -> {
            applicationDOExample.createCriteria().andIdEqualTo(applicationId).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    public boolean removeApplication(long operateUserId, Long applicationId) {
        ApplicationDO applicationDO = new ApplicationDO();
        applicationDO.setIsDeleted(true);
        return MybatisExampleUtils.update(applicationDO, applicationDOMapper, applicationDOExample -> {
            applicationDOExample.createCriteria().andIdEqualTo(applicationId).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    public Page<ApplicationDTO> queryApplications(long operateUserId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(ApplicationDTO.class, searchAO.getPage(), searchAO.getPageSize(), applicationDOMapper, applicationDOExample -> {
            applicationDOExample.createCriteria().andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).andUserIdEqualTo(operateUserId);
        });
    }
}
