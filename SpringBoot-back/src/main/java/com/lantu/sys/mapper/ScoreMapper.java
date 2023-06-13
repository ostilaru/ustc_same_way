package com.lantu.sys.mapper;

import com.lantu.sys.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lantu.sys.entity.ScoreForAll;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public interface ScoreMapper extends BaseMapper<Score> {
    public List<Score> getScoreByStudentId(String studentId);

    List<Score> getScoreByCourseId(String courseId);

    List<ScoreForAll> getScoreByStudentName(String studentName);

    List<ScoreForAll> getScoreByTeacherName(String teacherName);

    List<ScoreForAll> getScoreList(
            @Param("start") Long start,
            @Param("limit") Long limit,
            @Param("courseId") String courseId,
            @Param("courseName") String courseName,
            @Param("studentId") String studentId,
            @Param("studentName") String studentName,
            @Param("teacherName") String teacherName,
            @Param("departmentName") String departmentName,
            @Param("score") String score
    );

    Integer getScoreCount(
            @Param("courseId") String courseId,
            @Param("courseName") String courseName,
            @Param("studentId") String studentId,
            @Param("studentName") String studentName,
            @Param("teacherName") String teacherName,
            @Param("departmentName") String departmentName,
            @Param("score") String score
    );

    void updateScore(String studentId, Integer courseId, Integer score);
}
