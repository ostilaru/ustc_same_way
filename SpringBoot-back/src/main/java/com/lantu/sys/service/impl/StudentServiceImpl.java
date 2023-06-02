package com.lantu.sys.service.impl;

import com.lantu.sys.entity.Student;
import com.lantu.sys.mapper.StudentMapper;
import com.lantu.sys.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author woowoo
 * @since 2023-06-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
