package com.rigel.buffer;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huming on 2019-11-12.
 */
public class BatchConsumeBlockingQueueTrigger<E> implements BufferTrigger<E> {
    private static final Logger logger = LoggerFactory.getLogger(BatchConsumeBlockingQueueTrigger.class);
    private final BlockingQueue<E> queue;
    private final int batchSize;


    public BatchConsumeBlockingQueueTrigger(BlockingQueue<E> queue, int batchSize) {
        this.queue = queue;
        this.batchSize = batchSize;
    }


    @Override
    public void enqueue(E element) {

    }

    @Override
    public void manuallyDoTrigger() {

    }
}
