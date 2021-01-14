package xyz.leo.lego.assemble.platform.dao.enums;

import java.util.Objects;

/**
 * @author xuyangze
 * @date 2019/11/20 10:34 AM
 */
public enum FunctionBindTypeEnum {
    UNKONW("unknow"),
    FLOOR("floor"),
    ACTIVITY("activity"),;

    private String type;

    FunctionBindTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static FunctionBindTypeEnum transform(String type) {
        for (FunctionBindTypeEnum functionBindTypeEnum : FunctionBindTypeEnum.values()) {
            if (Objects.equals(type, functionBindTypeEnum.type)) {
                return functionBindTypeEnum;
            }
        }

        return UNKONW;
    }
}
