package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.PhysicalExam;
import com.lantu.sys.entity.Student;
import com.lantu.sys.service.IPhysicalExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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
@RequestMapping("/physicalExam")
public class PhysicalExamController {

    @Autowired
    private IPhysicalExamService physicalExamService;

    @GetMapping("/all")
    public Result<List<PhysicalExam>> getAllStudent() {
        List<PhysicalExam> list = physicalExamService.list();
        return Result.success(list, "查询成功");
    }

    // 分页查询
    @GetMapping("/list")
    public Result<Map<String, Object>> getPhysicalExam(@RequestParam(value = "student_id", required = false) String studentId,
                                                      @RequestParam(value = "name", required = false) String name,
                                                      @RequestParam(value = "gender", required = false) String gender,
                                                       @RequestParam(value = "height", required = false) String height,
                                                      @RequestParam(value = "weight", required = false) String weight,
                                                      @RequestParam(value = "vision", required = false) String vision,
                                                       @RequestParam(value = "lung_capacity", required = false) String lungCapacity,
                                                       @RequestParam(value = "exam_date", required = false) String examDate,
                                                       @RequestParam(value = "pageNo") Long pageNo,
                                                      @RequestParam(value = "pageSize") Long pageSize) {
        // 需要传入条件构造器
        LambdaQueryWrapper<PhysicalExam> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(studentId), PhysicalExam::getStudentId, studentId);
        queryWrapper.like(StringUtils.hasLength(name), PhysicalExam::getName, name);
        queryWrapper.eq(StringUtils.hasLength(gender), PhysicalExam::getGender, gender);
        queryWrapper.eq(StringUtils.hasLength(height), PhysicalExam::getHeight, height);
        queryWrapper.like(StringUtils.hasLength(weight), PhysicalExam::getWeight, weight);
        queryWrapper.like(StringUtils.hasLength(vision), PhysicalExam::getVision, vision);
        queryWrapper.like(StringUtils.hasLength(lungCapacity), PhysicalExam::getLungCapacity, lungCapacity);
        queryWrapper.like(StringUtils.hasLength(examDate), PhysicalExam::getExamDate, examDate);
        queryWrapper.orderByDesc(PhysicalExam::getStudentId);


        Page<PhysicalExam> page = new Page<>(pageNo, pageSize);
        physicalExamService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);

    }

    @PostMapping
    public Result<?> addStudent(@RequestBody PhysicalExam physicalExam) {
        physicalExamService.save(physicalExam);

        return Result.success("添加学生成功");
    }

    @DeleteMapping("/{student_id}")
    public Result<?> deleteStudentById(@PathVariable("student_id") String studentId) {
        physicalExamService.removeById(studentId);
        // 处理体检信息

        return Result.success("删除成功");
    }

    @PutMapping
    public Result<?> updateStudent(@RequestBody PhysicalExam physicalExam) {
        physicalExamService.updateById(physicalExam);
        // 处理体检信息

        return Result.success("修改成功");
    }

    @GetMapping("/{student_id}")
    public Result<PhysicalExam> getStudentById(@PathVariable("student_id") String studentId) {
        PhysicalExam physicalExam = physicalExamService.getById(studentId);
        return Result.success(physicalExam);
    }
}
