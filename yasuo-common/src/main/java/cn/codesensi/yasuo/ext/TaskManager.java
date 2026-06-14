package cn.codesensi.yasuo.ext;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务管理器
 */
@RequiredArgsConstructor
@Component
public class TaskManager {

    /**
     * 异步任务执行器（通过构造器注入）
     */
    private final ThreadPoolTaskExecutor asyncTaskExecutor;

    /**
     * 定时任务执行器（通过构造器注入）
     */
    private final ScheduledExecutorService scheduledTaskExecutor;

    /**
     * 持有单例引用，兼容原有的 TaskManager.me() 静态调用方式
     */
    private static TaskManager me;

    @PostConstruct
    public void init() {
        TaskManager.me = this;
    }

    /**
     * 获取 TaskManager 实例（兼容原有静态调用方式）
     */
    public static TaskManager me() {
        return me;
    }

    /**
     * 执行异步任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        asyncTaskExecutor.execute(task);
    }

    /**
     * 执行定时任务
     *
     * @param task  任务
     * @param delay 延时毫秒数
     */
    public void schedule(TimerTask task, Long delay) {
        scheduledTaskExecutor.schedule(task, delay, TimeUnit.MILLISECONDS);
    }

}
