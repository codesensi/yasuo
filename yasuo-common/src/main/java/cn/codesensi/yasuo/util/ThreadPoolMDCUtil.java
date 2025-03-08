package cn.codesensi.yasuo.util;

import cn.codesensi.yasuo.constant.Constant;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 线程MDC工具
 */
public class ThreadPoolMDCUtil {

    /**
     * 设置traceId
     */
    public static void setTraceId() {
        if (StrUtil.isNotBlank(MDC.get(Constant.TRACE_ID))) {
            MDC.put(Constant.TRACE_ID, MDC.get(Constant.TRACE_ID));
        } else {
            MDC.put(Constant.TRACE_ID, IdUtil.fastSimpleUUID());
        }

    }

    /**
     * Callable Wrap
     *
     * @param callable
     * @param context
     * @param <T>
     * @return
     */
    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceId();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    /**
     * Runnable Wrap
     *
     * @param runnable
     * @param context
     * @return
     */
    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceId();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
