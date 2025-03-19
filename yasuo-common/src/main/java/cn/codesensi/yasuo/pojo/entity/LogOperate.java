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
 * 操作日志表 实体类
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("log_operate")
@Schema(name = "LogOperate", description = "操作日志表")
public class LogOperate extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @Schema(description = "日志ID")
    private Long id;

    /**
     * 操作类型:0-未知,1-新增,2-更新,3-查询,4-删除
     */
    @Schema(description = "操作类型:0-未知,1-新增,2-更新,3-查询,4-删除")
    private Integer type;

    /**
     * 操作类型说明
     */
    @Schema(description = "操作类型说明")
    private String message;

    /**
     * 操作描述
     */
    @Schema(description = "操作描述")
    private String description;

    /**
     * 请求时间
     */
    @Schema(description = "请求时间")
    private LocalDateTime requestTime;

    /**
     * 请求IP
     */
    @Schema(description = "请求IP")
    private String requestIp;

    /**
     * 请求地区
     */
    @Schema(description = "请求地区")
    private String requestArea;

    /**
     * 请求系统
     */
    @Schema(description = "请求系统")
    private String requestOs;

    /**
     * 请求设备
     */
    @Schema(description = "请求设备")
    private String requestDevice;

    /**
     * 请求浏览器
     */
    @Schema(description = "请求浏览器")
    private String requestBrowser;

    /**
     * 请求接口
     */
    @Schema(description = "请求接口")
    private String requestUrl;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private String requestMethod;

    /**
     * 请求方式
     */
    @Schema(description = "请求方式")
    private String requestMode;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String requestParam;

    /**
     * 响应状态:0-异常,1-正常
     */
    @Schema(description = "响应状态:0-异常,1-正常")
    private Integer responseStatus;

    /**
     * 正常响应时间
     */
    @Schema(description = "正常响应时间")
    private LocalDateTime responseTime;

    /**
     * 响应耗时:单位毫秒
     */
    @Schema(description = "响应耗时:单位毫秒")
    private Long responseConsume;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private String responseData;

    /**
     * 异常响应时间
     */
    @Schema(description = "异常响应时间")
    private LocalDateTime errorTime;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String errorMessage;
}
