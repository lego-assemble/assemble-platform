package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

/**
 * Created by Lego Generator
 */
@Data
public class FunctionBindDTO extends BaseDTO {
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