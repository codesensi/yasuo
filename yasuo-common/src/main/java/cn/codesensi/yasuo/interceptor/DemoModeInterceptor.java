package cn.codesensi.yasuo.interceptor;

import cn.codesensi.yasuo.enums.RequestMethod;
import cn.codesensi.yasuo.exception.ModeException;
import cn.codesensi.yasuo.properties.CustomProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 演示模式拦截器
 * 演示模式下禁止 POST、PUT、DELETE 等写操作
 */
@RequiredArgsConstructor
public class DemoModeInterceptor implements HandlerInterceptor {

    private final CustomProperties customProperties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (customProperties.getSecure().getDemoMode()) {
            String method = request.getMethod();
            if (RequestMethod.POST.getCode().equals(method) || RequestMethod.PUT.getCode().equals(method) || RequestMethod.DELETE.getCode().equals(method)) {
                throw new ModeException("演示模式不允许操作哦~");
            }
        }
        return true;
    }
}
