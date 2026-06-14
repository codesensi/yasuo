package cn.codesensi.yasuo.advice;

import cn.codesensi.yasuo.exception.BizException;
import cn.codesensi.yasuo.exception.LoginException;
import cn.codesensi.yasuo.exception.ModeException;
import cn.codesensi.yasuo.exception.SysException;
import cn.codesensi.yasuo.response.R;
import cn.codesensi.yasuo.response.Result;
import cn.codesensi.yasuo.response.ResultStatus;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理
 *
 * @author codesensi
 * @since 2024/1/10 22:44
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常
     */
    @ExceptionHandler(SysException.class)
    public Result<?> sysExceptionHandler(SysException e) {
        log.error("系统异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.FAIL.getCode(), e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public Result<?> bizExceptionHandler(BizException e) {
        log.error("业务异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.FAIL.getCode(), e.getMessage());
    }

    /**
     * 模式异常
     */
    @ExceptionHandler(ModeException.class)
    public Result<?> modeExceptionHandler(ModeException e) {
        log.error("模式异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.FAIL.getCode(), e.getMessage());
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?> nullPointerExceptionHandler(NullPointerException e) {
        log.error("空指针异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.INTERNAL_ERROR);
    }

    /**
     * 未找到资源异常
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Result<?> noResourceFoundExceptionHandler(NoResourceFoundException e) {
        log.error("未找到资源异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.NOT_FOUND);
    }

    /**
     * 参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("参数缺失异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.PARAMETER_MISSING.getCode(), "缺少必须参数：" + e.getParameterName());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> bindExceptionHandler(BindException e) {
        log.error("参数校验异常！原因是：{}", e.getMessage(), e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = ResultStatus.PARAMETER_VERIFY.getMessage();
        if (ObjUtil.isNotNull(fieldError)) {
            message = fieldError.getDefaultMessage();
        }
        return R.fail(ResultStatus.PARAMETER_VERIFY.getCode(), message);
    }

    /**
     * 账号登录异常
     */
    @ExceptionHandler(LoginException.class)
    public Result<?> loginExceptionHandler(LoginException e) {
        log.error("账号登录异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.FAIL.getCode(), e.getMessage());
    }

    /**
     * 账号鉴权异常
     */
    @ExceptionHandler(SaTokenException.class)
    public Result<?> saTokenExceptionHandler(SaTokenException e) {
        log.error("账号鉴权异常！原因是：{}", e.getMessage(), e);
        switch (e) {
            case NotLoginException notLoginException -> {
                if (NotLoginException.TOKEN_FREEZE.equals(notLoginException.getType())) {
                    return R.fail(ResultStatus.ACCOUNT_FREEZE);
                }
                return R.fail(ResultStatus.NOT_LOGIN);
            }
            case NotRoleException ignored1 -> {
                return R.fail(ResultStatus.NOT_ROLE);
            }
            case NotPermissionException ignored2 -> {
                return R.fail(ResultStatus.NOT_PERMIT);
            }
            default -> {
                return R.fail(ResultStatus.ACCOUNT_ERROR);
            }
        }
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> exceptionHandler(Exception e) {
        log.error("未知异常！原因是：{}", e.getMessage(), e);
        return R.fail(ResultStatus.INTERNAL_ERROR);
    }

}
