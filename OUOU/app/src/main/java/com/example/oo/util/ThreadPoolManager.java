package com.example.oo.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolManager {
    //双重效验锁
    private volatile static ThreadPoolManager mInstance;
    public static ThreadPoolManager getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolManager();
                }
            }
        }
        return mInstance;
    }

    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime = 1;
    private TimeUnit unit = TimeUnit.HOURS;
    private ThreadPoolExecutor executor;

    private ThreadPoolManager() {
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        //maximumPoolSize需要赋值，否则报错
        maximumPoolSize = corePoolSize;
        executor = new ThreadPoolExecutor(
                //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                corePoolSize,
                //5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                maximumPoolSize,
                //表示的是maximumPoolSize当中等待任务的存活时间
                keepAliveTime,
                unit,
                //缓冲队列，用于存放等待任务，Linked的先进先出
                new LinkedBlockingQueue<Runnable>(),
                //创建线程的工厂
                //  Executors.defaultThreadFactory(),
                new DefaultThreadFactory(Thread.NORM_PRIORITY, "tiaoba-pool-"),
                //用来对超出maximumPoolSize的任务的处理策略
                new ThreadPoolExecutor.AbortPolicy()
        );
    }


    public void execute(Runnable runnable) {
        if (executor == null) {
            //线程池执行者。
            //参1:核心线程数;参2:最大线程数;参3:线程休眠时间;参4:时间单位;参5:线程队列;参6:生产线程的工厂;参7:线程异常处理策略
            executor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    new DefaultThreadFactory(Thread.NORM_PRIORITY, "tiaoba-pool-"),
                    new ThreadPoolExecutor.AbortPolicy());
        }
        if (runnable != null) {
            executor.execute(runnable);
        }
    }

    public void remove(Runnable runnable) {
        if (runnable != null) {
            executor.remove(runnable);
        }
    }

    //创建线程的工厂，设置线程的优先级，group，以及命名
    private static class DefaultThreadFactory implements ThreadFactory {
        //线程池的计数
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        //线程的计数
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        private final ThreadGroup group;
        private final String namePrefix;
        private final int threadPriority;

        DefaultThreadFactory(int threadPriority, String threadNamePrefix) {
            this.threadPriority = threadPriority;
            this.group = Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            t.setPriority(threadPriority);
            return t;
        }
    }

}
