package com.potato.ecommerce.global.config.redis.lock;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {

    private static final String REDISSON_LOCK_PREFIX = "LOCK: ";
    private static final long RETRY_DELAY = 3L; // 재시도 사이의 대기 시간
    private static final long MAX_RETRY_COUNT = 5L; // 최대 재시도 횟수


    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Around("@annotation(com.potato.ecommerce.global.config.redis.lock.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
            signature.getParameterNames(),
            joinPoint.getArgs(),
            distributedLock.key());

        RLock rLock = redissonClient.getLock(key);

        // 예외 처리 및 락 획득을 재시도하는 로직
        int retryCount = 0;

        while (retryCount < MAX_RETRY_COUNT) {
            try {
                boolean available = rLock.tryLock(distributedLock.waitTime(),
                    distributedLock.leaseTime(), distributedLock.timeUnit());

                if (available) {
                    System.out.println("Lock acquired with key: " + key);
                    try {
                        return aopForTransaction.proceed(joinPoint);
                    } finally {
                        if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                            System.out.println("Lock released with key: " + key);
                            rLock.unlock();
                        }
                    }
                } else {
                    System.out.println("!Lock acquisition failed. Retrying...");
                    retryCount++;
                    Thread.sleep(RETRY_DELAY);
                }
            } catch (InterruptedException e) {
                log.error("DistributedLock lock interrupted");
                System.out.println("DistributedLock lock interrupted");
                throw new InterruptedException(e.getMessage());
            }
        }
        throw new IllegalStateException("DistributedLock lock failed after retries");
    }
}