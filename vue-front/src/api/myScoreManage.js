import request from "@/utils/request";

export default {
  getMyScoreList(studentId) {
    return request({
      url: `/student/myscore`,
      method: 'get',
      params: {
        name: studentId
      }
    });
  }
}
