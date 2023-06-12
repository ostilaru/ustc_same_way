<template>
  <div>
    <el-button @click="getMyScoreList" type="primary" round icon="el-icon-search">查询</el-button>
    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="myscoreList"
        stripe
        style="width: 100%">
        <el-table-column
          prop="studentId"
          label="学号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="studentName"
          label="姓名"
          width="100">
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
          prop="score"
          label="成绩"
          width="120">
        </el-table-column>


      </el-table>
    </el-card>
  </div>
</template>

<script>
import myscoreApi from '@/api/myScoreManage'
export default {
  name: "myCourse",
  data() {
    return {
      myscoreList: [],
      myscoreForm: {
        studentId: '',
        studentName: '',
        courseId: '',
        courseName: '',
        creditPoint: '',
        score: '',
      },
      total: 0,

    }
  },
  methods: {
    getMyScoreList(){
      const studentId = this.$store.state.user.name
      myscoreApi.getMyScoreList(studentId).then(res => {
        this.myscoreList = res.data.scoreList
        // this.total = res.data.scoreList.length
      })
    }
  },

  created() {
    this.getMyScoreList()
  }
}
</script>

<style scoped>

</style>
