package cn.codesensi.yasuo.factory;

import cn.codesensi.yasuo.enums.CommonEnum;
import cn.codesensi.yasuo.pojo.entity.LogLogin;
import cn.codesensi.yasuo.pojo.entity.LogOperate;
import cn.codesensi.yasuo.sys.service.ILogLoginService;
import cn.codesensi.yasuo.sys.service.ILogOperateService;
import cn.codesensi.yasuo.util.Ip2regionUtil;
import cn.codesensi.yasuo.util.IpUtil;
import cn.codesensi.yasuo.util.ServletUtil;
import cn.codesensi.yasuo.util.SpringUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
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
     * @return 任务task
     */
    public static TimerTask login(final Object userId, final Integer type, final Integer mode, final HttpServletRequest request) {
        LogLogin logLogin = new LogLogin();
        logLogin.setType(type);
        logLogin.setMode(mode);
        String ipAddr = IpUtil.getIpAddr(request);
        logLogin.setIp(ipAddr);
        logLogin.setArea(Ip2regionUtil.search(ipAddr));
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtil.getUserAgent(request));
        logLogin.setOs(userAgent.getOperatingSystem().getName());
        logLogin.setDevice(userAgent.getOperatingSystem().getDeviceType().getName());
        logLogin.setBrowser(userAgent.getBrowser().getName());
        logLogin.setStatus(CommonEnum.OkOrFail.OK.getCode());
        logLogin.setCreator(Long.valueOf(String.valueOf(userId)));
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtil.getBean(ILogLoginService.class).save(logLogin);
            }
        };
    }
}
