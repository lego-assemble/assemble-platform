package xyz.leo.lego.assemble.platform.common.executor.util;

import java.util.concurrent.*;

public class ThreadPoolUtils implements RejectedExecutionHandler {
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            500,      // 核心线程池大小
            1000,     // 最大线程池大小
            10 * 60,      // 线程池中超过corePoolSize数目的空闲线程最大存活时间
            TimeUnit.SECONDS,   // keepAliveTime时间单位
            new LinkedBlockingQueue(), // 阻塞任务队列
            Executors.defaultThreadFactory(), // 新建线程工厂
            new ThreadPoolUtils()// 当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
    );

    public static Future submit(Runnable runnable) {
        return threadPoolExecutor.submit(runnable);
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        r.run();
    }
}