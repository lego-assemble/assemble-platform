package xyz.leo.lego.assemble.platform.dao.base;

import lombok.Data;

import java.util.Date;

/**
 * @author xuyangze
 * @date 2019/11/12 3:52 PM
 */
@Data
public class BaseDO {
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
}
