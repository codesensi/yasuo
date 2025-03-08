package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.sys.entity.SysConfig;
import cn.codesensi.yasuo.sys.mapper.SysConfigMapper;
import cn.codesensi.yasuo.sys.service.ISysConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 系统配置表 服务实现类
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

}
