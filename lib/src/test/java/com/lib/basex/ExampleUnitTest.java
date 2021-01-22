package com.lib.basex;

import com.lib.basex.thread.LThreadService;
import com.lib.basex.utils.Logger;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws InterruptedException {
//        assertEquals(4, 2 + 2);
        System.out.println("12312312312");
        for (int i = 0; i < 100; i++) {
            final int m = i;
            System.out.println("" + m);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println(m + "" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {

                    }
                }
            };
            LThreadService.runOnThread(runnable);
        }
        new CountDownLatch(100).await();
//        Thread.sleep(1000*120);
//        new CountDownLatch(1).await();
    }
}