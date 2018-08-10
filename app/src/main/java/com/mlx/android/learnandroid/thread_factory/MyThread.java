package com.mlx.android.learnandroid.thread_factory;

import java.util.Date;

/**
 * 描述：自定义Thread
 * 作者: mlx
 * 创建时间： 2018/7/31
 */
public class MyThread extends Thread {
    private Date creationDate;
    private Date startDate;
    private Date finishDate;

    public MyThread(Runnable target, String name) {
        super(target, name);
        setCreationDate();
    }

    public void setCreationDate() {
        this.creationDate = new Date();
    }

    @Override
    public void run() {
        setStartDate();
        super.run();
        setFinishDate();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(getName());
        buffer.append(": ");
        buffer.append(" Creation Date: ");
        buffer.append(creationDate);
        buffer.append(" : Running time: ");
        buffer.append(getExecutionTime());
        buffer.append(" Milliseconds.");
        return buffer.toString();
    }

    public long getExecutionTime() {
        return finishDate.getTime() - startDate.getTime();
    }

    public void setStartDate() {
        this.startDate = new Date();
    }

    public void setFinishDate() {
        this.finishDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }
}
