package xyz.leo.lego.assemble.platform.dao.base;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/5/9 8:04 PM
 */
public interface BaseMapper<D, E> {
    /**
     * 统计数量
     * @param example
     * @return
     */
    long countByExample(E example);

    /**
     * 删除数据
     * @param example
     * @return
     */
    int deleteByExample(E example);

    /**
     * 插入数据
     * @param record
     * @return
     */
    int insert(D record);

    /**
     * 插入数据
     * @param record
     * @return
     */
    int insertSelective(D record);

    /**
     * 查询数据
     * @param example
     * @return
     */
    List<D> selectByExample(E example);

    /**
     * 更新数据
     * @param record
     * @param example
     * @return
     */
    int updateByExampleSelective(D record, E example);

    /**
     * 更新数据
     * @param record
     * @param example
     * @return
     */
    int updateByExample(D record, E example);

    /**
     * 获取Example
     * @return
     */
    Class<? extends E> getExampleClass();
}
