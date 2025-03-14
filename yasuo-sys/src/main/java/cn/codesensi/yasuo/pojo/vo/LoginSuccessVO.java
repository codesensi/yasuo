package cn.codesensi.yasuo.pojo.vo;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 登录成功结果参数
 *
 * @author codesensi
 * @since 2024/1/21 15:39
 */
@Data
@Accessors(chain = true)
@Schema(name = "登录成功结果参数", description = "登录成功结果参数")
public class LoginSuccessVO implements Serializable {

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
     * 刷新token
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    @Schema(description = "过期时间")
    private LocalDateTime expireTime;

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
