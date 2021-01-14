package xyz.leo.lego.assemble.platform.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/5/10 4:09 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {
    /**
     * 数据
     */
    private List<T> items;

    /**
     * 是否还有更多的数据
     */
    private boolean hasMore;

    /**
     * 当前所在的位置
     */
    private int current;
}
