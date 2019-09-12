package com.rigel.concurrent;

import org.springframework.util.StopWatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huming on 2019-09-12.
 */
@Data
@Slf4j
@AllArgsConstructor
public class ProcessStreamTask implements Runnable {
    private Integer value;

    private Statistic statistic;

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (Exception ignored) {
            log.info("ignored", ignored);
        }
        // log.info("process.value:{}.", value);
        int cnt = statistic.incr();
        if (cnt % 10 == 0) {
            int qps = (int) (statistic.getAtomicInteger().intValue() * 1000L / (System.currentTimeMillis() - statistic.getStart()));
            log.info("qps:{}", qps);
        }
    }
}
