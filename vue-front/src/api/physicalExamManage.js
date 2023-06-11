import request from "@/utils/request";

export default {
  getPhysicalExamList(searchModel) {
    return request({
      url: "/physicalExam/list",
      method: "get",
      params: {
        pageNo: searchModel.pageNo,
        pageSize: searchModel.pageSize,
        student_id: searchModel.student_id,
        name: searchModel.name,
        gender: searchModel.gender,
        height: searchModel.height,
        weight: searchModel.weight,
        vision: searchModel.vision,
        lung_capacity: searchModel.lung_capacity,
        exam_date: searchModel.exam_date,
      }
    });
  },

  getPhysicalExamById(student_id) {
    return request({
      url: `/physicalExam/${student_id}`,
      method: "get"
    });
  },

  addPhysicalExam(physicalExam) {
    return request({
      url: "/physicalExam",
      method: "post",
      data: physicalExam
    });
  },

  updatePhysicalExam(physicalExam) {
    return request({
      url: "/physicalExam",
      method: "put",
      data: physicalExam
    });
  },

  savePhysicalExam(physicalExam, title) {
    if (title === "新增体检表") {
      return this.addPhysicalExam(physicalExam);
    } else {
      return this.updatePhysicalExam(physicalExam);
    }
  },

  deletePhysicalExamById(student_id) {
    return request({
      url: `/physicalExam/${student_id}`,
      method: "delete"
    });
  }
}
