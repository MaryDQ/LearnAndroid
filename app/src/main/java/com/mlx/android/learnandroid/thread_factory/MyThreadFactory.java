package com.mlx.android.learnandroid.thread_factory;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {
    private int counter;

    private String prefix;

    public MyThreadFactory(String prefix) {
        this.counter = 1;
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        MyThread myThread=new MyThread(runnable,prefix+"-"+counter);
        counter++;
        return myThread;
    }
}
