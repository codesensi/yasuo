package cn.codesensi.yasuo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 自定义业务异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BizException(String message) {
        super(message);
    }

}
