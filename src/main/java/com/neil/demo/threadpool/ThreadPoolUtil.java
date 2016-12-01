package com.neil.demo.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * ThreadPool单例，ThreadPoolUtil单例
 *
 * @author gooooooo
 */
public class ThreadPoolUtil {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(40, 60, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1000000));

    public static ThreadPoolUtil threadPoolUtil = new ThreadPoolUtil();

    public static ThreadPoolUtil getInstance() {
        return threadPoolUtil;
    }

    public <V> void submitTask(Task<V> task) {
        Future<V> future = threadPoolExecutor.submit(task);
        //return future;
    }

}
