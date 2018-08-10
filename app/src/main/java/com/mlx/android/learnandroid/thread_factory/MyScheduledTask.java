package com.mlx.android.learnandroid.thread_factory;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyScheduledTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {

    private RunnableScheduledFuture<V> task;

    private ScheduledThreadPoolExecutor executor;

    private long period;

    private long startDate;

    public MyScheduledTask(@NonNull Runnable runnable, V result, RunnableScheduledFuture<V> task, ScheduledThreadPoolExecutor executor) {
        super(runnable, result);
        this.task = task;
        this.executor = executor;
    }



    @Override
    public boolean isPeriodic() {
        return task.isPeriodic();
    }

    @Override
    public long getDelay(@NonNull TimeUnit timeUnit) {

        if (!isPeriodic()) {
            return task.getDelay(timeUnit);
        }else {
            if (startDate==0) {
                return task.getDelay(timeUnit);
            } else {
                Date now =new Date();
                long delay=startDate-now.getTime();
                return timeUnit.convert(delay,TimeUnit.MILLISECONDS);
            }
        }
    }

    @Override
    public int compareTo(@NonNull Delayed delayed) {
        return task.compareTo(delayed);
    }

    @Override
    public void run() {
        if (isPeriodic()&&(!executor.isShutdown())) {
            Date now=new Date();
            startDate=now.getTime()+period;
            executor.getQueue().add(this);
        }
        System.out.printf("Pre-MyScheduledTask: %s\n",new Date());
        System.out.printf("MyScheduledTask: Is Periodic:%s\n",isPeriodic());
        super.runAndReset();
        System.out.printf("Post-MyScheduledTask: %s\n",new Date());
    }

    public void  setPeriod(long period){
        this.period=period;
    }

}
