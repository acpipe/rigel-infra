package com.rigel.retry;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author huming on 2019-08-16.
 */
@AllArgsConstructor
@Data
public class RetryParam {
    // 以打款重试为例, remitId保持幂等.
    private int remitId;
    private int remitAmount;
}
