package xyz.leo.lego.assemble.platform.dao.domain;

import java.util.Date;
import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;

/**
 * Created by Lego Generator
 */
@Data
public class ActivityDO extends BaseDO {
    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 激活状态
     */
    private Boolean isActive;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 参数信息
     */
    private String params;

    /**
     * 布局id
     */
    private Long layoutId;
}