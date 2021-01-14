package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.ActivityDO;
import xyz.leo.lego.assemble.platform.dao.domain.ActivityDOExample;

/**
 * Created by Lego Generator
 */
public interface ActivityDOMapper extends BaseMapper<ActivityDO, ActivityDOExample> {
    long countByExample(ActivityDOExample example);

    int deleteByExample(ActivityDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ActivityDO record);

    int insertSelective(ActivityDO record);

    List<ActivityDO> selectByExample(ActivityDOExample example);

    ActivityDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ActivityDO record, @Param("example") ActivityDOExample example);

    int updateByExample(@Param("record") ActivityDO record, @Param("example") ActivityDOExample example);

    int updateByPrimaryKeySelective(ActivityDO record);

    int updateByPrimaryKey(ActivityDO record);

    @Override
    default Class<? extends ActivityDOExample> getExampleClass() {
        return ActivityDOExample.class;
    }
}