package com.rigel.concurrent.speedcontrol;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

/**
 * @author huming on 2019-09-12.
 */

@Data
public class Statistic {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private volatile long startTimeStamp;

    public Statistic(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public int incr() {
        return atomicInteger.incrementAndGet();
    }

    public void clean() {
        atomicInteger.set(0);
        startTimeStamp = System.currentTimeMillis();
    }
}
