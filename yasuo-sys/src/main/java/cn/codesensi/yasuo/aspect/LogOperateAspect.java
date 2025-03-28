package cn.codesensi.yasuo.aspect;

import cn.codesensi.yasuo.annotation.LogOperate;
import cn.codesensi.yasuo.enums.CommonEnum;
import cn.codesensi.yasuo.ext.TaskManager;
import cn.codesensi.yasuo.factory.LogRecordFactory;
import cn.codesensi.yasuo.util.Ip2regionUtil;
import cn.codesensi.yasuo.util.IpUtil;
import cn.codesensi.yasuo.util.ServletUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 接口请求切面日志
 *
 * @author codesensi
 * @since 2024/1/10 22:44
 */
@Slf4j
@Aspect
@Component
public class LogOperateAspect {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<>("Cost Time");

    public LogOperateAspect() {
    }

    /**
     * 切点
     */
    @Pointcut("@annotation(cn.codesensi.yasuo.annotation.LogOperate)")
    public void doPointcut() {
    }

    /**
     * 前置通知
     */
    @Before("doPointcut()")
    public void doBefore() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    /**
     * 返回通知
     */
    @AfterReturning(pointcut = "doPointcut()", returning = "returning")
    public void doAfterReturning(JoinPoint joinPoint, Object returning) {
        handleLog(joinPoint, null, returning);
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "doPointcut()", throwing = "throwable")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        handleLog(joinPoint, throwable, null);
    }

    /**
     * 执行保存日志
     *
     * @param joinPoint 切点
     * @param throwable 异常
     * @param returning 响应
     */
    private void handleLog(final JoinPoint joinPoint, final Throwable throwable, Object returning) {
        try {
            ServletRequestAttributes attributes = ServletUtil.getRequestAttributes();
            Optional.ofNullable(attributes).ifPresent(o -> {
                HttpServletRequest request = o.getRequest();
                Signature signature = joinPoint.getSignature();
                StringBuffer requestUrl = request.getRequestURL();
                String requestMethod = StrUtil.join(".", signature.getDeclaringTypeName(), signature.getName());
                String requestMode = request.getMethod();

                cn.codesensi.yasuo.pojo.entity.LogOperate logOperate = new cn.codesensi.yasuo.pojo.entity.LogOperate();
                // 获取注解信息
                LogOperate annotationLogOperate = getAnnotationLog(joinPoint);
                Optional.ofNullable(annotationLogOperate).ifPresent(l -> {
                    logOperate.setType(l.operateType().getCode());
                    logOperate.setMessage(l.operateType().getMessage());
                    logOperate.setDescription(l.description());
                    // 请求参数
                    if (l.isSaveRequestParam()) {
                        String[] names = ((CodeSignature) signature).getParameterNames();
                        Object[] values = joinPoint.getArgs();
                        Map<String, Object> paramsMap = new HashMap<>();
                        for (int i = 0; i < names.length; i++) {
                            paramsMap.put(names[i], values[i]);
                        }
                        String requestParam = JSONUtil.toJsonStr(paramsMap);
                        logOperate.setRequestParam(requestParam);
                    }
                    // 响应数据
                    if (l.isSaveResponseData()) {
                        String responseData = JSONUtil.toJsonStr(returning);
                        logOperate.setResponseData(responseData);
                    }
                });
                // 请求相关字段
                logOperate.setRequestTime(LocalDateTime.now());
                String ipAddr = IpUtil.getIpAddr();
                logOperate.setRequestIp(ipAddr);
                logOperate.setRequestArea(Ip2regionUtil.search(ipAddr));
                UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtil.getUserAgent());
                logOperate.setRequestOs(userAgent.getOperatingSystem().getName());
                logOperate.setRequestDevice(userAgent.getOperatingSystem().getDeviceType().getName());
                logOperate.setRequestBrowser(userAgent.getBrowser().getName());
                logOperate.setRequestUrl(requestUrl.toString());
                logOperate.setRequestMethod(requestMethod);
                logOperate.setRequestMode(requestMode);
                // 响应相关字段
                logOperate.setResponseStatus(CommonEnum.OkOrFail.OK.getCode());
                logOperate.setResponseTime(LocalDateTime.now());
                logOperate.setResponseConsume(System.currentTimeMillis() - TIME_THREADLOCAL.get());
                log.info("|-----请求来源：{}", logOperate.getRequestIp());
                log.info("|-----请求接口：{}", logOperate.getRequestUrl());
                log.info("|-----请求方法：{}", logOperate.getRequestMethod());
                log.info("|-----请求参数：{}", logOperate.getRequestParam());
                // 异常相关字段
                if (ObjUtil.isNotNull(throwable)) {
                    logOperate.setResponseStatus(CommonEnum.OkOrFail.FAIL.getCode());
                    logOperate.setErrorTime(LocalDateTime.now());
                    logOperate.setErrorMessage(throwable.getMessage());
                    log.error("|-----异常原因：{}", logOperate.getErrorMessage());
                }
                log.info("|-----请求返回：{}", logOperate.getResponseData());
                log.info("|-----请求耗时：{}ms", logOperate.getResponseConsume());
                if (StpUtil.isLogin()) {
                    logOperate.setCreator(StpUtil.getLoginIdAsLong());
                }
                // 异步保存数据库
                TaskManager.me().execute(LogRecordFactory.operate(logOperate));
            });
        } catch (Exception e) {
            // 记录本地异常日志
            log.error("操作日志记录异常！原因是：{}", e.getMessage(), e);
        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    /**
     * 获取存在的注解
     */
    private LogOperate getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (ObjUtil.isNotNull(method)) {
            return method.getAnnotation(LogOperate.class);
        }
        return null;
    }

}
