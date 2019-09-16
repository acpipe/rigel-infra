package com.rigel.concurrent.printab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author huming on 2019-09-12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class PrintABAtomicTest {

    @Autowired
    private PrintABAtomic printABAtomic;

    @Autowired
    private PrintABVolatile printABVolatile;

    @Test
    public void printABAtomic() {
        printABAtomic.printAB();
    }

    @Test
    public void setPrintABVolatile() {
        printABVolatile.printAB();
    }
}