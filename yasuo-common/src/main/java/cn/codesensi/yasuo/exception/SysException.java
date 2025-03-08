package cn.codesensi.yasuo.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 自定义系统异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public SysException(String message) {
        super(message);
    }

}
