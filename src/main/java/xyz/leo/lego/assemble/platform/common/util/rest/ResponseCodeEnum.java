package xyz.leo.lego.assemble.platform.common.util.rest;

/**
 * @author xuyangze
 * @date 2019/5/10 5:48 PM
 */
public enum ResponseCodeEnum {
    SYSTEM_ERROR(-1, "系统异常"),
    SUCCESS(0, "success"),
    EMAIL_SEND_VALIDATE_ERROR(101, "发送校验邮件失败"),
    ACTION_CODE_EXIST(102, "事件编码已存在"),;

    private int code;

    private String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
