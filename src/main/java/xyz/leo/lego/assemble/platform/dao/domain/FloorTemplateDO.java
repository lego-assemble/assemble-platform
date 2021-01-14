package xyz.leo.lego.assemble.platform.dao.domain;

import java.util.Date;
import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;

/**
 * Created by Lego Generator
 */
@Data
public class FloorTemplateDO extends BaseDO {
    /**
     * 名称
     */
    private String name;

    /**
     * 参数信息
     */
    private String param;

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