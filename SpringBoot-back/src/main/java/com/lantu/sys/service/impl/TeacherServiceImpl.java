package com.lantu.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.sys.entity.MyCourse;
import com.lantu.sys.entity.Student;
import com.lantu.sys.entity.Teacher;
import com.lantu.sys.mapper.TeacherMapper;
import com.lantu.sys.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public Map<String, Object> teacherLogin(Teacher tea) {
        System.out.println("正在调用teacherLogin方法");
        // 根据用户名和密码查询
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teacher::getTeacherId, tea.getTeacherId());

        Teacher loginTeacher = this.baseMapper.selectOne(queryWrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
        if(loginTeacher != null){
            // 暂时用UUID，后面会用jwt
            String key = "teacher:" + UUID.randomUUID();

            // 将用户信息存入redis
            redisTemplate.opsForValue().set(key, loginTeacher, 30, TimeUnit.MINUTES);// 有效时间为30分钟

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            data.put("name", loginTeacher.getTeacherId());
            data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            data.put("roleType", "teacher");
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getTeacherInfo(String token) {
        System.out.println("正在调用getTeacherInfo方法");
        // 根据token获取用户信息
        Object obj = redisTemplate.opsForValue().get(token);
        // 拿出来做反序列化
        if (obj != null) {
            Teacher LoginTeacher = JSON.parseObject(JSON.toJSONString(obj), Teacher.class);
            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("name", LoginTeacher.getTeacherId());
            data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            data.put("roleType", "teacher");
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        System.out.println("正在调用teacher的logout方法");
        // 删除redis中的token
        redisTemplate.delete(token);
    }

    @Override
    public Map<String, Object> getMyCourseByTeacherId(String teacherId) {
        System.out.println("正在调用getMyCourseByTeacherId方法");
        // 根据teacherId查询
        List<MyCourse> courseList = this.baseMapper.getCourseByTeacherId(teacherId);
        // 返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("courseList", courseList);
        return data;
    }
}
