package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutDO;
import xyz.leo.lego.assemble.platform.dao.domain.LayoutDOExample;

/**
 * Created by Lego Generator
 */
public interface LayoutDOMapper extends BaseMapper<LayoutDO, LayoutDOExample> {
    long countByExample(LayoutDOExample example);

    int deleteByExample(LayoutDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LayoutDO record);

    int insertSelective(LayoutDO record);

    List<LayoutDO> selectByExample(LayoutDOExample example);

    LayoutDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LayoutDO record, @Param("example") LayoutDOExample example);

    int updateByExample(@Param("record") LayoutDO record, @Param("example") LayoutDOExample example);

    int updateByPrimaryKeySelective(LayoutDO record);

    int updateByPrimaryKey(LayoutDO record);

    @Override
    default Class<? extends LayoutDOExample> getExampleClass() {
        return LayoutDOExample.class;
    }
}