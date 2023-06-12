package com.lantu.sys.mapper;

import com.lantu.sys.entity.MyCourse;
import com.lantu.sys.entity.MyScore;
import com.lantu.sys.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public interface StudentMapper extends BaseMapper<Student> {

    public List<MyScore> getScoreByStudentId(String studentId);

    public List<MyCourse> getCourseByStudentId(String studentId);
}
