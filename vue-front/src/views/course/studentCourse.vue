<template>
  <div>
    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="myCourseList"
        stripe
        style="width: 100%">
        <el-table-column
          prop="teacherId"
          label="教师工号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="teacherName"
          label="教师姓名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="courseId"
          label="课程号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="courseName"
          label="课程名"
          width="150">
        </el-table-column>
        <el-table-column
          prop="creditPoints"
          label="学分"
          width="100">
        </el-table-column>
        <el-table-column
          prop="classSchedule"
          label="上课时间"
          width="200">
        </el-table-column>
        <el-table-column
          prop="classroomLocation"
          label="上课地点"
          width="120">
        </el-table-column>
        <el-table-column
          prop="departmentId"
          label="开设院系"
          width="120">
        </el-table-column>

      </el-table>
    </el-card>
  </div>
</template>

<script>
import stuAPI from "@/api/studentManage";
export default {
  data() {
    return {
      myCourseList: [],

    }
  },
  methods: {
    getMyCourseList(){
      const studentId = this.$store.state.user.name
      stuAPI.getMyCourseList(studentId).then(response => {
        this.myCourseList = response.data.courseList
      }).catch(error => {
        console.log(error)
      })
    },
  },
  created() {
    this.getMyCourseList()
  }
}
</script>

<style scoped>

</style>
