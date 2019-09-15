package com.rigel.concurrent.speedcontrol;

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
    private static final int PROCESS_TIME_MS = 10;
    private Integer value;

    private Statistic statistic;

    @Override
    public void run() {
        try {
            Thread.sleep(PROCESS_TIME_MS);
        } catch (Exception ignored) {
            log.info("ignored", ignored);
        }
        // log.info("process.value:{}.", value);
        int cnt = statistic.incr();
        // 每100个请求统计一次.
        if (cnt % 1000 == 0) {
            int qps = (int) (statistic.getAtomicInteger().intValue() * 1000L / (System.currentTimeMillis() - statistic.getStartTimeStamp()));
            log.info("qps:{}/s", qps);
            statistic.clean();
            statistic.setStartTimeStamp(System.currentTimeMillis());
        }
    }
}
