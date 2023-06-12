import request from '@/utils/request'
import store from "@/store";

export function loginRole(data) {
  // 检查用户名是否以 'PB' 开头
  if(data.username.startsWith('PB')){
    let requestData = {
      studentId: data.username,
      // password: data.password
    }
    // 调用学生登录接口
    return request({
      url: '/student/login',
      method: 'post',
      data: requestData
    });
  } else if (data.username.startsWith('TE')) {
    let requestData = {
      teacherId: data.username,
      // password: data.password
    }
    // 调用老师登录接口
    return request({
      url: '/teacher/login',
      method: 'post',
      data: requestData
    });
  } else {
    // 调用管理员登录接口
    return request({
      url: '/user/login',
      method: 'post',
      data
    })
  }
}

export function register(data) {
  console.log(data)
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function getStudentInfo(token) {
  return request({
    url: '/student/info',
    method: 'get',
    params: { token }
  })
}

export function stuLogout() {
  return request({
    url: '/student/logout',
    method: 'post'
  })
}

export function getTeacherInfo(token) {
  return request({
    url: '/teacher/info',
    method: 'get',
    params: { token }
  })
}

export function teaLogout() {
  return request({
    url: '/teacher/logout',
    method: 'post'
  })
}
