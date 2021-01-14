package xyz.leo.lego.assemble.platform.common.util.rest;

import lombok.Data;

/**
 * @author xuyangze
 * @date 2019/5/13 4:03 PM
 */
@Data
public class BizException extends RuntimeException {

    /**
     * 异常信息
     */
    private ResponseCodeEnum responseCodeEnum;

    public BizException(ResponseCodeEnum responseCodeEnum) {
        super(null, null, false, false);
        this.responseCodeEnum = responseCodeEnum;
    }
}
