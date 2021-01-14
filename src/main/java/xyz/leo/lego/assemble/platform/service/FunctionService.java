package xyz.leo.lego.assemble.platform.service;

import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FunctionBindDTO;
import xyz.leo.lego.assemble.platform.service.domain.dto.FunctionDTO;

import java.util.List;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2019/11/12 7:09 PM
 */
public interface FunctionService {
    /**
     * 分页查询函数列表
     * 
     * @param operateUserId
     * @param searchAO
     * @return
     */
    Page<FunctionDTO> queryTemplates(long operateUserId, SearchAO searchAO);

    /**
     * 写入函数信息
     * 
     * @param operateUserId
     * @param functionDTO
     * @return
     */
    boolean insert(Long operateUserId, FunctionDTO functionDTO);

    /**
     * 移除函数信息
     * 
     * @param operateUserId
     * @param functionId
     * @return
     */
    boolean remove(Long operateUserId, Long functionId);

    /**
     * 移除函数信息
     * @param operateUserId
     * @param bindId
     * @param type
     * @return
     */
    boolean removeByBindIdAndType(Long operateUserId, Long bindId, String type);

    /**
     * 绑定函数信息
     * @param operateUserId
     * @param bindId
     * @param type
     * @param functionDTO
     * @return
     */
    boolean bindByBindIdAndType(Long operateUserId, Long bindId, String type, FunctionDTO functionDTO);

    /**
     * 函数信息
     * @param bindId
     * @param type
     * @return
     */
    List<FunctionDTO> queryByBindIdAndType(Long bindId, String type);

    /**
     * 查询函数信息
     * 
     * @param functionIds
     * @return
     */
    List<FunctionDTO> query(List<Long> functionIds);

    /**
     * 绑定函数信息
     * 
     * @param operateUserId
     * @param functionBindDTO
     * @return
     */
    boolean bind(Long operateUserId, FunctionBindDTO functionBindDTO);
}
