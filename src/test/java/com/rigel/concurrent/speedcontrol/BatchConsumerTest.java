package com.rigel.concurrent.speedcontrol;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rigel.concurrent.speedcontrol.BatchConsumerService;

/**
 * @author huming on 2019-09-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BatchConsumerTest {

    @Autowired
    private BatchConsumerService batchConsumerService;

    @Test
    public void consume() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 8000; i++) {
            list.add(i);
        }

        batchConsumerService.consume(list);
    }
}