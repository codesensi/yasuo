package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.sys.entity.SysUser;
import cn.codesensi.yasuo.sys.mapper.SysUserMapper;
import cn.codesensi.yasuo.sys.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户信息表 服务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
