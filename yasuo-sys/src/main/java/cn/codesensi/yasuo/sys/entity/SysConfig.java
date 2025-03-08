package cn.codesensi.yasuo.sys.entity;

import cn.codesensi.yasuo.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统配置表 实体类
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
@Schema(name = "SysConfig", description = "系统配置表")
public class SysConfig extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @Schema(description = "配置ID")
    private Long id;

    /**
     * 是否初始化:0-否,1-是
     */
    @Schema(description = "是否初始化:0-否,1-是")
    private Integer isInit;
}
