package xyz.leo.lego.assemble.platform.dao.domain;

import java.util.Date;
import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;

/**
 * Created by Lego Generator
 */
@Data
public class FunctionBindDO extends BaseDO {

    /**
     * 关联类型
     */
    private String mappingType;

    /**
     * 被关联的id
     */
    private Long mappingId;

    /**
     * 函数id
     */
    private Long functionId;
}