package cn.codesensi.yasuo.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 退出登录参数
 *
 * @author codesensi
 * @since 2024/1/21 15:39
 */
@Data
@Accessors(chain = true)
@Schema(name = "退出登录参数", description = "退出登录参数")
public class LogoutDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * accessToken
     */
    @Schema(description = "accessToken")
    private String accessToken;

    /**
     * refreshToken
     */
    @Schema(description = "refreshToken")
    private String refreshToken;

}
