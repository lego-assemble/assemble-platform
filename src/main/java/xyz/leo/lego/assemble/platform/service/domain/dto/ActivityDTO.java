package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Lego Generator
 */
@Data
public class ActivityDTO extends BaseDTO {
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
    private Map<String, Object> params;

    /**
     * 布局id
     */
    private Long layoutId;

    /**
     * 楼层信息
     */
    private List<FloorDTO> floors;

    /**
     * 函数信息
     */
    private List<FunctionDTO> functions;
}