package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.sys.entity.LogLogin;
import cn.codesensi.yasuo.sys.mapper.LogLoginMapper;
import cn.codesensi.yasuo.sys.service.ILogLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 登录日志表 服务实现类
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService {

}
