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
     * 返回一个账号所拥有的权限编码列表
     *
     * @param userId 用户ID
     * @return 权限编码列表
     */
    List<String> listPermByUserId(@Param("userId") Long userId);

    /**
     * 查询用户菜单列表
     *
     * @param userId
     * @return
     */
    List<SysMenu> listMenuByUserId(@Param("userId") Long userId);
}
