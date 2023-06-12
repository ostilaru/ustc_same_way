package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.MyScore;
import com.lantu.sys.entity.Student;
import com.lantu.sys.entity.User;
import com.lantu.sys.service.IPhysicalExamService;
import com.lantu.sys.service.IStudentService;
import com.lantu.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IPhysicalExamService physicalExamService;

    @GetMapping("/all")
    public Result<List<Student>> getAllStudent() {
        List<Student> list = studentService.list();
        return Result.success(list, "查询成功");
    }

    // 分页查询
    @GetMapping("/list")
    public Result<Map<String, Object>> getStudentList(@RequestParam(value = "student_id", required = false) String studentId,
                                                      @RequestParam(value = "name", required = false) String studentName,
                                                      @RequestParam(value = "gender", required = false) String gender,
                                                      @RequestParam(value = "department_id", required = false) String departmentId,
                                                      @RequestParam(value = "class_id", required = false) String classId,
                                                        @RequestParam(value = "contact_phone", required = false) String contactPhone,
                                                        @RequestParam(value = "contact_email", required = false) String contactEmail,
                                                        @RequestParam(value = "pageNo") Long pageNo,
                                                        @RequestParam(value = "pageSize") Long pageSize) {
        // 需要传入条件构造器
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(studentId), Student::getStudentId, studentId);
        queryWrapper.like(StringUtils.hasLength(studentName), Student::getName, studentName);
        queryWrapper.eq(StringUtils.hasLength(gender), Student::getGender, gender);
        queryWrapper.eq(StringUtils.hasLength(departmentId), Student::getDepartmentId, departmentId);
        queryWrapper.like(StringUtils.hasLength(classId), Student::getClassId, classId);
        queryWrapper.like(StringUtils.hasLength(contactPhone), Student::getContactPhone, contactPhone);
        queryWrapper.like(StringUtils.hasLength(contactEmail), Student::getContactMail, contactEmail);
        queryWrapper.orderByDesc(Student::getStudentId);


        Page<Student> page = new Page<>(pageNo, pageSize);
        studentService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);

    }

    @PostMapping
    public Result<?> addStudent(@RequestBody Student student) {
        studentService.save(student);
        // 处理体检信息

        return Result.success("添加学生成功");
    }

    @DeleteMapping("/{student_id}")
    public Result<?> deleteStudentById(@PathVariable("student_id") String studentId) {
        studentService.removeById(studentId);
        // 处理体检信息

        return Result.success("开除成功");
    }

    @PutMapping
    public Result<?> updateStudent(@RequestBody Student student) {
        studentService.updateById(student);
        // 处理体检信息

        return Result.success("修改成功");
    }

    @GetMapping("/{student_id}")
    public Result<Student> getStudentById(@PathVariable("student_id") String studentId) {
        Student student = studentService.getById(studentId);
        return Result.success(student);
    }


    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Student stu) {
        // System.out.println(stu);
        Map<String, Object> data = studentService.studentLogin(stu);
        // System.out.println(data);

        if(data != null) {
            System.out.println("登录成功");
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getStudentInfo(@RequestParam("token") String token) {
        Map<String, Object> data = studentService.getStudentInfo(token);
        if(data != null) {
            return Result.success(data);
        }
        return Result.fail(20003, "token无效");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestParam("token") String token) {
        studentService.logout(token);
        return Result.success("退出成功");
    }

    @GetMapping("/myscore")
    public Result<Map<String, Object>> getMyScoreById(@RequestParam(value = "name") String studentId) {
        Map<String, Object> data = studentService.getMyScoreById(studentId);

//        Page<MyScore> page = new Page<>(Long.parseLong(pageNo), Long.parseLong(pageSize));
//        studentService.page(page, data);

        if (data != null) {
            return Result.success(data);
        }
        return Result.fail(20004, "查询失败");


    }

}
