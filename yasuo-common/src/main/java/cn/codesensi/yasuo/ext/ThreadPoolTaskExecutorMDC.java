package cn.codesensi.yasuo.ext;

import cn.codesensi.yasuo.util.ThreadPoolMDCUtil;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 传递traceId的线程执行器
 */
public class ThreadPoolTaskExecutorMDC extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadPoolMDCUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadPoolMDCUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadPoolMDCUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

}
