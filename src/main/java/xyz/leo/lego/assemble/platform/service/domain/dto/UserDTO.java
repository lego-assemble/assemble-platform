package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

/**
 * Created by Lego Generator
 */
@Data
public class UserDTO extends BaseDTO {
    /**
     * 名称
     */
    private String nick;
}