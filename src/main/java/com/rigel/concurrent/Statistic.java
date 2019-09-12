package com.rigel.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author huming on 2019-09-12.
 */

@Data
public class Statistic {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private long start;
    private long end;

    public Statistic(long start) {
        this.start = start;
    }

    public int incr() {
        return atomicInteger.incrementAndGet();
    }

    public void clean() {
        atomicInteger.set(0);
        start = System.currentTimeMillis();
    }
}
