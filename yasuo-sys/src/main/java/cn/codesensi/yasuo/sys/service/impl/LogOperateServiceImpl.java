package cn.codesensi.yasuo.sys.service.impl;

import cn.codesensi.yasuo.pojo.entity.LogOperate;
import cn.codesensi.yasuo.sys.mapper.LogOperateMapper;
import cn.codesensi.yasuo.sys.service.ILogOperateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 操作日志表 服务实现类
 */
@Service
public class LogOperateServiceImpl extends ServiceImpl<LogOperateMapper, LogOperate> implements ILogOperateService {

}
