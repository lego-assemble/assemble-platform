package xyz.leo.lego.assemble.platform.common.request.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class HttpThreadPool implements RejectedExecutionHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpThreadPool.class);
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            30,      // 核心线程池大小
            200,     // 最大线程池大小
            10 * 60,      // 线程池中超过corePoolSize数目的空闲线程最大存活时间
            TimeUnit.SECONDS,   // keepAliveTime时间单位
            new LinkedBlockingQueue(50), // 阻塞任务队列
            Executors.defaultThreadFactory(), // 新建线程工厂
            new HttpThreadPool()// 当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
    );

    public static Future submit(Callable callable) {
        return threadPoolExecutor.submit(callable);
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        LOGGER.error("HttpThreadPool rejectedExecution");
        r.run();
    }
}