package xyz.leo.lego.assemble.platform.dao.enums;

import java.util.Objects;

/**
 * @author xuyangze
 * @date 2019/11/20 10:38 AM
 */
public enum ControllerTypeEnum {
    UNKONW("unknow"),
    TEMPLATE("template"),
    INSTANCE("instance"),;

    private String type;

    ControllerTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ControllerTypeEnum transform(String type) {
        for (ControllerTypeEnum actionTypeEnum : ControllerTypeEnum.values()) {
            if (Objects.equals(type, actionTypeEnum.type)) {
                return actionTypeEnum;
            }
        }

        return UNKONW;
    }
}