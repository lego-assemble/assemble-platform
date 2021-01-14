package xyz.leo.lego.assemble.platform.common.util.rest.result;

import lombok.Data;

/**
 * @author xuyangze
 * @date 2019/5/10 5:45 PM
 */
@Data
public class RestResult<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 返回结果
     */
    private T content;

    /**
     * 错误信息
     */
    private String msg;
}
