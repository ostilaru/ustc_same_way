package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.Score;
import com.lantu.sys.entity.ScoreForAll;
import com.lantu.sys.service.IScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/all")
    public Result<Map<String,Object>> getAllScore() {
        Map<String,Object> list = scoreService.getScoreByStudentId("P");
        return Result.success(list, "查询成功");
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getScoreList(@RequestParam(value = "course_id", required = false) String courseID,
                                                    @RequestParam(value = "student_id", required = false) String studentID,
                                                    @RequestParam(value = "score", required = false) String score,
                                                    @RequestParam(value = "pageNo") Long pageNo,
                                                    @RequestParam(value = "pageSize") Long pageSize) {
        // 需要传入条件构造器
        LambdaQueryWrapper<Score> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(courseID), Score::getCourseId, courseID)
                .like(StringUtils.hasLength(studentID), Score::getStudentId, studentID)
                .eq(StringUtils.hasLength(score), Score::getScore, score)
                .orderByDesc(Score::getScore);

        Page<Score> page = new Page<>(pageNo, pageSize);
        scoreService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);

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
