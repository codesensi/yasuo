package cn.codesensi.yasuo.sys.mapper;

import cn.codesensi.yasuo.pojo.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表 Mapper接口
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询角色所属的权限码
     *
     * @param roles 角色码列表
     * @return 权限码列表
     */
    List<SysMenu> listMenuPermsByRoles(@Param("roles") List<String> roles);

    /**
     * 查询角色所属的路由菜单
     *
     * @param roles 角色码列表
     * @return 路由菜单列表
     */
    List<SysMenu> listMenuByRoles(@Param("roles") List<String> roles);
}
