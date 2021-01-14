package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.ControllerDO;
import xyz.leo.lego.assemble.platform.dao.domain.ControllerDOExample;

/**
 * Created by Lego Generator
 */
public interface ControllerDOMapper extends BaseMapper<ControllerDO, ControllerDOExample> {
    long countByExample(ControllerDOExample example);

    int deleteByExample(ControllerDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ControllerDO record);

    int insertSelective(ControllerDO record);

    List<ControllerDO> selectByExample(ControllerDOExample example);

    ControllerDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ControllerDO record, @Param("example") ControllerDOExample example);

    int updateByExample(@Param("record") ControllerDO record, @Param("example") ControllerDOExample example);

    int updateByPrimaryKeySelective(ControllerDO record);

    int updateByPrimaryKey(ControllerDO record);

    @Override
    default Class<? extends ControllerDOExample> getExampleClass() {
        return ControllerDOExample.class;
    }
}