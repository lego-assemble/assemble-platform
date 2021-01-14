package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.FloorTemplateDO;
import xyz.leo.lego.assemble.platform.dao.domain.FloorTemplateDOExample;

/**
 * Created by Lego Generator
 */
public interface FloorTemplateDOMapper extends BaseMapper<FloorTemplateDO, FloorTemplateDOExample> {
    long countByExample(FloorTemplateDOExample example);

    int deleteByExample(FloorTemplateDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FloorTemplateDO record);

    int insertSelective(FloorTemplateDO record);

    List<FloorTemplateDO> selectByExample(FloorTemplateDOExample example);

    FloorTemplateDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FloorTemplateDO record, @Param("example") FloorTemplateDOExample example);

    int updateByExample(@Param("record") FloorTemplateDO record, @Param("example") FloorTemplateDOExample example);

    int updateByPrimaryKeySelective(FloorTemplateDO record);

    int updateByPrimaryKey(FloorTemplateDO record);

    @Override
    default Class<? extends FloorTemplateDOExample> getExampleClass() {
        return FloorTemplateDOExample.class;
    }
}