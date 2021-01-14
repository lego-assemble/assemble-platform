package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

/**
 * Created by Lego Generator
 */
@Data
public class ApplicationDTO extends BaseDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 所属用户的id
     */
    private Long userId;

    /**
     * 应用类型
     */
    private String type;
}