package com.rigel.concurrent.printab;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huming on 2019-09-12.
 */
@Service
@Slf4j
public class PrintABAutomicService implements IPrintAB {
    private static volatile int count = 0;
    private static final int MAX_PRINT_NUM = 100;

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
