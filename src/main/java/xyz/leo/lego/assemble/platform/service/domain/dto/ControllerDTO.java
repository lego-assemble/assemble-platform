package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

import java.util.Map;

/**
 * Created by Lego Generator
 */
@Data
public class ControllerDTO extends BaseDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 所属用户的id
     */
    private Long userId;

    /**
     * 参数信息
     */
    private Map<String, Object> param;

    /**
     * 排序值
     */
    private String sortOrder;

    /**
     * 模版code
     */
    private String code;

    /**
     * 应用id
     */
    private Long applicationId;
}