package cn.codesensi.yasuo.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 刷新token结果
 *
 * @author codesensi
 * @since 2024/1/21 15:39
 */
@Data
@Accessors(chain = true)
@Schema(name = "刷新token结果", description = "刷新token结果")
public class TokenRefreshVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 认证token
     */
    @Schema(description = "认证token")
    private String accessToken;

    /**
     * 刷新token
     */
    @Schema(description = "刷新token")
    private String refreshToken;

    /**
     * accessToken过期时间（毫秒值）
     */
    @Schema(description = "accessToken过期时间（毫秒值）")
    private Long expires;

}
