package com.lantu.sys.service;

import com.lantu.sys.entity.Teacher;
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
public interface ITeacherService extends IService<Teacher> {

    Map<String, Object> teacherLogin(Teacher tea);

    Map<String, Object> getTeacherInfo(String token);

    void logout(String token);

    Map<String, Object> getMyCourseByTeacherId(String teacherId);
}
