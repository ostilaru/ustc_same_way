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
          label="工号"
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


    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="22">
          <el-input v-model="searchModel.course_id" placeholder="课程号" clearable></el-input>
          <el-input v-model="searchModel.course_name" placeholder="课程名" clearable></el-input>
          <el-select v-model="searchModel.department_id" clearable placeholder="院系">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="searchModel.student_id" placeholder="学号" clearable></el-input>
          <el-input v-model="searchModel.student_name" placeholder="学生姓名" clearable></el-input>
          <el-button @click="getScoreList" type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>

        <el-col :span="2" align="right">
          <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" round>新增</el-button>

        </el-col>
      </el-row>
    </el-card>

    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="scoreList"
        stripe
        style="width: 100%">
        <el-table-column
          label="#"
          width="50">
          <template slot-scope="scope">
            <!-- (pageNo-1) * pageSize + index + 1 -->
            {{(searchModel.pageNo-1) * searchModel.pageSize + scope.$index + 1}}
          </template>
        </el-table-column>
        <el-table-column
          prop="courseId"
          label="课程号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="courseName"
          label="课程名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="teacherName"
          label="授课教师"
          width="120">
        </el-table-column>
        <el-table-column
          prop="departmentId"
          label="院系"
          width="100">
        </el-table-column>
        <el-table-column
          prop="studentId"
          label="学号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="studentName"
          label="学生姓名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="score"
          label="成绩"
          width="120">
        </el-table-column>
        <el-table-column
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button @click="openEditUI(scope.row.studentId)" type="primary" size="mini" icon="el-icon-edit" circle></el-button>
            <el-button @click="deleteScore(scope.row.studentId, scope.row.course_id)" type="danger" size="mini" icon="el-icon-delete" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 分页组件 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="searchModel.pageNo"
      :page-sizes="[5, 10, 20, 50]"
      :page-size="searchModel.pagesize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

  </div>
</template>

<script>
import myCourseApi from '@/api/teacherManage'
import allScoreAPI from "@/api/allScore";
export default {
  name: "myCourse",
  data() {
    return {
      searchModel: {
        course_id: '',
        course_name: '',
        student_id: '',
        student_name: '',
        teacher_name: '',
        department_id: '',
        score: '',
        pageNo: 1,
        pagesize: 10,
      },
      total: 0,
      title: '',
      scoreList: [],
      myCourseList: [],
      formLabelWidth: '130px',
      dialogFormVisible: false,
      options2: [{
        value: '1',
        label: '1（数学院）'
      }, {
        value: '2',
        label: '2（物理学院）'
      }, {
        value: '3',
        label: '3（医学院）'
      }, {
        value: '4',
        label: '4（计算机学院）'
      }, {
        value: '5',
        label: '5（音乐学院）'
      }],
      rules:{
        studentId: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { min: 11, max: 11, message: '长度在 11 个字符', trigger: 'blur' }
        ],
        studentName: [
          { required: true, message: '请输入学生姓名', trigger: 'blur' },
          { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
        ],
        teacherName: [
          { required: true, message: '请输入老师姓名', trigger: 'blur' },
          { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
        ]
      },
      myCourseForm: {
        teacherId: '',
        teacherName: '',
        courseId: '',
        courseName: '',
        creditPoints: '',
        classSchedule: '',
        classroomLocation: '',
        departmentId: '',
      },

    }
  },
  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getScoreList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getScoreList()
    },
    getMyCourseList(){
      const teacherId = this.$store.state.user.name
      myCourseApi.getMyCourseByTeacherId(teacherId).then(response => {
        this.myCourseList = response.data.courseList
      }).catch(error => {
        console.log(error)
      })
    },
    getMyCourseScoreList(){

    },
    openEditUI(student_id) {
      if(student_id == null){
        this.flag = false;
        this.title = "新增成绩单";
      }else{
        this.flag = true;
        this.title = "修改成绩单";
        console.log(student_id);
        // 根据student_id查询用户信息
        allScoreAPI.getStudentById(student_id).then(res => {
          this.studentForm = res.data;
        });
      }

      this.dialogFormVisible = true;
    },
    clearValidate(props = []) {
      const fields = props.length
        ? (typeof props === 'string'
            ? this.fields.filter(field => props === field.prop)
            : this.fields.filter(field => props.indexOf(field.prop) > -1)
        ) : this.fields;
      fields.forEach(field => {
        field.clearValidate();
      });
    },
    clearForm() {
      this.scoreForm = {
        courseId: '',
        courseName: '',
        studentId: '',
        studentName: '',
        teacherName: '',
        departmentId: '',
        score: '',
      };
      this.$refs.scoreFormRef.clearValidate();
    },
  },

  created() {
    this.getMyCourseList()
    this.getMyCourseScoreList()
  }
}
</script>

<style scoped>
#search .el-input {
  width: 125px !important;
  margin-right: 10px;
  margin-bottom: 5px;
}
</style>
