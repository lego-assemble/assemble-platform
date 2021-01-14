package xyz.leo.lego.assemble.platform.common.service.impl;

import xyz.leo.lego.assemble.platform.common.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "remoteCacheService")
public class RemoteCacheServiceImpl implements CacheService {

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public <T> boolean set(String key, T value, int second) {
        return true;
    }

    @Override
    public boolean del(String key) {
        return true;
    }
}
