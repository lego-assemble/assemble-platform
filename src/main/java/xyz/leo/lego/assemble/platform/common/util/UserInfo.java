package xyz.leo.lego.assemble.platform.common.util;

import lombok.Data;

/**
 * @author xuyangze
 * @date 2019/11/12 5:55 PM
 */
@Data
public class UserInfo {
    /**
     * 用户id
     */
    private long userId;

    /**
     * 用户昵称
     */
    private String nick;
}
