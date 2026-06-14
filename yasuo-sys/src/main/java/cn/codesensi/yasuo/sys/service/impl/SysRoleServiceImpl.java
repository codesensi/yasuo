package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.constants.RbacConst;
import cn.codesensi.yasuo.pojo.entity.SysRole;
import cn.codesensi.yasuo.sys.mapper.SysRoleMapper;
import cn.codesensi.yasuo.sys.service.ISysRoleService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表 服务实现类
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    /**
     * 返回一个账号所拥有的角色编码列表
     *
     * @param userId 用户ID
     * @return 角色编码列表
     */
    @Override
    public List<String> listRoleCodeByUserId(Long userId) {
        // 获取去重后的角色列表
        List<SysRole> sysRoles = listRoleByUserId(userId);
        // 获取角色编码列表
        List<String> roleCodeList = sysRoles.stream()
                .map(SysRole::getCode)
                .filter(StrUtil::isNotBlank)
                .toList();
        // 超级管理员角色编码
        if (roleCodeList.contains(RbacConst.ROLE_ADMIN_CODE)) {
            return List.of(RbacConst.ROLE_ADMIN_CODE);
        }
        return roleCodeList;
    }

    /**
     * 返回一个账号所拥有的角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> listRoleByUserId(Long userId) {
        List<SysRole> sysRoles = baseMapper.listRoleByUserId(userId);
        // 去重：保留第一个出现的元素
        return sysRoles.stream()
                .collect(Collectors.toMap(SysRole::getCode,
                        role -> role))
                .values()
                .stream()
                .toList();
    }

}
