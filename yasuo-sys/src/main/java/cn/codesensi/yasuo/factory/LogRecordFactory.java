package cn.codesensi.yasuo.factory;

import cn.codesensi.yasuo.sys.entity.LogLogin;
import cn.codesensi.yasuo.sys.entity.LogOperate;
import cn.codesensi.yasuo.sys.service.ILogLoginService;
import cn.codesensi.yasuo.sys.service.ILogOperateService;
import cn.codesensi.yasuo.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 任务工厂
 */
@Slf4j
public class LogRecordFactory {

    /**
     * 操作日志记录
     *
     * @param logOperate 操作日志信息
     * @return 任务task
     */
    public static TimerTask operate(final LogOperate logOperate) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtil.getBean(ILogOperateService.class).save(logOperate);
            }
        };
    }

    /**
     * 登录日志记录
     *
     * @param logLogin 登录日志
     * @return 任务task
     */
    public static TimerTask login(final LogLogin logLogin) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtil.getBean(ILogLoginService.class).save(logLogin);
            }
        };
    }
}
