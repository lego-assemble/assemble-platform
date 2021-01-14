package xyz.leo.lego.assemble.platform.common.service.impl;

import xyz.leo.lego.assemble.platform.common.service.LockerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author xuyangze
 * @date 2018/9/6 下午3:32
 */
@Service("localLockerService")
public class LocalLockerServiceImpl implements LockerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(LockerService.class);

    private static final int CORE_LOCKER_SIZE = 100;
    private static final int CLEAN_LOCKER_DISTANCE = 1000 * 60; // 超过一分钟空闲的就清理

    private Map<String, Locker> key2LockerMap = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean lock(String key, long timeout) {
        readLock();
        boolean result = false;
        Locker lock = key2LockerMap.get(key);
        if (null == lock) {
            // 获取写锁，然后创建锁对象
            readUnLock();

            // 升级写锁
            writeLock();

            // 并发下出现多次进入的情况
            // 表明并发来创建锁，但是已经被提前获取到了，直接返回获取失败，不管此时锁是否是锁定的状态
            lock = key2LockerMap.get(key);

            if (null == lock) {
                lock = new Locker(System.currentTimeMillis(), timeout);
                key2LockerMap.put(key, lock);
                result = true;
            }

            writeUnLock();
        } else {
            // 尝试再加锁
            result = lock.lock(timeout);
            readUnLock();
        }

        return result;
    }

    @Override
    public boolean lock(String key, long timeout, int retry, long retryInterval) {
        if (lock(key, timeout)) {
            return true;
        }

        // 重试
        for (int index = 0; index < retry; index ++ ) {
            try {
                Thread.sleep(retryInterval);
            } catch (Exception e) {
                // do nothing
            }

            if (lock(key, timeout)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void unLock(String key) {
        readLock();
        Locker lock = key2LockerMap.get(key);
        if (null == lock || !lock.isLocked()) {
            readUnLock();
            return;
        }

        // 锁升级
        readUnLock();
        writeLock();

        // 获取到写锁后再次判断，并发下出现这种情况
        lock = key2LockerMap.get(key);
        if (null != lock && lock.isLocked()) {
            key2LockerMap.remove(key);
        }

        // 清理掉不使用了的对象，防止内存泄露
        if (key2LockerMap.size() > CORE_LOCKER_SIZE) {
            LOGGER.info("clean unused locker before: " + key2LockerMap.size());

            List<String> needRemoveKeys = new ArrayList<>();
            for (Map.Entry<String, Locker> key2Locker : key2LockerMap.entrySet()) {
                if (key2Locker.getValue().distance() > CLEAN_LOCKER_DISTANCE) {
                    needRemoveKeys.add(key2Locker.getKey());
                }
            }

            for (String needRemove : needRemoveKeys) {
                key2LockerMap.remove(needRemove);
            }

            LOGGER.info("clean unused locker after: " + key2LockerMap.size());
        }

        writeUnLock();
    }

    private void readLock() {
        readWriteLock.readLock().lock();
    }

    private void readUnLock() {
        readWriteLock.readLock().unlock();
    }

    private void writeLock() {
        readWriteLock.writeLock().lock();
    }

    private void writeUnLock() {
        readWriteLock.writeLock().unlock();
    }

    class Locker {
        private volatile long start;
        private volatile long interval;

        public Locker(long start, long interval) {
            this.start = start;
            this.interval = interval;
        }

        private long distance() {
            return System.currentTimeMillis() - start - interval;
        }

        public boolean isLocked() {
            return start + interval >= System.currentTimeMillis();
        }

        public synchronized boolean lock(long lockTime) {
            if (isLocked()) {
                return false;
            }

            interval = lockTime;
            start = System.currentTimeMillis();
            return true;
        }
    }

    private static void test(LockerService lockerService, int value) {
        long start = System.currentTimeMillis();
        int index = value;
        while (true) {
            if (lockerService.lock("hello_world_" + index, 1000, 3, 200)) {
                System.out.println(System.currentTimeMillis() - start);
                start = System.currentTimeMillis();
                if (index % 2 == 0) {
                    lockerService.unLock("hello_world_" + index);
                }

                index ++;
            }
        }
    }

    public static void main(String []args) {
        LockerService lockerService = new LocalLockerServiceImpl();

        new Thread(() -> test(lockerService, 1)).start();
        new Thread(() -> test(lockerService, 2)).start();
        new Thread(() -> test(lockerService, 3)).start();
        new Thread(() -> test(lockerService, 4)).start();
    }
}
