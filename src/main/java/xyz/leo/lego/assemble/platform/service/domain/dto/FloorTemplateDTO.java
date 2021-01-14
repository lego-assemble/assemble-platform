package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

import java.util.Map;

/**
 * Created by Lego Generator
 */
@Data
public class FloorTemplateDTO extends BaseDTO {
    /**
     * 名称
     */
    private String name;

    /**
     * 参数信息
     */
    private Map<String, Object> param;

    /**
     * 活动id
     */
    private Long activityId;

    /**
     * 排序值
     */
    private String sortOrder;

    /**
     * 模版code
     */
    private String code;

    /**
     * 外部引用别名
     */
    private String refAlias;
}