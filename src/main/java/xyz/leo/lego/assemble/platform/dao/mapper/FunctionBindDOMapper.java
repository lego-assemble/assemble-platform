package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.FunctionBindDO;
import xyz.leo.lego.assemble.platform.dao.domain.FunctionBindDOExample;

/**
 * Created by Lego Generator
 */
public interface FunctionBindDOMapper extends BaseMapper<FunctionBindDO, FunctionBindDOExample> {
    long countByExample(FunctionBindDOExample example);

    int deleteByExample(FunctionBindDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FunctionBindDO record);

    int insertSelective(FunctionBindDO record);

    List<FunctionBindDO> selectByExample(FunctionBindDOExample example);

    FunctionBindDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FunctionBindDO record, @Param("example") FunctionBindDOExample example);

    int updateByExample(@Param("record") FunctionBindDO record, @Param("example") FunctionBindDOExample example);

    int updateByPrimaryKeySelective(FunctionBindDO record);

    int updateByPrimaryKey(FunctionBindDO record);

    @Override
    default Class<? extends FunctionBindDOExample> getExampleClass() {
        return FunctionBindDOExample.class;
    }
}