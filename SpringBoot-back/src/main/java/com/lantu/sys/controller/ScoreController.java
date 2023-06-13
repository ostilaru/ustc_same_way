package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.github.pagehelper.PageHelper;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.Score;
import com.lantu.sys.entity.ScoreForAll;
import com.lantu.sys.mapper.ScoreMapper;
import com.lantu.sys.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
@RestController
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    private IScoreService scoreService;

    @Resource
    private ScoreMapper scoreMapper;

    @GetMapping("/all")
    public Result<Map<String,Object>> getAllScore() {
        Map<String,Object> list = scoreService.getScoreByStudentId("P");
        return Result.success(list, "查询成功");
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getScoreList(@RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize,
                                                    @RequestParam(value = "course_id", required = false) String courseID,
                                                    @RequestParam(value = "course_name", required = false) String courseName,
                                                    @RequestParam(value = "student_id", required = false) String studentID,
                                                    @RequestParam(value = "student_name", required = false) String studentName,
                                                    @RequestParam(value = "teacher_name", required = false) String teacherName,
                                                    @RequestParam(value = "department_name", required = false) String departmentName,
                                                    @RequestParam(value = "score", required = false) String score )
                                                     {

        // 判断前端参数是否为空，如果为空则设置为null
        if (StringUtils.isEmpty(courseID)) {
            courseID = null;
        }
        if (StringUtils.isEmpty(courseName)) {
            courseName = null;
        }
        if (StringUtils.isEmpty(studentID)) {
            studentID = null;
        }
        if (StringUtils.isEmpty(studentName)) {
            studentName = null;
        }
        if (StringUtils.isEmpty(teacherName)) {
            teacherName = null;
        }
        if (StringUtils.isEmpty(departmentName)) {
            departmentName = null;
        }
        if (StringUtils.isEmpty(score)) {
            score = null;
        }


        Long start =  ((pageNo - 1) * pageSize);


        List<ScoreForAll> scoreList = scoreMapper.getScoreList(start, pageSize, courseID, courseName, studentID, studentName, teacherName, departmentName, score);
        Integer total = scoreMapper.getScoreCount(courseID, courseName, studentID, studentName, teacherName, departmentName, score);


        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("rows", scoreList);
        if (scoreList != null) {
            return Result.success(data, "查询成功");
        } else {
            return Result.fail("查询失败");
        }

    }

    @PutMapping
    public Result<?> updateScore(@RequestBody Score score) {
        System.out.println(score);
        scoreMapper.updateScore(score.getStudentId(), score.getCourseId(), score.getScore());
        return Result.success("修改成绩成功");
    }


    @GetMapping("/course_id/{course_id}")
    public Result<Map<String, Object>> getScoreByCourseId(@PathVariable("course_id") String courseId) {
        Map<String, Object> score = scoreService.getScoreByCourseId(courseId);
        if (score == null) {
            return Result.fail("没有该课程");
        } else {
            return Result.success(score);
        }

    }

    @GetMapping("/student_id/{student_id}")
    public Result<Map<String,Object>> getScoreByStudentId(@PathVariable("student_id") String studentId) {
       Map<String, Object> score = scoreService.getScoreByStudentId(studentId);
        if (score == null) {
            return Result.fail("没有该学生");
        } else {
            return Result.success(score);
        }
    }

    @GetMapping("/student_name/{student_name}")
    public Result<Map<String,Object>> getScoreByStudentName(@PathVariable("student_name") String studentName) {
        Map<String, Object> score = scoreService.getScoreByStudentName(studentName);
        if (score == null) {
            return Result.fail("没有该学生");
        } else {
            return Result.success(score);
        }
    }

    @GetMapping("/teacher_name/{teacher_name}")
    public Result<Map<String,Object>> getScoreByTeacherName(@PathVariable("teacher_name") String teacherName) {
        Map<String, Object> score = scoreService.getScoreByTeacherName(teacherName);
        if (score == null) {
            return Result.fail("没有该老师");
        } else {
            return Result.success(score);
        }
    }




}
