package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.constant.CacheConstant;
import cn.codesensi.yasuo.constant.Constant;
import cn.codesensi.yasuo.sys.entity.SysMenu;
import cn.codesensi.yasuo.sys.mapper.SysMenuMapper;
import cn.codesensi.yasuo.sys.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单权限表 服务实现类
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    /**
     * 返回一个账号所拥有的权限编码列表
     *
     * @param userId 用户ID
     * @return 权限编码列表
     */
    @Cacheable(cacheNames = CacheConstant.CACHE_USER, key ="'permission:' + #userId")
    @Override
    public List<String> listPermsByUserId(Long userId) {
        // 超级管理员
        if (Constant.ADMIN_ID.equals(userId)) {
            return List.of(Constant.PERM_ADMIN_CODE);
        }
        return baseMapper.listPermsByUserId(userId);
    }
}
