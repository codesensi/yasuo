package cn.codesensi.yasuo.ext;

import cn.codesensi.yasuo.constants.ThreadConst;
import cn.codesensi.yasuo.util.SpringUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务管理器
 */
public class TaskManager {

    /**
     * 异步任务执行器
     */
    private final ThreadPoolTaskExecutor asyncTaskExecutor = SpringUtil.getBean(ThreadConst.ASYNC_TASK_EXECUTOR_NAME, ThreadPoolTaskExecutor.class);

    /**
     * 定时额任务执行器
     */
    private final ScheduledExecutorService scheduledTaskExecutor = SpringUtil.getBean(ThreadConst.SCHEDULED_TASK_EXECUTOR_NAME, ScheduledExecutorService.class);


    /**
     * 单例模式
     */
    private TaskManager() {
    }

    private static final TaskManager me = new TaskManager();

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
