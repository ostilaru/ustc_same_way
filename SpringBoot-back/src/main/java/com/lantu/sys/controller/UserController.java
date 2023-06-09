package com.lantu.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.User;
import com.lantu.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author woowoo
 * @since 2023-03-02
 */
@RestController
@RequestMapping("/user")
// @CrossOrigin 跨域处理，但是每个控制器都需要加注解，有些麻烦
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public Result<List<User>> getAllUser() {
        List<User> list = userService.list();
        return Result.success(list, "查询成功");
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
//        System.out.println("已经调用login()");
        Map<String, Object> data = userService.login(user);
        if(data != null){
            System.out.println("登录成功");
            return Result.success(data);
        }
        return Result.fail(20002, "用户名或密码错误");
    }

//    @PostMapping("/register")

    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token) {
        // 根据token获取用户信息，redis
//        System.out.println("前端传来的token：" + token);
        Map<String, Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003, "token无效");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token) {
        // 根据token删除用户信息，redis
        userService.logout(token);
        return Result.success("退出成功");
    }

    // 分页查询
    @GetMapping("/list")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                               @RequestParam(value = "phone",required = false) String phone,
                                               @RequestParam(value = "pageNo") Long pageNo,
                                               @RequestParam(value = "email",required = false) String email,
                                                   @RequestParam(value = "pageSize") Long pageSize){
        // 需要传入条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(username), User::getUsername, username);
        queryWrapper.like(StringUtils.hasLength(phone), User::getPhone, phone);
        queryWrapper.like(StringUtils.hasLength(email), User::getEmail, email);
        queryWrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo, pageSize);
        userService.page(page, queryWrapper);

        Map<String, Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);
    }

    @PostMapping
    public Result<?> addUser(@RequestBody User user){
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Result.success("添加用户成功");
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        user.setPassword(null);
        userService.updateById(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteUserById(@PathVariable("id") Integer id){
        userService.removeById(id);
        return Result.success("删除用户成功");
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Result.success("注册成功");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("上传文件不能为空");
            }

            // 获取文件名
            String fileName = file.getOriginalFilename();
            System.out.println("图片名：" + fileName);

            // 获取文件的二进制数据
            byte[] fileData = file.getBytes();
            System.out.println("图片大小：" + fileData.length + "字节");

            // 保存文件
            // 保存文件到服务器的本地文件系统
            String filePath = "/Users/jianglei/somebook_and_course/team/ustc_same_way/SpringBoot-back/src/main/resources/imgs/" + fileName;
            File destFile = new File(filePath);
            file.transferTo(destFile);


            return ResponseEntity.ok("文件上传成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件上传失败");
        }
    }






//    @GetMapping("/{phone}")
//    public Result<?> getUserByPhone(@PathVariable("phone") String phone){
//        User user = userService.getUserByPhone(phone);
//        return Result.success(user);
//    }

}
