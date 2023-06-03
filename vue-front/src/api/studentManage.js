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
        department_id: searchModel.department_id,
        class_id: searchModel.class_id,
        contact_phone: searchModel.contact_phone,
        contact_email: searchModel.contact_email
      }
    })
  },


}
