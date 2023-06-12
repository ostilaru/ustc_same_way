package com.lantu.sys.mapper;

import com.lantu.sys.entity.MyCourse;
import com.lantu.sys.entity.Teacher;
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
public interface TeacherMapper extends BaseMapper<Teacher> {

    public List<MyCourse> getCourseByTeacherId(String teacherId);

}
