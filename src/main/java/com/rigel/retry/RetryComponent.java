package com.rigel.retry;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huming on 2019-08-16.
 */
@Service
@Slf4j
public class RetryComponent {
    public boolean remit(RetryParam retryParam) {
        if (retryParam.getRemitId() == -1) {
            try {
                log.info("retryParam:{}", retryParam);
                Thread.sleep(500);
            } catch (Exception e) {
                log.error("thread sleep error.", e);
            }
            return false;
        }

        return true;
    }
}
