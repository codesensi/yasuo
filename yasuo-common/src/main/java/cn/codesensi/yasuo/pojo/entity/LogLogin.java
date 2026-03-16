package cn.codesensi.yasuo.pojo.entity;

import cn.codesensi.yasuo.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志表 实体类
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("log_login")
@Schema(name = "LogLogin", description = "登录日志表")
public class LogLogin extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @Schema(description = "日志ID")
    private Long id;

    /**
     * 登录类型:0-未知,1-登录,2-登出
     */
    @Schema(description = "登录类型:0-未知,1-登录,2-登出")
    private Integer type;

    /**
     * 登录方式:0-未知,1-账号密码,2-手机验证码
     */
    @Schema(description = "登录方式:0-未知,1-账号密码,2-手机验证码")
    private Integer mode;

    /**
     * 登录时间
     */
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 登录IP
     */
    @Schema(description = "登录IP")
    private String ip;

    /**
     * 登录地区
     */
    @Schema(description = "登录地区")
    private String area;

    /**
     * 登录系统
     */
    @Schema(description = "登录系统")
    private String os;

    /**
     * 登录设备
     */
    @Schema(description = "登录设备")
    private String device;

    /**
     * 登录浏览器
     */
    @Schema(description = "登录浏览器")
    private String browser;

    /**
     * 登录状态:0-失败,1-成功
     */
    @Schema(description = "登录状态:0-失败,1-成功")
    private Integer status;

    /**
     * 登录失败原因
     */
    @Schema(description = "登录失败原因")
    private String failReason;
}
