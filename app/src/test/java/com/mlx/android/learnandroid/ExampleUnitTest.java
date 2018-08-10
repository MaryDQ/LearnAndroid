package com.mlx.android.learnandroid;

import com.mlx.android.learnandroid.thread_factory.MyScheduledThreadPoolExecutor;
import com.mlx.android.learnandroid.thread_factory.MyTask;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void main() throws InterruptedException {
        MyScheduledThreadPoolExecutor executor=new MyScheduledThreadPoolExecutor(2);
        MyTask task=new MyTask();
        System.out.printf("Main: %s\n",new Date());

        executor.schedule(task,1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(3);

        task=new MyTask();
        System.out.printf("Main: %s\n",new Date());

        executor.scheduleAtFixedRate(task,1,3,TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);

        executor.shutdown();

        executor.awaitTermination(1,TimeUnit.DAYS);

        System.out.printf("Main:End of the program.\n");

    }
}