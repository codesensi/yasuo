package cn.codesensi.yasuo.sys.service;

import cn.codesensi.yasuo.pojo.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色信息表 服务类
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 返回一个账号所拥有的角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> listRoleByUserId(Long userId);
}
