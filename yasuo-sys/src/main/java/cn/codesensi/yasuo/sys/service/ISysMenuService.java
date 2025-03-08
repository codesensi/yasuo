package cn.codesensi.yasuo.sys.service;

import cn.codesensi.yasuo.sys.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 菜单权限表 服务类
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 返回一个账号所拥有的权限编码列表
     *
     * @param userId 用户ID
     * @return 权限编码列表
     */
    List<String> listPermsByUserId(Long userId);
}
