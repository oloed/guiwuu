package com.guiwuu.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcurrentTestUtils {

    private static final Logger logger = Logger.getLogger(ConcurrentTestUtils.class.getName());

    public static int run(ThreadWrapper[] threads) throws Exception {
        int concurrent = threads.length;
        AtomicInteger success = new AtomicInteger();
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(concurrent);
        long beginTime = System.currentTimeMillis();

        for (ThreadWrapper t : threads) {
            t.setBegin(begin);
            t.setEnd(end);
            t.setSuccess(success);
            t.start();
        }

        begin.countDown();
        end.await();
        long endTime = System.currentTimeMillis();
        logger.log(Level.WARNING, "cost time: {0}ms", (endTime - beginTime));
        return success.get();
    }
}