package xyz.leo.lego.assemble.platform.dao.domain;

import java.util.Date;
import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;

/**
 * Created by Lego Generator
 */
@Data
public class FloorDO extends BaseDO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 创建日期
     */
    private Date gmtCreate;

    /**
     * 修改日期
     */
    private Date gmtModified;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String modifier;

    /**
     * 删除标志
     */
    private Boolean isDeleted;

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

    /**
     * 模版类型
     */
    private String type;
}