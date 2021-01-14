package xyz.leo.lego.assemble.platform.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.leo.lego.assemble.platform.common.copy.BeanUtils;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.SqlUtils;
import xyz.leo.lego.assemble.platform.dao.domain.FunctionBindDO;
import xyz.leo.lego.assemble.platform.dao.domain.FunctionDO;
import xyz.leo.lego.assemble.platform.dao.domain.FunctionDOExample;
import xyz.leo.lego.assemble.platform.dao.enums.FloorTypeEnum;
import xyz.leo.lego.assemble.platform.dao.mapper.FunctionBindDOMapper;
import xyz.leo.lego.assemble.platform.dao.mapper.FunctionDOMapper;
import xyz.leo.lego.assemble.platform.dao.util.MybatisExampleUtils;
import xyz.leo.lego.assemble.platform.service.FunctionService;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FunctionBindDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FunctionDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuyangze
 * @date 2019/11/12 8:02 PM
 */
@Service
public class FunctionServiceImpl implements FunctionService {

    @Autowired
    private FunctionDOMapper functionDOMapper;

    @Autowired
    private FunctionBindDOMapper functionBindDOMapper;

    @Override
    public Page<FunctionDTO> queryTemplates(long operateUserId, SearchAO searchAO) {
        return MybatisExampleUtils.pageQuery(FunctionDTO.class, searchAO.getPage(), searchAO.getPageSize(), functionDOMapper, functionDOExample -> {
            functionDOExample.createCriteria().
                    andNameLike(SqlUtils.rightConcat(searchAO.getKeyword())).
                    andUserIdIn(Arrays.asList(1L, operateUserId)).
                    andIsDeletedEqualTo(false);
        });
    }

    @Override
    public boolean insert(Long operateUserId, FunctionDTO functionDTO) {
        return insertInner(operateUserId, functionDTO) > 0;
    }

    @Override
    public boolean remove(Long operateUserId, Long functionId) {
        FunctionDO functionDO = new FunctionDO();
        functionDO.setIsDeleted(true);

        FunctionDOExample functionDOExample = new FunctionDOExample();
        functionDOExample.createCriteria().andIdEqualTo(functionId).andUserIdEqualTo(operateUserId);

        return functionDOMapper.updateByExampleSelective(functionDO, functionDOExample) > 0;
    }

    @Override
    public List<FunctionDTO> query(List<Long> functionIds) {
        return MybatisExampleUtils.queryList(FunctionDTO.class, functionDOMapper, functionDOExample -> {
            functionDOExample.createCriteria().andIdIn(functionIds).
                    andIsDeletedEqualTo(false);
        });
    }

    @Override
    public boolean bind(Long operateUserId, FunctionBindDTO functionBindDTO) {
        FunctionBindDO functionBindDO = BeanUtils.copy(functionBindDTO, FunctionBindDO.class);
        return MybatisExampleUtils.insert(functionBindDO, functionBindDOMapper);
    }

    @Transactional
    @Override
    public boolean removeByBindIdAndType(Long operateUserId, Long bindId, String type) {
        List<FunctionBindDO> functionBindDOS = MybatisExampleUtils.queryList(functionBindDOMapper, functionBindDOExample -> {
            functionBindDOExample.createCriteria().
                    andMappingTypeEqualTo(type).
                    andMappingIdEqualTo(bindId);
        });

        FunctionBindDO functionBindDO = new FunctionBindDO();
        functionBindDO.setIsDeleted(true);

        MybatisExampleUtils.update(functionBindDO, functionBindDOMapper, functionBindDOExample -> {
            functionBindDOExample.createCriteria().
                    andMappingTypeEqualTo(type).
                    andMappingIdEqualTo(bindId);
        });

        if (CollectionUtils.isNotEmpty(functionBindDOS)) {
            List<Long> functionIds = functionBindDOS.stream().map(FunctionBindDO::getFunctionId).collect(Collectors.toList());
            FunctionDO functionDO = new FunctionDO();
            functionDO.setIsDeleted(true);

            MybatisExampleUtils.update(functionDO, functionDOMapper, functionDOExample -> {
                functionDOExample.createCriteria().andIdIn(functionIds);
            });
        }

        return true;
    }

    @Transactional
    @Override
    public boolean bindByBindIdAndType(Long operateUserId, Long bindId, String type, FunctionDTO functionDTO) {
        long functionId = insertInner(operateUserId, functionDTO);
        FunctionBindDTO functionBindDTO = new FunctionBindDTO();
        functionBindDTO.setMappingType(type);
        functionBindDTO.setMappingId(bindId);
        functionBindDTO.setFunctionId(functionId);

        return bind(operateUserId, functionBindDTO);
    }

    @Override
    public List<FunctionDTO> queryByBindIdAndType(Long bindId, String type) {
        List<FunctionBindDO> functionBindDOS = MybatisExampleUtils.queryList(functionBindDOMapper, functionBindDOExample -> {
            functionBindDOExample.createCriteria().
                    andMappingTypeEqualTo(type).
                    andMappingIdEqualTo(bindId);
        });

        if (CollectionUtils.isNotEmpty(functionBindDOS)) {
            List<Long> functionIds = functionBindDOS.stream().map(FunctionBindDO::getFunctionId).collect(Collectors.toList());

            return MybatisExampleUtils.queryList(FunctionDTO.class, functionDOMapper, functionDOExample -> {
                functionDOExample.createCriteria().andIdIn(functionIds);
            });
        }

        return Collections.EMPTY_LIST;
    }

    private long insertInner(Long operateUserId, FunctionDTO functionDTO) {
        FunctionDO functionDO = BeanUtils.copy(functionDTO, FunctionDO.class);
        functionDO.setUserId(operateUserId);
        functionDO.setId(null);
        functionDO.setGmtCreate(null);
        functionDO.setGmtModified(null);
        functionDO.setModifier(null);
        functionDO.setCreator(null);

        if (MybatisExampleUtils.insert(functionDO, functionDOMapper)) {
            return functionDO.getId();
        }

        return 0;
    }
}
