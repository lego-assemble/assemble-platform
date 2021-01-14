package xyz.leo.lego.assemble.platform.web.controller.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.RestResultUtils;
import xyz.leo.lego.assemble.platform.common.util.rest.result.RestResult;
import xyz.leo.lego.assemble.platform.service.*;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.*;

/**
 * @author xuyangze
 * @date 2019/11/20 2:12 PM
 */
@RequestMapping("api/template")
@RestController
public class LegoTemplateController {
    @Autowired
    private FloorTemplateService floorTemplateService;

    @Autowired
    private LayoutTemplateService layoutTemplateService;

    @Autowired
    private ActionTemplateService actionTemplateService;

    @GetMapping("floor/page")
    public RestResult<Page<FloorTemplateDTO>> pageFloorTemplates(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                                         @RequestParam("keyword") String keyword) {
        return RestResultUtils.success(floorTemplateService.queryTemplates(1L, transform(page, size, keyword)));
    }

    @PostMapping("floor/create")
    public RestResult<Boolean> createFloorTemplate(@RequestBody FloorTemplateDTO floorTemplateDTO) {
        return RestResultUtils.success(floorTemplateService.insert(1L, floorTemplateDTO));
    }

    @PostMapping("floor/remove")
    public RestResult<Boolean> removeFloorTemplate(@RequestBody FloorTemplateDTO floorTemplateDTO) {
        return RestResultUtils.success(floorTemplateService.remove(1L, floorTemplateDTO.getId()));
    }

    @PostMapping("floor/save")
    public RestResult<Boolean> saveFloorTemplate(@RequestBody FloorTemplateDTO floorTemplateDTO) {
        return RestResultUtils.success(floorTemplateService.update(1L, floorTemplateDTO));
    }

    @GetMapping("layout/page")
    public RestResult<Page<LayoutTemplateDTO>> pageLayoutTemplate(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                  @RequestParam(value = "size", defaultValue = "20") int size,
                                                                  @RequestParam("keyword") String keyword) {
        return RestResultUtils.success(layoutTemplateService.queryLayoutTemplates(1L, transform(page, size, keyword)));
    }

    @PostMapping("layout/create")
    public RestResult<Boolean> createLayoutTemplate(@RequestBody LayoutTemplateDTO layoutTemplateDTO) {
        return RestResultUtils.success(layoutTemplateService.createLayoutTemplate(1L, layoutTemplateDTO));
    }

    @PostMapping("layout/remove")
    public RestResult<Boolean> removeLayoutTemplate(@RequestBody LayoutTemplateDTO layoutTemplateDTO) {
        return RestResultUtils.success(layoutTemplateService.removeLayoutTemplate(1L, layoutTemplateDTO.getId()));
    }

    @PostMapping("layout/save")
    public RestResult<Boolean> updateLayoutTemplate(@RequestBody LayoutTemplateDTO layoutTemplateDTO) {
        return RestResultUtils.success(layoutTemplateService.updateLayoutTemplate(1L, layoutTemplateDTO));
    }

    @GetMapping("action/page")
    public RestResult<Page<ActionTemplateDTO>> pageActionTemplates(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "20") int size,
                                                            @RequestParam("keyword") String keyword) {
        return RestResultUtils.success(actionTemplateService.queryActionTemplates(1L, transform(page, size, keyword)));
    }

    @PostMapping("action/create")
    public RestResult<Boolean> createActionTemplate(@RequestBody ActionTemplateDTO actionTemplateDTO) {
        return RestResultUtils.success(actionTemplateService.insert(1L, actionTemplateDTO));
    }

    @PostMapping("action/remove")
    public RestResult<Boolean> removeActionTemplate(@RequestBody ActionTemplateDTO actionTemplateDTO) {
        return RestResultUtils.success(actionTemplateService.remove(1L, actionTemplateDTO.getId()));
    }

    @PostMapping("action/save")
    public RestResult<Boolean> saveActionTemplate(@RequestBody ActionTemplateDTO actionTemplateDTO) {
        return RestResultUtils.success(actionTemplateService.update(1L, actionTemplateDTO));
    }

    private SearchAO transform(int page, int size, String keyword) {
        SearchAO searchAO = new SearchAO();
        searchAO.setKeyword(keyword);
        searchAO.setPage(page);
        searchAO.setPageSize(size);

        return searchAO;
    }
}
