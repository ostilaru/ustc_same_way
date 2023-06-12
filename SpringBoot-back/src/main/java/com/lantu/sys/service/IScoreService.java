package com.lantu.sys.service;

import com.lantu.sys.entity.Score;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public interface IScoreService extends IService<Score> {
    Map<String, Object> getScoreByStudentId(String studentId);
    Map<String, Object> getScoreByCourseId(String courseId);
    Map<String, Object> getScoreByStudentName(String studentName);
    Map<String, Object> getScoreByTeacherName(String teacherName);
    Map<String, Object> getScoreByStudentIdAndCourseId(String studentId, String courseId);

    Map<String, Object> getScoreByCourseName(String studentId, String courseName);



    Map<String, Object> getScoreByStudentNameAndCourseName(String studentName, String courseName);

    Map<String, Object> getScoreByTeacherId(String studentId, String studentName);



}
