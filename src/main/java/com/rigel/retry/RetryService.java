package com.rigel.retry;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huming on 2019-08-16.
 */
@Service
@Slf4j
public class RetryService {

    @Autowired
    private RetryComponent retryComponent;

    public void remitWithRetry(RetryParam retryParam) throws RetryException {
        Retryer<Boolean> retryer = RetryerBuilder
                .<Boolean>newBuilder()
                //返回false也需要重试
                .retryIfResult(e -> Objects.equals(e, false))
                //重调策略
                .withWaitStrategy(WaitStrategies.fixedWait(50, TimeUnit.MILLISECONDS))
                //尝试次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .build();

        try {
            retryer.call(() -> retryComponent.remit(retryParam));
        } catch (ExecutionException e) {
            log.error("重试方法执行错误");
        } catch (RetryException e) {
            throw e;
        }
    }
}
