package com.ijustyce.fastandroiddev3.baseLib.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangchun on 2017/1/3.
 */

public class ThreadUtils {

    private static ExecutorService service = new ThreadPoolExecutor(0, getCpuNUmber() * 4,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

    public static void execute(Runnable runnable){
        service.execute(runnable);
        int threadCount = ((ThreadPoolExecutor)service).getActiveCount();
        ILog.e("===threadCount===", threadCount);
    }

    private static int getCpuNUmber() {
        int value = CpuUtils.getCoreNumber();
        if (value < 2) value = 2;
        return value;
    }

    public interface ICallBack{
        <T> void onResult(T t);
    }
}