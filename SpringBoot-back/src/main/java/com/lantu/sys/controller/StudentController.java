package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.Student;
import com.lantu.sys.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public Result<List<Student>> getAllStudent() {
        List<Student> list = studentService.list();
        return Result.success(list, "查询成功");
    }

    // 分页查询
    @GetMapping("/list")
    public Result<Map<String, Object>> getStudentList(@RequestParam(value = "student_id", required = false) String studentId,
                                                      @RequestParam(value = "name", required = false) String studentName,
                                                      @RequestParam(value = "department_id", required = false) String departmentId,
                                                      @RequestParam(value = "class_id", required = false) String classId,
                                                        @RequestParam(value = "contact_phone", required = false) String contactPhone,
                                                        @RequestParam(value = "contact_email", required = false) String contactEmail,
                                                        @RequestParam(value = "pageNo") Long pageNo,
                                                        @RequestParam(value = "pageSize") Long pageSize) {
        // 需要传入条件构造器
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasLength(studentId), Student::getStudentId, studentId);
        queryWrapper.eq(StringUtils.hasLength(studentName), Student::getName, studentName);
        queryWrapper.eq(StringUtils.hasLength(departmentId), Student::getDepartmentId, departmentId);
        queryWrapper.eq(StringUtils.hasLength(classId), Student::getClassId, classId);
        queryWrapper.eq(StringUtils.hasLength(contactPhone), Student::getContactPhone, contactPhone);
        queryWrapper.eq(StringUtils.hasLength(contactEmail), Student::getContactMail, contactEmail);
        queryWrapper.orderByDesc(Student::getStudentId);


        Page<Student> page = new Page<>(pageNo, pageSize);
        studentService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);

    }

}
