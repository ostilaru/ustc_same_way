import request from "@/utils/request";

export default {
  getTeacherList(searchModel) {
    return request({
      url: "/teacher/list",
      method: "get",
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        teacher_id: searchModel.teacher_id,
        name: searchModel.teacher_name,
        gender: searchModel.gender,
        department_id: searchModel.department_id,
        contact_phone: searchModel.contact_phone,
        contact_email: searchModel.contact_email
      }
    });
  },

  getTeacherById(teacher_id) {
    return request({
      url: `/teacher/${teacher_id}`,
      method: "get"
    });
  },

  addTeacher(teacher) {
    return request({
      url: "/teacher",
      method: "post",
      data: teacher
    });
  },

  updateTeacher(teacher) {
    return request({
      url: "/teacher",
      method: "put",
      data: teacher
    });
  },

  saveTeacher(teacher, title) {
    if (title === "新增教师") {
      return this.addTeacher(teacher);
    } else {
      return this.updateTeacher(teacher);
    }
  },

  deleteTeacherById(teacher_id) {
    return request({
      url: `/teacher/${teacher_id}`,
      method: "delete"
    });
  }

}
