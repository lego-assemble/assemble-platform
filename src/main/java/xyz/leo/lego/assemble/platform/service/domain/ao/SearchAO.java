package xyz.leo.lego.assemble.platform.service.domain.ao;

import lombok.Data;

/**
 * @author xuyangze
 * @date 2019/11/12 7:45 PM
 */
@Data
public class SearchAO {

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 当前页面
     */
    private Integer page;

    /**
     * 分页大小
     */
    private Integer pageSize;
}
