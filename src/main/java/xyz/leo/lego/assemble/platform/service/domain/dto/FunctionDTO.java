package xyz.leo.lego.assemble.platform.service.domain.dto;

import lombok.Data;
import xyz.leo.lego.assemble.platform.dao.base.BaseDO;
import xyz.leo.lego.assemble.platform.service.domain.base.BaseDTO;

import java.util.Map;

/**
 * Created by Lego Generator
 */
@Data
public class FunctionDTO extends BaseDTO {
    /**
     * 名称
     */
    private String name;

    /**
     * 函数方法(POST/GET)
     */
    private String method;

    /**
     * 函数参数信息
     */
    private String param;

    /**
     * 函数出参别名
     */
    private String outputAlias;

    /**
     * 函数入参demo
     */
    private String inputDemo;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 请求链接
     */
    private String url;
}