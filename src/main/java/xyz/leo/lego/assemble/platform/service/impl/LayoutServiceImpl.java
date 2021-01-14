package xyz.leo.lego.assemble.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutDO;
import xyz.leo.lego.assemble.platform.dao.mapper.LayoutDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.LayoutService;
import xyz.leo.lego.assemble.platform.service.domain.dto.LayoutDTO;

/**
 * @author xuyangze
 * @date 2019/12/4 4:56 PM
 */
@Service
public class LayoutServiceImpl implements LayoutService {

    @Autowired
    private LayoutDOMapper layoutDOMapper;

    @Override
    public boolean createLayout(long operateUserId, LayoutDTO layoutDTO) {
        removeLayout(operateUserId, layoutDTO.getControllerId());

        LayoutDO layoutDO = BeanUtils.copy(layoutDTO, LayoutDO.class);
        layoutDO.setUserId(operateUserId);
        return MybatisExampleUtils.insert(layoutDO, layoutDOMapper);
    }

    @Override
    public boolean updateLayout(long operateUserId, LayoutDTO layoutDTO) {
        if (null == layoutDTO.getId()) {
            return false;
        }

        Long layoutId = layoutDTO.getId();
        LayoutDO layoutDO = BeanUtils.copy(layoutDTO, LayoutDO.class);
        layoutDO.setId(null);

        return MybatisExampleUtils.update(layoutDO, layoutDOMapper, layoutDOExample -> {
            layoutDOExample.createCriteria().andIdEqualTo(layoutId).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    public boolean removeLayout(long operateUserId, Long controllerId) {
        LayoutDO layoutDO = new LayoutDO();
        layoutDO.setIsDeleted(true);

        return MybatisExampleUtils.update(layoutDO, layoutDOMapper, layoutDOExample -> {
            layoutDOExample.createCriteria().andControllerIdEqualTo(controllerId).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    public LayoutDTO queryLayout(long operateUserId, Long controllerId) {
        return MybatisExampleUtils.query(LayoutDTO.class, layoutDOMapper, layoutDOExample -> {
            layoutDOExample.createCriteria().andControllerIdEqualTo(controllerId).andUserIdEqualTo(operateUserId);
        });
    }

    @Override
    public LayoutDTO queryByLayoutId(long operateUserId, Long layoutId) {
        return MybatisExampleUtils.query(LayoutDTO.class, layoutDOMapper, layoutDOExample -> {
            layoutDOExample.createCriteria().andIdEqualTo(layoutId).andUserIdEqualTo(operateUserId);
        });
    }
}
