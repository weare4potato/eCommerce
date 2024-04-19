package com.potato.ecommerce.global.config.redisson;

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

    // Redis 데이터 초기화
    public void flushAll() {
        redissonClient.getKeys().flushall();
    }

    @Around("@annotation(com.potato.ecommerce.global.config.redisson.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
            signature.getParameterNames(),
            joinPoint.getArgs(),
            distributedLock.key());

        RLock rLock = redissonClient.getLock(key);

//        try {
//            System.out.println("key: " + key);
//            boolean available = rLock.tryLock(distributedLock.waitTime(),
//                distributedLock.leaseTime(), distributedLock.timeUnit());
//
//            if (!available) {
//                throw new IllegalStateException("DistributedLock lock failed");
//            }
//            return aopForTransaction.proceed(joinPoint);
//
//        } catch (InterruptedException e) {
//            // TODO: replace error statement
//            log.error("DistributedLock lock interrupted");
//            throw new InterruptedException(e.getMessage());
//        } finally {
//            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
//                rLock.unlock();
//            }
//        }

        // 예외 처리 및 락 획득을 재시도하는 로직
        int retryCount = 0;

        while (retryCount < MAX_RETRY_COUNT) {
            try {
                boolean available = rLock.tryLock(distributedLock.waitTime(),
                    distributedLock.leaseTime(), distributedLock.timeUnit());

                if (available) {
                    return aopForTransaction.proceed(joinPoint);
                } else {
                    retryCount++;
                    Thread.sleep(RETRY_DELAY);
                }
            } catch (InterruptedException e) {
                log.error("DistributedLock lock interrupted");
                throw new InterruptedException(e.getMessage());
            } finally {
                if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                    // Todo: flushAll 은 현재 test 환경에서만 사용
                    flushAll();
                    rLock.unlock();
                }
            }
        }

        throw new IllegalStateException("DistributedLock lock failed after retries");
    }
}