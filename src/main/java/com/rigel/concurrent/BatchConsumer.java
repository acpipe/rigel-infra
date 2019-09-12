package com.rigel.concurrent;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huming on 2019-09-12.
 * 解决问题: 消费队列只能单线程, 但是消费要加速处理不阻塞, 充分利用性能, 同时速度也要是可以控制的.
 * 例如: 打款性能优化.
 */
@Service
@Slf4j
public class BatchConsumer {
    private static final int CORE_THREAD = 30;
    private static final int MAX_THREAD_COUNT = 10;
    private static final int KEEP_ALIVE_TIMEOUT = 100;
    private static final int QUEUE_SIZE = 10;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_THREAD,
            MAX_THREAD_COUNT, KEEP_ALIVE_TIMEOUT, SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE), new ThreadFactoryBuilder()
            .setNameFormat("BatchConsumer")
            .build());

    public void consume(List<Integer> streamList) {
        streamList.forEach(e -> {
            executor.submit(new ProcessStreamTask(e));
        });
    }

    @Data
    @AllArgsConstructor
    private static final class ProcessStreamTask implements Runnable {
        private Integer value;

        @Override
        public void run() {
            StopWatch stopWatch = new StopWatch();
            try {
                Thread.sleep(300);
            } catch (Exception ignored) {
                log.info("ignored", ignored);
            }
            stopWatch.stop();
            log.info("process.value:{}, cost:{}ms", value, stopWatch.getTotalTimeMillis());
        }
    }
}
