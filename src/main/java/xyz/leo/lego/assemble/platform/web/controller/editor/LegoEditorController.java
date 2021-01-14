package xyz.leo.lego.assemble.platform.web.controller.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.RestResultUtils;
import xyz.leo.lego.assemble.platform.common.util.rest.result.RestResult;
import xyz.leo.lego.assemble.platform.service.*;
import xyz.leo.lego.assemble.platform.service.domain.ao.SearchAO;
import xyz.leo.lego.assemble.platform.service.domain.dto.*;

import java.util.List;

/**
 * @author xuyangze
 * @date 2019/11/20 2:12 PM
 */
@RequestMapping("api")
@RestController
public class LegoEditorController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private FunctionService functionService;

    @Autowired
    private ControllerService controllerService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private ActionTemplateService actionTemplateService;

    @GetMapping("activity/page")
    public RestResult<Page<ActivityDTO>> pageActivities(@RequestParam(value = "page", defaultValue = "1") int page,
                                                              @RequestParam(value = "size", defaultValue = "20") int size,
                                                              @RequestParam("keyword") String keyword) {
        return RestResultUtils.success(activityService.queryActivities(1L, transform(page, size, keyword)));
    }

    @PostMapping("activity/upsert")
    public RestResult<Long> createActivity(@RequestBody ActivityDTO activityDTO) {
        return RestResultUtils.success(activityService.upsertActivity(1L, activityDTO));
    }

    @PostMapping("activity/save")
    public RestResult<Boolean> updateActivityFloors(@RequestBody ActivityDTO activityDTO) {
        return RestResultUtils.success(activityService.saveActivityFloors(1L, activityDTO.getId(), activityDTO.getFloors()));
    }

    @PostMapping("controller/create")
    public RestResult<Boolean> createController(@RequestBody ControllerDTO controllerDTO) {
        return RestResultUtils.success(controllerService.createController(1L, controllerDTO));
    }

    @PostMapping("controller/save")
    public RestResult<Boolean> updateController(@RequestBody ControllerDTO controllerDTO) {
        return RestResultUtils.success(controllerService.updateController(1L, controllerDTO));
    }

    @PostMapping("controller/remove")
    public RestResult<Boolean> removeController(@RequestBody ControllerDTO controllerDTO) {
        return RestResultUtils.success(controllerService.removeController(1L, controllerDTO.getId()));
    }

    @GetMapping("controller/page")
    public RestResult<Page<ControllerDTO>> pageControllers(@RequestParam(value = "page", defaultValue = "1") int page,
                                                            @RequestParam(value = "size", defaultValue = "20") int size,
                                                            @RequestParam("keyword") String keyword,
                                                           @RequestParam("appId") Long appId) {
        return RestResultUtils.success(controllerService.queryControllers(1L, appId, transform(page, size, keyword)));
    }

    @GetMapping("controller/list")
    public RestResult<List<ControllerDTO>> listControllers(@RequestParam(value = "controllerId") Long controllerId) {
        return RestResultUtils.success(controllerService.queryControllersByControllerId(1L, controllerId));
    }

    @PostMapping("application/create")
    public RestResult<Boolean> createApplication(@RequestBody ApplicationDTO applicationDTO) {
        return RestResultUtils.success(applicationService.createApplication(1L, applicationDTO));
    }

    @PostMapping("application/save")
    public RestResult<Boolean> updateApplication(@RequestBody ApplicationDTO applicationDTO) {
        return RestResultUtils.success(applicationService.updateApplication(1L, applicationDTO));
    }

    @PostMapping("application/remove")
    public RestResult<Boolean> removeApplication(@RequestBody ApplicationDTO applicationDTO) {
        return RestResultUtils.success(applicationService.removeApplication(1L, applicationDTO.getId()));
    }

    @GetMapping("application/page")
    public RestResult<Page<ApplicationDTO>> pageApplications(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "size", defaultValue = "20") int size,
                                                           @RequestParam("keyword") String keyword) {
        return RestResultUtils.success(applicationService.queryApplications(1L, transform(page, size, keyword)));
    }

    @GetMapping("activity/detail")
    public RestResult<ActivityDTO> queryActivityDetail(@RequestParam("activityId") Long activityId) {
        return RestResultUtils.success(activityService.queryDetail(activityId));
    }

    @GetMapping("floor/page")
    public RestResult<Page<FloorDTO>> pageFloorTemplates(@RequestParam(value = "page", defaultValue = "1") int page,
                                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                                         @RequestParam("keyword") String keyword) {
        return RestResultUtils.success(floorService.queryTemplates(1L, transform(page, size, keyword)));
    }

    @PostMapping("floor/create")
    public RestResult<Boolean> createFloor(@RequestBody FloorDTO floorDTO) {
        return RestResultUtils.success(floorService.insert(1L, floorDTO));
    }

    @PostMapping("floor/remove")
    public RestResult<Boolean> removeFloor(@RequestBody FloorDTO floorDTO) {
        return RestResultUtils.success(floorService.remove(1L, floorDTO.getId()));
    }

    @PostMapping("floor/save")
    public RestResult<Boolean> saveFloor(@RequestBody FloorDTO floorDTO) {
        return RestResultUtils.success(floorService.update(1L, floorDTO));
    }

    @GetMapping("function/page")
    public RestResult<Page<FunctionDTO>> pageFunctionTemplates(@RequestParam(value = "page", defaultValue = "1") int page,
                                                               @RequestParam(value = "size", defaultValue = "20") int size,
                                                               @RequestParam("keyword") String keyword) {
        return RestResultUtils.success(functionService.queryTemplates(1L, transform(page, size, keyword)));
    }

    @PostMapping("function/create")
    public RestResult<Boolean> createFloor(@RequestBody FunctionDTO functionDTO) {
        return RestResultUtils.success(functionService.insert(1L, functionDTO));
    }

    @PostMapping("layout/create")
    public RestResult<Boolean> createLayout(@RequestBody LayoutDTO layoutDTO) {
        return RestResultUtils.success(layoutService.createLayout(1L, layoutDTO));
    }

    @PostMapping("layout/save")
    public RestResult<Boolean> updateLayout(@RequestBody LayoutDTO layoutDTO) {
        return RestResultUtils.success(layoutService.updateLayout(1L, layoutDTO));
    }

    @GetMapping("layout/query")
    public RestResult<LayoutDTO> pageControllers(@RequestParam("controllerId") Long controllerId) {
        return RestResultUtils.success(layoutService.queryLayout(1L, controllerId));
    }

    private SearchAO transform(int page, int size, String keyword) {
        SearchAO searchAO = new SearchAO();
        searchAO.setKeyword(keyword);
        searchAO.setPage(page);
        searchAO.setPageSize(size);

        return searchAO;
    }
}
