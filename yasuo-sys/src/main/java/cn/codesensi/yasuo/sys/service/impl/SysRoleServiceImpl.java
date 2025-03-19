package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.constants.CacheConst;
import cn.codesensi.yasuo.constants.RbacConst;
import cn.codesensi.yasuo.pojo.entity.SysRole;
import cn.codesensi.yasuo.sys.mapper.SysRoleMapper;
import cn.codesensi.yasuo.sys.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息表 服务实现类
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    /**
     * 返回一个账号所拥有的角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    @Cacheable(cacheNames = CacheConst.CACHE_USER, key = "'roles:' + #userId")
    @Override
    public List<String> listRoleCodeByUserId(Long userId) {
        // 超级管理员
        if (RbacConst.ADMIN_ID.equals(userId)) {
            return List.of(RbacConst.ROLE_ADMIN_CODE);
        }
        return baseMapper.listRoleCodeByUserId(userId);
    }
}
