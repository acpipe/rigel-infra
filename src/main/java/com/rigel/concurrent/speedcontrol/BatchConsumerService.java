package com.rigel.concurrent.speedcontrol;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huming on 2019-09-12.
 * 解决问题: 消费队列只能单线程, 但是消费要加速处理不阻塞, 充分利用性能, 同时候要保证内存不会爆掉。
 * 同时速度也要是可以控制的, 加最大线程数.
 * 例如: 打款性能优化.
 * 限制: 对消息顺序没要求.
 */
@Service
@Slf4j
public class BatchConsumerService {
    private static final int CORE_THREAD = 30;
    private static final int MAX_THREAD_COUNT = 100;
    private static final int KEEP_ALIVE_TIMEOUT = 100;
    private static final int QUEUE_SIZE = 10;
    private ThreadPoolExecutor executor;

    @PostConstruct
    public void init() {
        executor = new ThreadPoolExecutor(CORE_THREAD,
                MAX_THREAD_COUNT, KEEP_ALIVE_TIMEOUT, SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE), new ThreadFactoryBuilder()
                .setNameFormat("BatchConsumer")
                .build(),
                (r, executor) -> {
                    // log.info("blocking queue");
                    // 如果线程池队列满了, 这里用put方法阻塞住.
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                    }
                });
    }

    public void consume(List<Integer> streamList) {
        Statistic statistic = new Statistic(System.currentTimeMillis());
        streamList.forEach(e -> {
            ProcessStreamTask task = new ProcessStreamTask(e, statistic);
            executor.submit(task);
        });
    }
}
