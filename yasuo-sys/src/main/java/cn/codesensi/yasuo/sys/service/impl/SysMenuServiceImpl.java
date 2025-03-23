package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.constants.CacheConst;
import cn.codesensi.yasuo.constants.RbacConst;
import cn.codesensi.yasuo.enums.CommonEnum;
import cn.codesensi.yasuo.pojo.entity.SysMenu;
import cn.codesensi.yasuo.pojo.vo.MetaVO;
import cn.codesensi.yasuo.pojo.vo.RouteVO;
import cn.codesensi.yasuo.sys.mapper.SysMenuMapper;
import cn.codesensi.yasuo.sys.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Cacheable(cacheNames = CacheConst.CACHE_USER, key = "'perms:' + #userId")
    @Override
    public List<String> listPermsByUserId(Long userId) {
        // 超级管理员
        if (RbacConst.ADMIN_ID.equals(userId)) {
            return List.of(RbacConst.PERM_ADMIN_CODE);
        }
        return baseMapper.listPermsByUserId(userId);
    }

    /**
     * 查询用户路由菜单树
     *
     * @param userId 用户id
     * @return 路由菜单树
     */
    @Cacheable(cacheNames = CacheConst.CACHE_USER, key = "'routes:' + #userId")
    @Override
    public List<RouteVO> getRoutesByUserId(Long userId) {
        List<SysMenu> menus = baseMapper.listMenuByUserId(userId);
        return buildRoutesTree(menus);
    }

    /**
     * 构建路由菜单树
     *
     * @param menuList 菜单列表
     * @return RouteVO 树结构
     */
    private List<RouteVO> buildRoutesTree(List<SysMenu> menuList) {
        // 将菜单列表按父节点 ID 分组
        Map<Long, List<SysMenu>> menuGroupByPid = menuList.stream()
                .collect(Collectors.groupingBy(SysMenu::getPid));

        // 获取根节点（pid = 0）
        List<SysMenu> rootMenus = menuGroupByPid.getOrDefault(0L, new ArrayList<>());

        // 构建树结构,按 sort 排序
        return rootMenus.stream()
                .map(menu -> buildRouteVO(menu, menuGroupByPid))
                .sorted(Comparator.comparingInt(route -> route.getMeta().getSort())) // 按 sort 排序
                .collect(Collectors.toList());
    }

    /**
     * 递归构建 RouteVO 树结构
     *
     * @param menu           当前菜单
     * @param menuGroupByPid 按父节点 ID 分组的菜单列表
     * @return RouteVO
     */
    private RouteVO buildRouteVO(SysMenu menu, Map<Long, List<SysMenu>> menuGroupByPid) {
        RouteVO routeVO = new RouteVO();
        routeVO.setPath(menu.getPath());
        routeVO.setName(menu.getName());
        routeVO.setComponent(menu.getComponent());

        // 设置 MetaVO
        MetaVO metaVO = new MetaVO();
        metaVO.setTitle(menu.getTitle());
        metaVO.setIcon(menu.getIcon());
        metaVO.setShowLink(CommonEnum.YesOrNo.YES.getCode().equals(menu.getIsShow()));
        metaVO.setSort(menu.getSort());
        metaVO.setShowParent(CommonEnum.YesOrNo.YES.getCode().equals(menu.getIsShowParent()));
        metaVO.setFrameSrc(menu.getFrameSrc());
        routeVO.setMeta(metaVO);

        // 递归构建子节点
        List<SysMenu> childrenMenus = menuGroupByPid.getOrDefault(menu.getId(), new ArrayList<>());
        if (!childrenMenus.isEmpty()) {
            List<RouteVO> children = childrenMenus.stream()
                    .map(childMenu -> buildRouteVO(childMenu, menuGroupByPid))
                    .sorted(Comparator.comparingInt(route -> route.getMeta().getSort())) // 按 sort 排序
                    .collect(Collectors.toList());
            routeVO.setChildren(children);
        }
        return routeVO;
    }

}
