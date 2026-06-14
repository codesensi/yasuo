package cn.codesensi.yasuo.sys.controller;

import cn.codesensi.yasuo.annotation.ApiResponseBody;
import cn.codesensi.yasuo.annotation.LogOperate;
import cn.codesensi.yasuo.enums.OperateType;
import cn.codesensi.yasuo.pojo.entity.SysUser;
import cn.codesensi.yasuo.pojo.vo.RouteVO;
import cn.codesensi.yasuo.sys.service.ISysUserService;
import cn.hutool.core.util.StrUtil;
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

import java.util.Arrays;
import java.util.List;

/**
 * 用户信息表 前端控制器
 */
@ApiResponseBody
@RequiredArgsConstructor
@RestController
@Tag(name = "用户信息表接口", description = "用户信息表接口")
@RequestMapping("/sys/user")
public class SysUserController {

    private final ISysUserService sysUserService;

    /**
     * 新增
     *
     * @param sysUser 实体类
     */
    @LogOperate(operateType = OperateType.INSERT, description = "新增一条用户信息表数据")
    @ApiOperationSupport(order = 1)
    @Operation(summary = "新增")
    @PostMapping("/save")
    public void save(@RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
    }

    /**
     * 删除
     *
     * @param id 主键id
     */
    @LogOperate(operateType = OperateType.DELETE, description = "根据id删除一条用户信息表数据")
    @ApiOperationSupport(order = 2)
    @Operation(summary = "删除")
    @Parameter(name = "id", description = "主键id", required = true, in = ParameterIn.PATH)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        sysUserService.removeById(id);
    }

    /**
     * 更新
     *
     * @param sysUser 实体类
     */
    @LogOperate(operateType = OperateType.UPDATE, description = "根据id更新一条用户信息表数据")
    @ApiOperationSupport(order = 3)
    @Operation(summary = "更新")
    @PutMapping("/update")
    public void update(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
    }

    /**
     * 分页列表
     *
     * @param current 当前页
     * @param size    每页数量
     * @param sysUser 实体类
     * @return PageInfo<SysUser> 分页对象
     */
    // @LogOperate(operateType = OperateType.QUERY, description = "条件查询用户信息表分页列表", isSaveResponseData = false)
    @ApiOperationSupport(order = 4)
    @Operation(summary = "分页列表")
    @Parameters({
            @Parameter(name = "current", description = "当前页", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "每页数量", required = true, in = ParameterIn.QUERY)
    })
    @GetMapping("/page")
    public Page<SysUser> page(@RequestParam(name = "current", defaultValue = "1") Integer current,
                              @RequestParam(name = "size", defaultValue = "10") Integer size,
                              @ParameterObject SysUser sysUser) {
        Page<SysUser> page = new Page<>(current, size);
        return sysUserService.lambdaQuery()
                .like(StrUtil.isNotBlank(sysUser.getUsername()), SysUser::getUsername, sysUser.getUsername())
                .orderByDesc(Arrays.asList(SysUser::getCreateTime, SysUser::getId))
                .page(page);
    }

    /**
     * 详情
     *
     * @param id 主键id
     * @return SysUser 实体类
     */
    // @LogOperate(operateType = OperateType.QUERY, description = "根据id查询用户信息表详情")
    @ApiOperationSupport(order = 5)
    @Operation(summary = "详情")
    @Parameter(name = "id", description = "主键id", required = true, in = ParameterIn.PATH)
    @GetMapping("/detail/{id}")
    public SysUser detail(@PathVariable(name = "id") Long id) {
        return sysUserService.getById(id);
    }

    /**
     * 获取当前用户的路由菜单树
     *
     * @return List<RouteVO> 路由菜单树
     */
    @ApiOperationSupport(order = 6)
    @Operation(summary = "获取当前用户的路由菜单树")
    @GetMapping("/getRoutes")
    public List<RouteVO> getRoutes() {
        return sysUserService.getRoutes();
    }

    /**
     * 获取当前用户信息
     *
     * @return SysUser 用户信息
     */
    @ApiOperationSupport(order = 7)
    @Operation(summary = "获取当前用户信息")
    @GetMapping("/getMine")
    public SysUser getMine() {
        return sysUserService.getMine();
    }

}
