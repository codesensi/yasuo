package cn.codesensi.yasuo.sys.service;

import cn.codesensi.yasuo.pojo.entity.SysUser;
import cn.codesensi.yasuo.pojo.vo.RouteVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户信息表 服务类
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 获取当前用户的菜单列表
     *
     * @return
     */
    List<RouteVO> getRoutes();

}
