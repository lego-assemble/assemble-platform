package xyz.leo.lego.assemble.platform.common.service.impl;

import xyz.leo.lego.assemble.platform.common.service.LockerService;
import org.springframework.stereotype.Service;

/**
 * Created by xuyangze on 2018/7/13.
 */
@Service("remoteLockerService")
public class RemoteLockerServiceImpl implements LockerService {

    @Override
    public boolean lock(String key, long timeout) {
        return true;
    }

    @Override
    public boolean lock(String key, long timeout, int tryTime, long retryInterval) {
        return true;
    }

    @Override
    public void unLock(String key) {

    }
}
