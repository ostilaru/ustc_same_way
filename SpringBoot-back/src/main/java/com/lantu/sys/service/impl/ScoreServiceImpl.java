package com.lantu.sys.service.impl;

import com.lantu.sys.entity.Score;
import com.lantu.sys.entity.ScoreForAll;
import com.lantu.sys.mapper.ScoreMapper;
import com.lantu.sys.service.IScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {
    @Override
    public Map<String, Object> getScoreByStudentId(String studentId) {
        System.out.println("正在调用getMyScoreByStudentId方法");
        // 根据学号查询成绩
        List<Score> scoreList = this.baseMapper.getScoreByStudentId(studentId);
        // 返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("scoreList", scoreList);
        return data;
    }

    @Override
    public Map<String, Object> getScoreByCourseId(String courseId) {
        System.out.println("正在调用getScoreByCourseId方法");
        // 根据课程号查询成绩
        List<Score> scoreList = this.baseMapper.getScoreByCourseId(courseId);
        // 返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("scoreList", scoreList);
        return data;
    }

    @Override
    public Map<String, Object> getScoreByStudentIdAndCourseId(String studentId, String courseId) {
       return null;
    }

    @Override
    public Map<String, Object> getScoreByCourseName(String studentId, String courseName) {
        return null;
    }

    @Override
    public Map<String, Object> getScoreByStudentName(String studentName) {
       List<ScoreForAll> scoreForAllList = this.baseMapper.getScoreByStudentName(studentName);
         Map<String, Object> data = new HashMap<>();
        data.put("scoreForAllList", scoreForAllList);
        return data;
    }

    @Override
    public Map<String, Object> getScoreByStudentNameAndCourseName(String studentName, String courseName) {
        return null;
    }

    @Override
    public Map<String, Object> getScoreByTeacherId(String studentId, String studentName) {
        return null;
    }

    @Override
    public Map<String, Object> getScoreByTeacherName(String teacherName) {
        List<ScoreForAll> scoreForAllList = this.baseMapper.getScoreByTeacherName(teacherName);
        Map<String, Object> data= new HashMap<>();
        data.put("scoreForAllList", scoreForAllList);
        return data;
    }


}
