package com.lantu.sys.service;

import com.lantu.sys.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lantu.sys.entity.User;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
public interface IStudentService extends IService<Student> {

    Map<String, Object> studentLogin(Student student);

    Map<String, Object> getStudentInfo(String token);

    void logout(String token);
}
