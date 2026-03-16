package cn.codesensi.yasuo.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 登录结果
 *
 * @author codesensi
 * @since 2024/1/21 15:39
 */
@Data
@Accessors(chain = true)
@Schema(name = "登录结果", description = "登录结果")
public class LoginVO implements Serializable {

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

    /**
     * accessToken过期时间（毫秒值）
     */
    @Schema(description = "accessToken过期时间（毫秒值）")
    private Long expires;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 角色
     */
    @Schema(description = "角色")
    private List<String> roles;

    /**
     * 权限
     */
    @Schema(description = "权限")
    private List<String> permissions;
}
