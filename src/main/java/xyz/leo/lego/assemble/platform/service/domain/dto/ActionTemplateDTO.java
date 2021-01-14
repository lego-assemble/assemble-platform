package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

import java.util.Map;

/**
 * Created by Lego Generator
 */
@Data
public class ActionTemplateDTO extends BaseDTO {
    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 类型
     */
    private String type;

    /**
     * 事件参数信息
     */
    private Map<String, Object> param;
}