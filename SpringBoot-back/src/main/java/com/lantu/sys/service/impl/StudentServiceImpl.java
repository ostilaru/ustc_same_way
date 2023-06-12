package com.lantu.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.sys.entity.MyScore;
import com.lantu.sys.entity.Student;
import com.lantu.sys.mapper.StudentMapper;
import com.lantu.sys.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> studentLogin(Student student) {
        System.out.println("正在调用studentLogin方法");
        // 根据用户名和密码查询
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getStudentId, student.getStudentId());

        Student loginStudent = this.baseMapper.selectOne(queryWrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
        if(loginStudent != null){
            // 暂时用UUID，后面会用jwt
            String key = "student:" + UUID.randomUUID();

            // 将用户信息存入redis
            redisTemplate.opsForValue().set(key, loginStudent, 30, TimeUnit.MINUTES);// 有效时间为30分钟

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            data.put("name", loginStudent.getStudentId());
            data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            data.put("roleType", "student");
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getStudentInfo(String token) {
        System.out.println("正在调用getStudentInfo方法");
        // 根据token获取用户信息
        Object obj = redisTemplate.opsForValue().get(token);
        // 拿出来做反序列化
        if (obj != null) {
            Student Loginstudent = JSON.parseObject(JSON.toJSONString(obj), Student.class);
            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("name", Loginstudent.getStudentId());
            data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            data.put("roleType", "student");
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        System.out.println("正在调用student的logout方法");
        // 删除redis中的token
        redisTemplate.delete(token);
    }

    @Override
    public Map<String, Object> getMyScoreById(String studentId) {
        System.out.println("正在调用getMyScoreById方法");
        // 根据学号查询成绩
        List<MyScore> scoreList = this.baseMapper.getScoreByStudentId(studentId);
        // 返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("scoreList", scoreList);
        return data;
    }

}
