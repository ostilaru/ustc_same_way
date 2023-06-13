package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.Teacher;
import com.lantu.sys.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private ITeacherService teacherService;

    @GetMapping("/all")
    public Result<List<Teacher>> getAllTeacher() {
        List<Teacher> list = teacherService.list();
        return Result.success(list, "查询成功");
    }

    //分页查询
    @GetMapping("/list")
    public Result<Map<String, Object>> getTeacherList(@RequestParam(value = "teacher_id", required = false) String teacherId,
                                                      @RequestParam(value = "name", required = false) String teacherName,
                                                      @RequestParam(value = "gender", required = false) String gender,
                                                      @RequestParam(value = "department_id", required = false) String departmentId,
                                                      @RequestParam(value = "contact_phone", required = false) String contactPhone,
                                                      @RequestParam(value = "contact_email", required = false) String contactEmail,
                                                      @RequestParam(value = "pageNo") Long pageNo,
                                                      @RequestParam(value = "pageSize") Long pageSize) {
        // 需要传入条件构造器
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(teacherId), Teacher::getTeacherId, teacherId);
        queryWrapper.like(StringUtils.hasLength(teacherName), Teacher::getTeacherName, teacherName);
        queryWrapper.eq(StringUtils.hasLength(gender), Teacher::getGender, gender);
        queryWrapper.eq(StringUtils.hasLength(departmentId), Teacher::getDepartmentId, departmentId);
        queryWrapper.like(StringUtils.hasLength(contactPhone), Teacher::getContactPhone, contactPhone);
        queryWrapper.like(StringUtils.hasLength(contactEmail), Teacher::getContactMail, contactEmail);
        queryWrapper.orderByDesc(Teacher::getTeacherId);


        Page<Teacher> page = new Page<>(pageNo, pageSize);
        teacherService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);

    }
    @PostMapping
    public Result<?> addTeacher(@RequestBody Teacher teacher) {
        teacherService.save(teacher);

        return Result.success("添加教师成功");
    }

    @DeleteMapping("/{teacher_id}")
    public Result<?> deleteTeacherById(@PathVariable("teacher_id") String teacherId) {
        teacherService.removeById(teacherId);
        return Result.success("辞退成功");
    }

    @PutMapping
    public Result<?> updateTeacher(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);

        return Result.success("修改成功");
    }
    @GetMapping("/{teacher_id}")
    public Result<Teacher> getTeacherById(@PathVariable("teacher_id") String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        return Result.success(teacher);
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Teacher tea) {
        Map<String, Object> data = teacherService.teacherLogin(tea);

        if(data != null) {
            System.out.println("登录成功");
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getTeacherInfo(@RequestParam("token") String token) {
        Map<String, Object> data = teacherService.getTeacherInfo(token);
        if(data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "token无效");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestParam("token") String token) {
        teacherService.logout(token);
        return Result.success("退出成功");
    }

    @GetMapping("/mycourse")
    public Result<Map<String, Object>> getMyCourse(@RequestParam("teacher_id") String teacherId) {
        Map<String, Object> data = teacherService.getMyCourseByTeacherId(teacherId);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20004, "查询失败");
    }

    @GetMapping("/name")
    public Result<Object> getTeacherNameById(@RequestParam("teacher_id") String teacherId) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getTeacherId, teacherId);
        Teacher teacher = teacherService.getOne(queryWrapper);
        return Result.success(teacher.getTeacherName());
    }

}
