package cn.codesensi.yasuo.sys.mapper;

import cn.codesensi.yasuo.pojo.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色信息表 Mapper接口
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 返回一个账号所拥有的角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    List<String> listRoleCodeByUserId(Long userId);
}
