package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutTemplateDO;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutTemplateDOExample;

/**
 * Created by Lego Generator
 */
public interface LayoutTemplateDOMapper extends BaseMapper<LayoutTemplateDO, LayoutTemplateDOExample> {
    long countByExample(LayoutTemplateDOExample example);

    int deleteByExample(LayoutTemplateDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LayoutTemplateDO record);

    int insertSelective(LayoutTemplateDO record);

    List<LayoutTemplateDO> selectByExample(LayoutTemplateDOExample example);

    LayoutTemplateDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LayoutTemplateDO record, @Param("example") LayoutTemplateDOExample example);

    int updateByExample(@Param("record") LayoutTemplateDO record, @Param("example") LayoutTemplateDOExample example);

    int updateByPrimaryKeySelective(LayoutTemplateDO record);

    int updateByPrimaryKey(LayoutTemplateDO record);

    @Override
    default Class<? extends LayoutTemplateDOExample> getExampleClass() {
        return LayoutTemplateDOExample.class;
    }
}