package xyz.leo.lego.assemble.platform.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import xyz.leo.lego.assemble.platform.dao.base.BaseMapper;
import xyz.leo.lego.assemble.platform.dao.domain.ApplicationDO;
import xyz.leo.lego.assemble.platform.dao.domain.ApplicationDOExample;

/**
 * Created by Lego Generator
 */
public interface ApplicationDOMapper extends BaseMapper<ApplicationDO, ApplicationDOExample> {
    long countByExample(ApplicationDOExample example);

    int deleteByExample(ApplicationDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ApplicationDO record);

    int insertSelective(ApplicationDO record);

    List<ApplicationDO> selectByExample(ApplicationDOExample example);

    ApplicationDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ApplicationDO record, @Param("example") ApplicationDOExample example);

    int updateByExample(@Param("record") ApplicationDO record, @Param("example") ApplicationDOExample example);

    int updateByPrimaryKeySelective(ApplicationDO record);

    int updateByPrimaryKey(ApplicationDO record);

    @Override
    default Class<? extends ApplicationDOExample> getExampleClass() {
        return ApplicationDOExample.class;
    }
}