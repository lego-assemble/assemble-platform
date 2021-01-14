package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.FunctionDO;
import xyz.leo.lego.assemble.platform.dao.domain.FunctionDOExample;

/**
 * Created by Lego Generator
 */
public interface FunctionDOMapper extends BaseMapper<FunctionDO, FunctionDOExample> {
    long countByExample(FunctionDOExample example);

    int deleteByExample(FunctionDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(FunctionDO record);

    int insertSelective(FunctionDO record);

    List<FunctionDO> selectByExample(FunctionDOExample example);

    FunctionDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") FunctionDO record, @Param("example") FunctionDOExample example);

    int updateByExample(@Param("record") FunctionDO record, @Param("example") FunctionDOExample example);

    int updateByPrimaryKeySelective(FunctionDO record);

    int updateByPrimaryKey(FunctionDO record);

    @Override
    default Class<? extends FunctionDOExample> getExampleClass() {
        return FunctionDOExample.class;
    }
}