import request from '@/utils/request'

export default {
  getCourseList(searchModel) {
    return request({
      url: '/course/list',
      method: 'get',
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        course_id: searchModel.course_id,
        course_name: searchModel.course_name,
        credit_points: searchModel.credit_points,
        class_schedule: searchModel.class_schedule,
        classroom_location: searchModel.classroom_location,
        teacher_id: searchModel.teacher_id,
        department_id: searchModel.department_id,
      }
    })
  },

  getCourseById(course_id) {
    return request({
      url: `/course/${course_id}`,
      method: 'get'
    });
  },

  addCourse(course) {
    return request({
      url: '/course',
      method: 'post',
      data: course
    });
  },

  updateCourse(course) {
    return request({
      url: '/course',
      method: 'put',
      data: course
    });
  },

  saveCourse(course, title) {
    if(title === '新增课程'){
      return this.addCourse(course);
    }else{
      return this.updateCourse(course);
    }
  },

  deleteCourseById(course_id) {
    return request({
      url: `/course/${course_id}`,
      method: 'delete'
    });
  }

}
