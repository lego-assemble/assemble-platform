package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

import java.util.Map;

/**
 * Created by Lego Generator
 */
@Data
public class LayoutTemplateDTO extends BaseDTO {

    /**
     * 名称
     */
    private String name;

    /**
     * 参数信息
     */
    private Map<String, Object> param;

    /**
     * 模版code
     */
    private String code;
}