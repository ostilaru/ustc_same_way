package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.Class;
import com.lantu.sys.entity.Course;
import com.lantu.sys.entity.Student;
import com.lantu.sys.service.IClassService;
import com.lantu.sys.service.ICourseService;
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
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping("/all")
    public Result<List<Course>> getAllCourse() {
        List<Course> list = courseService.list();
        return Result.success(list, "查询成功");
    }

    // 分页查询
    @GetMapping("/list")
    public Result<Map<String, Object>> getCourseList(@RequestParam(value = "course_id", required = false) String courseId,
                                                      @RequestParam(value = "course_name", required = false) String courseName,
                                                      @RequestParam(value = "credit_points", required = false) String creditPoints,
                                                      @RequestParam(value = "class_schedule", required = false) String classSchedule,
                                                      @RequestParam(value = "classroom_location", required = false) String classroomLocation,
                                                      @RequestParam(value = "teacher_id", required = false) String teacherId,
                                                      @RequestParam(value = "department_id", required = false) String departmentId,
                                                      @RequestParam(value = "pageNo") Long pageNo,
                                                      @RequestParam(value = "pageSize") Long pageSize) {
        // 需要传入条件构造器
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(courseId), Course::getCourseId, courseId);
        queryWrapper.like(StringUtils.hasLength(courseName), Course::getCourseName, courseName);
        queryWrapper.like(StringUtils.hasLength(creditPoints), Course::getCreditPoints, creditPoints);
        queryWrapper.like(StringUtils.hasLength(classSchedule), Course::getClassSchedule, classSchedule);
        queryWrapper.like(StringUtils.hasLength(classroomLocation), Course::getClassroomLocation, classroomLocation);
        queryWrapper.like(StringUtils.hasLength(teacherId), Course::getTeacherId, teacherId);
        queryWrapper.like(StringUtils.hasLength(departmentId), Course::getDepartmentId, departmentId);
        queryWrapper.orderByDesc(Course::getCourseId);


        Page<Course> page = new Page<>(pageNo, pageSize);
        courseService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);

    }

    @PostMapping
    public Result<?> addCourse(@RequestBody Course course) {
        courseService.save(course);
        return Result.success("添加成功");
    }

    @DeleteMapping("/{course_id}")
    public Result<?> deleteCourseById(@PathVariable("course_id") String courseId) {
        courseService.removeById(courseId);
        return Result.success("删除成功");
    }

    @PutMapping
    public Result<?> updateCourse(@RequestBody Course course) {
        courseService.updateById(course);
        return Result.success("修改成功");
    }

    @GetMapping("/{course_id}")
    public Result<Course> getCourseById(@PathVariable("course_id") String courseId) {
        Course course = courseService.getById(courseId);
        return Result.success(course);
    }



}
