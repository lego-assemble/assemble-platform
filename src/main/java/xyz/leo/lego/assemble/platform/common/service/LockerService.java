package xyz.leo.lego.assemble.platform.common.service;

/**
 * @author xuyangze
 * @date 2018/9/6 下午3:32
 */
public interface LockerService {
    /**
     * 锁住key的资源，超时时间
     * @param key
     * @param timeout
     * @return
     */
    boolean lock(String key, long timeout);

    /**
     * 分布式锁
     * @param key 锁的key
     * @param timeout 锁定的时间
     * @param tryTime 重试次数
     * @param retryInterval 重试时间间隔
     * @return
     */
    boolean lock(String key, long timeout, int tryTime, long retryInterval);

    /**
     * 解锁
     * @param key
     * @return
     */
    void unLock(String key);
}
