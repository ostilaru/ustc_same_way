package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.Student;
import com.lantu.sys.entity.Teacher;
import com.lantu.sys.service.IStudentService;
import com.lantu.sys.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
                                                      @RequestParam(value = "teacher_name", required = false) String teacherName,
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
    public Result<?> deleteStudentById(@PathVariable("teacher_id") String teacherId) {
        teacherService.removeById(teacherId);
        return Result.success("删除成功");
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
}
