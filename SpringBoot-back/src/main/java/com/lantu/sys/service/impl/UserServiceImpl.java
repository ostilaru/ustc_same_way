package com.lantu.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.sys.entity.Student;
import com.lantu.sys.entity.Teacher;
import com.lantu.sys.entity.User;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.service.IUserService;
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
 * @since 2023-03-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(User user) {
        System.out.println("正在调用login方法");
        // 根据用户名和密码查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());

        User loginUser = this.baseMapper.selectOne(queryWrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
        if(loginUser != null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())){
            // 暂时用UUID，后面会用jwt
            String key = "user:" + UUID.randomUUID();

            // 将用户信息存入redis
            loginUser.setPassword(null);

            // 添加一个 roleType 属性
            redisTemplate.opsForValue().set(key, loginUser, 30, TimeUnit.MINUTES);// 有效时间为30分钟

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token", key);
            data.put("name", loginUser.getUsername());
            data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            data.put("roleType", "user");
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        System.out.println("正在调用getUserInfo方法");
        // 根据token获取用户信息，redis  (本质类似于hashmap)
        Object obj = redisTemplate.opsForValue().get(token);

        String roleType = "";
        // 首先要获取token的首字母来判断是user，teacher， student
        if (token.startsWith("user:")) {
            System.out.println("user");
            roleType = "user";
        } else if (token.startsWith("teacher:")) {
            System.out.println("teacher");
            roleType = "teacher";
        } else if (token.startsWith("student:")) {
            System.out.println("student");
            roleType = "student";
        } else {
            System.out.println("error");
        }

        // 拿出来需要做反序列化
        if(obj != null){
            // 根据 roleType 来进行反序列化
            if (roleType.equals("user")) {
                // 返回数据
                User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);// 先转化为JSON字符串，再parse反序列为user对象
                Map<String, Object> data = new HashMap<>();
                data.put("name", loginUser.getUsername());
                data.put("avatar", loginUser.getAvatar());
                data.put("roleType", "user");
                return data;
            } else if (roleType.equals("teacher")) {
                Teacher loginTeacher = JSON.parseObject(JSON.toJSONString(obj), Teacher.class);
                Map<String, Object> data = new HashMap<>();
                data.put("name", loginTeacher.getTeacherName());
                data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
                data.put("roleType", "teacher");
                return data;
            } else if (roleType.equals("student")) {
                Student loginStudent = JSON.parseObject(JSON.toJSONString(obj), Student.class);
                Map<String, Object> data = new HashMap<>();
                data.put("name", loginStudent.getName());
                data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
                data.put("roleType", "student");
                return data;
            }

        }
        return null;
    }

    @Override
    public void logout(String token) {
        // 根据token删除用户信息，redis
        redisTemplate.delete(token);
    }

    @Override
    public User getUserByPhone(String phone) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User user = this.baseMapper.selectOne(queryWrapper);
        return user;
    }
}
