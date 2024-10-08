package com.sky.controller.admin;

import com.sky.annotation.Log;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理端登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @Log
    @PostMapping()
    private Result addEmp(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工{}",employeeDTO);
        employeeService.addEmp(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询员工
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> pageViewEmp(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询员工参数{}",employeePageQueryDTO);
        PageResult pageResult=employeeService.pageViewEmp(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 设置员工状态
     * @param status
     * @param id
     * @return
     */
    @Log
    @PostMapping("/status/{status}")
    public Result setEmpStatus(@PathVariable Integer status ,Long id){
        employeeService.setEmpStatus(status,id);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getEmpById(@PathVariable Long id){
        log.info("根据id查询员工信息{}",id);
        Employee employee=employeeService.getEmpById(id);
        return Result.success(employee);
    }

    /**
     * 编辑员工
     * @param employeeDTO
     * @return
     */
    @Log
    @PutMapping
    public Result updateEmpInfo(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑用户信息{}",employeeDTO);
        employeeService.updateEmpInfo(employeeDTO);
        return Result.success();
    }
}
