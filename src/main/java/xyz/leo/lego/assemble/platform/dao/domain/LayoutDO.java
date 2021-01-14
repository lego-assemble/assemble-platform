package xyz.leo.lego.assemble.platform.dao.domain;

import java.util.Date;
import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;

/**
 * Created by Lego Generator
 */
@Data
public class LayoutDO extends BaseDO {

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
    private String param;

    /**
     * 模版code
     */
    private String code;

    /**
     * 页面id
     */
    private Long controllerId;
}