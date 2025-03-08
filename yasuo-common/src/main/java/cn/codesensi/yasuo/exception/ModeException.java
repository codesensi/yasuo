package cn.codesensi.yasuo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 模式异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ModeException(String message) {
        super(message);
    }
}
