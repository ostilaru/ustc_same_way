<template>
  <div>
    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="myhealthList"
        stripe
        style="width: 100%">
        <el-table-column
          prop="studentId"
          label="学号"
          width="150">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="gender"
          label="性别"
          width="100">
        </el-table-column>
        <el-table-column
          prop="height"
          label="身高"
          width="150">
        </el-table-column>
        <el-table-column
          prop="weight"
          label="体重"
          width="100">
        </el-table-column>
        <el-table-column
          prop="vision"
          label="视力"
          width="120">
        </el-table-column>
        <el-table-column
          prop="lungCapacity"
          label="肺活量"
          width="120">
        </el-table-column>
        <el-table-column
          prop="examDate"
          label="体检日期"
          width="120">
        </el-table-column>


      </el-table>
    </el-card>
  </div>
</template>

<script>
import healthAPI from '@/api/physicalExamManage'
export default {
  name: "myCourse",
  data() {
    return {
      myhealthList: [],
      myhealthForm: {
        studentId: '',
        name: '',
        gender: '',
        height: '',
        weight: '',
        vision: '',
        lungCapacity: '',
        examDate: ''
      },
      total: 0,

    }
  },
  methods: {
    getMyHealthExam() {
      const studentId = this.$store.state.user.name;
      healthAPI.getPhysicalExamById(studentId).then(response => {
        const data = response.data;
        const myHealthExam = {
          studentId: data.studentId,
          name: data.name,
          gender: data.gender,
          height: data.height,
          weight: data.weight,
          vision: data.vision,
          lungCapacity: data.lungCapacity,
          examDate: data.examDate
        };
        this.myhealthList.push(myHealthExam);
      });
    }

  },

  created() {
    this.getMyHealthExam()
  }
}
</script>

<style scoped>

</style>
