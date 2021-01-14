package xyz.leo.lego.assemble.platform.common.service;

public interface CacheService {
    <T> T get(String key);
    <T> boolean set(String key, T value, int second);
    boolean del(String key);
}