package com.rigel.retry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.rholder.retry.RetryException;

/**
 * @author huming on 2019-08-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class RetryServiceTest {

    @Autowired
    private RetryService retryService;

    @Test
    public void retryCallRpc() throws RetryException {
        RetryParam retryParam = new RetryParam(-1, 1000);
        boolean retryResult = retryService.remitWithRetry(retryParam);
        assertFalse(retryResult);
    }
}