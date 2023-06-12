package com.lantu.sys.mapper;

import com.lantu.sys.entity.Score;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lantu.sys.entity.ScoreForAll;

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
}
