package cn.codesensi.yasuo.sys.controller;

import cn.codesensi.yasuo.annotation.ApiResponseBody;
import cn.codesensi.yasuo.pojo.entity.LogOperate;
import cn.codesensi.yasuo.sys.service.ILogOperateService;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志表 前端控制器
 */
@ApiResponseBody
@RequiredArgsConstructor
@RestController
@Tag(name = "操作日志表接口", description = "操作日志表接口")
@RequestMapping("/log/operate")
public class LogOperateController {

    private final ILogOperateService logOperateService;

    /**
     * 新增
     *
     * @param logOperate 实体类
     */
    // @LogOperate(operateType = OperateType.INSERT, description = "新增一条操作日志表数据")
    // @ApiOperationSupport(order = 1)
    // @Operation(summary = "新增")
    // @PostMapping("/save")
    public void save(@RequestBody LogOperate logOperate) {
        logOperateService.save(logOperate);
    }

    /**
     * 删除
     *
     * @param id 主键id
     */
    // @LogOperate(operateType = OperateType.DELETE, description = "根据id删除一条操作日志表数据")
    // @ApiOperationSupport(order = 2)
    // @Operation(summary = "删除")
    // @Parameter(name = "id", description = "主键id", required = true, in = ParameterIn.PATH)
    // @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        logOperateService.removeById(id);
    }

    /**
     * 更新
     *
     * @param logOperate 实体类
     */
    // @LogOperate(operateType = OperateType.UPDATE, description = "根据id更新一条操作日志表数据")
    // @ApiOperationSupport(order = 3)
    // @Operation(summary = "更新")
    // @PutMapping("/update")
    public void update(@RequestBody LogOperate logOperate) {
        logOperateService.updateById(logOperate);
    }

    /**
     * 分页列表
     *
     * @param current    当前页
     * @param size       每页数量
     * @param logOperate 实体类
     * @return PageInfo<LogOperate> 分页对象
     */
    // @OperateLog(operateType = OperateType.QUERY, description = "条件查询操作日志表分页列表", isSaveResponseData = false)
    @ApiOperationSupport(order = 4)
    @Operation(summary = "分页列表")
    @Parameters({
            @Parameter(name = "current", description = "当前页", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "每页数量", required = true, in = ParameterIn.QUERY)
    })
    @GetMapping("/page")
    public Page<LogOperate> page(@RequestParam(name = "current", defaultValue = "1") Integer current,
                                 @RequestParam(name = "size", defaultValue = "10") Integer size,
                                 @ParameterObject LogOperate logOperate) {
        Page<LogOperate> page = new Page<>(current, size);
        return logOperateService.lambdaQuery()
                // TODO 组织条件
                .eq(ObjUtil.isNotNull(logOperate.getId()), LogOperate::getId, logOperate.getId())
                .orderByDesc(LogOperate::getCreateTime)
                .page(page);
    }

    /**
     * 详情
     *
     * @param id 主键id
     * @return LogOperate 实体类
     */
    // @OperateLog(operateType = OperateType.QUERY, description = "根据id查询操作日志表详情")
    @ApiOperationSupport(order = 5)
    @Operation(summary = "详情")
    @Parameter(name = "id", description = "主键id", required = true, in = ParameterIn.PATH)
    @GetMapping("/detail/{id}")
    public LogOperate detail(@PathVariable(name = "id") Long id) {
        return logOperateService.getById(id);
    }

}
