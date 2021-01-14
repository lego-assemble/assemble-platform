package xyz.leo.lego.assemble.platform.service.domain.base;

import lombok.Data;

import java.util.Date;

/**
 * @author xuyangze
 * @date 2019/11/12 3:52 PM
 */
@Data
public class BaseDTO {
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
}
