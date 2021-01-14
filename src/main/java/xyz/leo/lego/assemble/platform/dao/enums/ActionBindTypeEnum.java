package xyz.leo.lego.assemble.platform.dao.enums;

import java.util.Objects;

/**
 * @author xuyangze
 * @date 2019/11/20 10:37 AM
 */
public enum  ActionBindTypeEnum {
    UNKONW("unknow"),
    FLOOR("floor"),
    ACTIVITY("activity"),;

    private String type;

    ActionBindTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static ActionBindTypeEnum transform(String type) {
        for (ActionBindTypeEnum actionBindTypeEnum : ActionBindTypeEnum.values()) {
            if (Objects.equals(type, actionBindTypeEnum.type)) {
                return actionBindTypeEnum;
            }
        }

        return UNKONW;
    }
}
