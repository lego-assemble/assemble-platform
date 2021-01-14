package xyz.leo.lego.assemble.platform.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.leo.lego.assemble.platform.common.util.RestResultUtils;
import xyz.leo.lego.assemble.platform.common.util.rest.BizException;
import xyz.leo.lego.assemble.platform.common.util.rest.ResponseCodeEnum;
import xyz.leo.lego.assemble.platform.common.util.rest.result.RestResult;

/**
 * @author xuyangze
 * @date 2019/3/14 10:57 AM
 */
@Slf4j
@ControllerAdvice
public class BizExceptionHandlerResolver {

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public RestResult handler(Exception e) {
        ResponseCodeEnum responseCodeEnum = ResponseCodeEnum.SYSTEM_ERROR;
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            responseCodeEnum = bizException.getResponseCodeEnum();
            log.error("handler bizException: {}, msg: {}", responseCodeEnum.getCode(), responseCodeEnum.getMsg());
        } else {
            log.error("handler exception", e);
        }

        return RestResultUtils.fail(responseCodeEnum);
    }
}
