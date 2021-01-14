package xyz.leo.lego.assemble.platform.common.aop.process;

import xyz.leo.lego.assemble.platform.common.annotations.Sync;
import xyz.leo.lego.assemble.platform.common.annotations.SyncKey;
import xyz.leo.lego.assemble.platform.common.context.SyncContext;
import xyz.leo.lego.assemble.platform.common.enums.LockType;
import xyz.leo.lego.assemble.platform.common.service.LockerService;
import xyz.leo.lego.assemble.platform.common.util.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author xuyangze
 * @date 2018/9/10 下午8:26
 */
@Component("syncAspectProcessor")
public class SyncAspectProcessor implements AspectProcessor<Sync> {

    @Autowired
    @Qualifier("localLockerService")
    private LockerService localLockerService;

    @Autowired
    @Qualifier("remoteLockerService")
    private LockerService remoteLockerService;

    private AspectUtils.Resolver resolver = (annotation) -> {
        if (annotation instanceof SyncKey) {
            return ((SyncKey) annotation).value();
        }

        return null;
    };

    @Override
    public Object process(ProceedingJoinPoint pjp, AspectProcessorInvoker aspectProcessorInvoker, Sync sync) throws Throwable {
        Object result = null;
        String lockKey = resolveSyncKey(pjp, sync);
        boolean isLocked = false;
        boolean needSkip = SyncContext.isSkipSync();
        try {
            if (!needSkip) {
                if (lock(lockKey, sync)) {
                    isLocked = true;
                    result = aspectProcessorInvoker.invoke(pjp);
                }

                return result;
            }

            return pjp.proceed();
        } finally {
            if (isLocked && !needSkip) {
                unLock(lockKey, sync.type());
            }
        }
    }

    private String resolveSyncKey(ProceedingJoinPoint pjp, Sync sync) {
        return AspectUtils.resolve(pjp, sync.value(), resolver);
    }

    private boolean lock(String key, Sync sync) {
        switch (sync.type()) {
            case LOCAL: {
                return localLockerService.lock(key, sync.expire(), sync.tryTime(), sync.tryWait());
            }
            case REMOTE:
            default:{
                return remoteLockerService.lock(key, sync.expire(), sync.tryTime(), sync.tryWait());
            }
        }
    }

    private void unLock(String key, LockType lockType) {
        switch (lockType) {
            case LOCAL: {
                localLockerService.unLock(key);
                break;
            }
            case REMOTE:
            default:{
                remoteLockerService.unLock(key);
                break;
            }
        }
    }
}
