package cn.codesensi.yasuo.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 刷新token参数
 *
 * @author codesensi
 * @since 2024/1/21 15:39
 */
@Data
@Accessors(chain = true)
@Schema(name = "刷新token参数", description = "刷新token参数")
public class TokenRefreshDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 刷新token
     */
    @NotBlank(message = "refreshToken不能为空")
    @Schema(description = "刷新token")
    private String refreshToken;

}
