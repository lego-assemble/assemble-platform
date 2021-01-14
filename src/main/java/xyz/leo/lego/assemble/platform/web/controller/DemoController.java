package xyz.leo.lego.assemble.platform.web.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.leo.lego.assemble.platform.common.util.Page;
import xyz.leo.lego.assemble.platform.common.util.RestResultUtils;
import xyz.leo.lego.assemble.platform.common.util.rest.result.RestResult;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author xuyangze
 * @date 2019/12/10 5:03 PM
 */
@RestController
@RequestMapping("api/demo")
public class DemoController {
    @GetMapping("title")
    public RestResult<String> title(@RequestParam("name") Integer name) {
        return RestResultUtils.success("Hello World _ " + name);
    }

    @GetMapping("list")
    public RestResult<List<Map<String, Object>>> list() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (int index = 0; index < 10; index ++) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("index", index);

            list.add(data);
        }

        return RestResultUtils.success(list);
    }

    @GetMapping("page")
    public RestResult<Page<Map<String, Object>>> page(@RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "20") int size) {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (int index = 0; index < 10; index ++) {
            Map<String, Object> data = Maps.newHashMap();
            data.put("index", index);

            list.add(data);
        }

        Page<Map<String, Object>> dataPage = new Page<>();
        dataPage.setItems(list);
        dataPage.setCurrent(page);
        dataPage.setHasMore(page < 3);
        return RestResultUtils.success(dataPage);
    }
}