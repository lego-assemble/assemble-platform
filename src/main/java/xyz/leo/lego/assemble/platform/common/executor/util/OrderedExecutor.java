package xyz.leo.lego.assemble.platform.common.executor.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author xuyangze
 * @date 2019/11/4 7:56 PM
 */
@Slf4j
public class OrderedExecutor {
    private Map<Integer, List<Runnable>> order2ExecutorMap;

    /**
     * 提交任务
     * @param order
     * @param action
     */
    public void submit(int order, Runnable action) {
        if (null == order2ExecutorMap) {
            order2ExecutorMap = Maps.newHashMap();
        }

        List<Runnable> runnables = order2ExecutorMap.get(order);
        if (null == runnables) {
            runnables = Lists.newArrayList();
            order2ExecutorMap.put(order, runnables);
        }

        runnables.add(action);
    }

    public void executeAndWaitDone() {
        if (MapUtils.isEmpty(order2ExecutorMap)) {
            return;
        }

        List<Integer> orders = Lists.newArrayList(order2ExecutorMap.keySet());
        orders.sort(Comparator.naturalOrder());

        for (Integer order : orders) {
            List<Runnable> runnables = order2ExecutorMap.get(order);
            executeAndWaitDone(runnables);
        }
    }

    private void executeAndWaitDone(List<Runnable> runnables) {
        if (CollectionUtils.isEmpty(runnables)) {
            return;
        }

        if (runnables.size() == 1) {
            runnables.get(0).run();
            return;
        }

        List<Future> futures = Lists.newArrayList();
        for (Runnable runnable : runnables) {
            futures.add(ThreadPoolUtils.submit(runnable));
        }

        for (Future future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                log.error("e", e);
            }
        }
    }
}
