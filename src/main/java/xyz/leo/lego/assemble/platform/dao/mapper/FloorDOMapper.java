package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.FloorDO;
import xyz.leo.lego.assemble.platform.dao.domain.FloorDOExample;

/**
 * Created by Lego Generator
 */
public interface FloorDOMapper extends BaseMapper<FloorDO, FloorDOExample> {
    long countByExample(FloorDOExample example);

    int deleteByExample(FloorDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FloorDO record);

    int insertSelective(FloorDO record);

    List<FloorDO> selectByExample(FloorDOExample example);

    FloorDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FloorDO record, @Param("example") FloorDOExample example);

    int updateByExample(@Param("record") FloorDO record, @Param("example") FloorDOExample example);

    int updateByPrimaryKeySelective(FloorDO record);

    int updateByPrimaryKey(FloorDO record);

    @Override
    default Class<? extends FloorDOExample> getExampleClass() {
        return FloorDOExample.class;
    }
}