import request from "@/utils/request";
import {title} from "@/settings";

export default {
  getScoreList(searchModel) {
    return request({
      url: "/score/list",
      method: "get",
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        course_id: searchModel.course_id,
        course_name: searchModel.course_name,
        teacher_name: searchModel.teacher_name,
        department_name: searchModel.department_name,
        student_id: searchModel.student_id,
        student_name: searchModel.student_name,
        score: searchModel.score,
      }
    });
  },

  getAllScore() {
    return request({
      url: "/score/all",
      method: "get",
    });
  },


  getScoreByStudentId_CourseId(student_id, course_id) {
    return request({
      url: "/score/student_id/" + student_id + "/course_id/" + course_id,
      method: "get",
    });
  },

  addScore(score) {
    return request({
      url: "/score",
      method: "post",
      data: score
    });
  },

  updateScore(score) {
    return request({
      url: "/score",
      method: "put",
      data: score
    });
  },

  saveScore(score, title) {
    if (title === '新增成绩单') {
      return this.addScore(score);
    } else {
      return this.updateScore(score);
    }
  },

  deleteScoreByStudentId_CourseId(student_id, course_id) {
    return request({
      url: "/score/student_id/" + student_id + "/course_id/" + course_id,
      method: "delete",
    });
  },

}
