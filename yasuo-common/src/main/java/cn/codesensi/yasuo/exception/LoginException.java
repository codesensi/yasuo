package cn.codesensi.yasuo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 账号登录异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LoginException(String message) {
        super(message);
    }

}
