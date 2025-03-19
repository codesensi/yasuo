package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.constants.CacheConst;
import cn.codesensi.yasuo.pojo.entity.SysUser;
import cn.codesensi.yasuo.pojo.vo.RouteVO;
import cn.codesensi.yasuo.sys.mapper.SysUserMapper;
import cn.codesensi.yasuo.sys.service.ISysMenuService;
import cn.codesensi.yasuo.sys.service.ISysUserService;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息表 服务实现类
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysMenuService sysMenuService;

    /**
     * 获取当前用户的路由菜单树
     *
     * @return 路由菜单树
     */
    @Override
    public List<RouteVO> getRoutes() {
        return sysMenuService.getRoutesByUserId(StpUtil.getLoginIdAsLong());
    }
}
