package xyz.leo.lego.assemble.platform.dao.enums;

import java.util.Objects;

/**
 * @author xuyangze
 * @date 2019/11/20 10:38 AM
 */
public enum FunctionMethodEnum {
    UNKONW("unknow"),
    POST("post"),
    GET("get"),
    INNER("inner"),;

    private String method;

    FunctionMethodEnum(String type) {
        this.method = type;
    }

    public String getType() {
        return method;
    }

    public static FunctionMethodEnum transform(String method) {
        for (FunctionMethodEnum functionMethodEnum : FunctionMethodEnum.values()) {
            if (Objects.equals(method, functionMethodEnum.method)) {
                return functionMethodEnum;
            }
        }

        return UNKONW;
    }
}