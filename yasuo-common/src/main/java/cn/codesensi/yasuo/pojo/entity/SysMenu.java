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

/**
 * 菜单权限表 实体类
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@Schema(name = "SysMenu", description = "路由菜单表")
public class SysMenu extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 路由菜单ID
     */
    @Schema(description = "路由菜单ID")
    private Long id;

    /**
     * 父级路由菜单ID
     */
    @Schema(description = "父级路由菜单ID")
    private Long pid;

    /**
     * 路由名称(外链地址)
     */
    @Schema(description = "路由名称(外链地址)")
    private String name;

    /**
     * 路由路径
     */
    @Schema(description = "路由路径")
    private String path;

    /**
     * 路由参数
     */
    @Schema(description = "路由参数")
    private String param;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String title;

    /**
     * 菜单类型:1-目录,2-菜单,3-按钮
     */
    @Schema(description = "菜单类型:1-目录,2-菜单,3-按钮")
    private Integer type;

    /**
     * 菜单排序
     */
    @Schema(description = "菜单排序")
    private Integer rank;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 权限编码
     */
    @Schema(description = "权限编码")
    private String perms;

    /**
     * 是否外链:0-否,1-是
     */
    @Schema(description = "是否外链:0-否,1-是")
    private Integer isLink;

    /**
     * 是否内嵌iframe:0-否,1-是
     */
    @Schema(description = "是否内嵌iframe:0-否,1-是")
    private Integer isFrame;

    /**
     * 内嵌iframe地址
     */
    @Schema(description = "内嵌iframe地址")
    private String frameSrc;

    /**
     * 是否显示:0-否,1-是
     */
    @Schema(description = "是否显示:0-否,1-是")
    private Integer isShow;

    /**
     * 是否显示父级菜单:0-否,1-是
     */
    @Schema(description = "是否显示父级菜单:0-否,1-是")
    private Integer isShowParent;

    /**
     * 菜单状态:0-启用,1-禁用
     */
    @Schema(description = "菜单状态:0-启用,1-禁用")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}
