package cn.codesensi.yasuo.config;

import cn.codesensi.yasuo.constants.ThreadConst;
import cn.codesensi.yasuo.ext.ThreadPoolTaskExecutorMDC;
import cn.codesensi.yasuo.properties.ThreadPoolProperties;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
@RequiredArgsConstructor
public class ThreadPoolConfig {

    private final ThreadPoolProperties threadPoolProperties;

    /**
     * 拒绝策略映射表（key与配置项 thread.pool.rejected-execution-handler 对应）
     */
    private static final Map<String, RejectedExecutionHandler> REJECTED_HANDLER_MAP = Map.of(
            "CallerRunsPolicy", new ThreadPoolExecutor.CallerRunsPolicy(),
            "AbortPolicy", new ThreadPoolExecutor.AbortPolicy(),
            "DiscardPolicy", new ThreadPoolExecutor.DiscardPolicy(),
            "DiscardOldestPolicy", new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    /**
     * 执行异步任务
     */
    @Bean(name = ThreadConst.ASYNC_TASK_EXECUTOR_NAME)
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutorMDC executor = new ThreadPoolTaskExecutorMDC();
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        executor.setAllowCoreThreadTimeOut(threadPoolProperties.getAllowCoreThreadTimeout());
        executor.setWaitForTasksToCompleteOnShutdown(threadPoolProperties.getWaitForTasksToCompleteOnShutdown());
        executor.setAwaitTerminationSeconds(threadPoolProperties.getAwaitTerminationSeconds());
        executor.setThreadNamePrefix("async-task-");
        // 根据配置获取拒绝策略，未匹配时默认使用CallerRunsPolicy
        RejectedExecutionHandler handler = REJECTED_HANDLER_MAP.getOrDefault(threadPoolProperties.getRejectedExecutionHandler(), new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setRejectedExecutionHandler(handler);
        executor.initialize();
        return executor;
    }

    /**
     * 执行定时任务
     */
    @Bean(name = ThreadConst.SCHEDULED_TASK_EXECUTOR_NAME)
    protected ScheduledExecutorService scheduledTaskExecutor() {
        return new ScheduledThreadPoolExecutor(threadPoolProperties.getCorePoolSize(),
                new BasicThreadFactory.Builder().namingPattern("scheduled-task-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
