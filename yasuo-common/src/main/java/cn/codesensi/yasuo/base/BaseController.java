package cn.codesensi.yasuo.base;

import cn.codesensi.yasuo.enums.RequestMethod;
import cn.codesensi.yasuo.exception.ModeException;
import cn.codesensi.yasuo.properties.CustomProperties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * web层通用数据处理
 */
public class BaseController {

    @Resource
    private CustomProperties customProperties;

    @ModelAttribute
    public void init(HttpServletRequest request) {
        if (customProperties.getSecure().getDemoMode()) {
            // 增删改 请求
            if (RequestMethod.PUT.getCode().equals(request.getMethod()) || RequestMethod.POST.getCode().equals(request.getMethod()) || RequestMethod.DELETE.getCode().equals(request.getMethod())) {
                throw new ModeException("演示模式不允许操作哦~");
            }
        }
    }

}
