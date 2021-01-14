package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.ActionTemplateDO;
import xyz.leo.lego.assemble.platform.dao.domain.ActionTemplateDOExample;

/**
 * Created by Lego Generator
 */
public interface ActionTemplateDOMapper extends BaseMapper<ActionTemplateDO, ActionTemplateDOExample> {
    long countByExample(ActionTemplateDOExample example);

    int deleteByExample(ActionTemplateDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ActionTemplateDO record);

    int insertSelective(ActionTemplateDO record);

    List<ActionTemplateDO> selectByExample(ActionTemplateDOExample example);

    ActionTemplateDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ActionTemplateDO record, @Param("example") ActionTemplateDOExample example);

    int updateByExample(@Param("record") ActionTemplateDO record, @Param("example") ActionTemplateDOExample example);

    int updateByPrimaryKeySelective(ActionTemplateDO record);

    int updateByPrimaryKey(ActionTemplateDO record);

    @Override
    default Class<? extends ActionTemplateDOExample> getExampleClass() {
        return ActionTemplateDOExample.class;
    }
}