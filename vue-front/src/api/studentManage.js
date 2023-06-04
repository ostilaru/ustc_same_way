import request from '@/utils/request'

export default {
  getStudentList(searchModel) {
    return request({
      url: '/student/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        student_id: searchModel.student_id,
        name: searchModel.name,
        gender: searchModel.gender,
        department_id: searchModel.department_id,
        class_id: searchModel.class_id,
        contact_phone: searchModel.contact_phone,
        contact_email: searchModel.contact_email
      }
    })
  },

  getStudentById(student_id) {
    return request({
      url: `/student/${student_id}`,
      method: 'get'
    });
  },

  addStudent(student) {
    return request({
      url: '/student',
      method: 'post',
      data: student
    });
  },

  updateStudent(student) {
    return request({
      url: '/student',
      method: 'put',
      data: student
    });
  },

  saveStudent(student, title) {
    if(title === '新增学生'){
      return this.addStudent(student);
    }else{
      return this.updateStudent(student);
    }
  },

  deleteStudentById(student_id) {
    return request({
      url: `/student/${student_id}`,
      method: 'delete'
    });
  }

}
