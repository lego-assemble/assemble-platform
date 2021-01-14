package xyz.leo.lego.assemble.platform.common.util;

import xyz.leo.lego.assemble.platform.common.util.rest.ResponseCodeEnum;
import xyz.leo.lego.assemble.platform.common.util.rest.result.RestResult;

/**
 * @author xuyangze
 * @date 2019/5/10 5:47 PM
 */
public class RestResultUtils {

    public static <T> RestResult<T> success(T model) {
        return create(ResponseCodeEnum.SUCCESS, model);
    }

    public static <T> RestResult<T> fail(int code, String msg) {
        return create(code, msg, null);
    }

    public static <T> RestResult<T> fail(ResponseCodeEnum responseCodeEnum) {
        return create(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), null);
    }

    public static <T> RestResult<T> create(ResponseCodeEnum responseCodeEnum, T model) {
        return create(responseCodeEnum.getCode(), responseCodeEnum.getMsg(), model);
    }

    private static <T> RestResult<T> create(int code, String msg, T model) {
        RestResult restResult = new RestResult();
        restResult.setCode(code);
        restResult.setContent(model);
        restResult.setMsg(msg);

        return restResult;
    }
}
