package com.rigel.concurrent.printab;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huming on 2019-09-16.
 */
@Service
@Slf4j
public class PrintABVolatile implements IPrintAB {
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    private static final int MAX_PRINT_NUM = 100;
    private static volatile int count = 0;

    @Override
    public void printAB() {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread(() -> {
            while (count < MAX_PRINT_NUM) {
                if (count % 2 == 0) {
                    log.info("num:" + count);
                    count++;
                }
            }
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            while (count < MAX_PRINT_NUM) {
                if (count % 2 == 1) {
                    log.info("num:" + count);
                    count++;
                }
            }
            countDownLatch.countDown();
        }).start();

        try {
            countDownLatch.await();
        } catch (Exception e) {
        }
    }
}
