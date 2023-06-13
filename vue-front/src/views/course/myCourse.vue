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
          <el-select v-model="searchModel.department_name" clearable placeholder="院系">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="searchModel.student_id" placeholder="学号" clearable></el-input>
          <el-input v-model="searchModel.student_name" placeholder="学生姓名" clearable></el-input>
          <el-button @click="getMyCourseScoreList" type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>

        <el-col :span="2" align="right">


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
          prop="departmentName"
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
            <el-button @click="openEditUI(scope.row.courseId, scope.row.studentId)" type="primary" size="mini" icon="el-icon-edit" circle></el-button>
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

    <!-- 用户信息编辑对话框 -->
    <el-dialog @close="clearForm" :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="myCourseScoreForm" ref="myCourseScoreFormRef" :rules="rules">
        <el-form-item label="课程号" prop="courseId" :label-width="formLabelWidth" >
          <el-input v-model="myCourseScoreForm.studentId" autocomplete="off" :disabled="flag" ></el-input>
        </el-form-item>
        <el-form-item label="课程名" prop="name" :label-width="formLabelWidth">
          <el-input v-model="myCourseScoreForm.courseName" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="授课教师" prop="teacherName" :label-width="formLabelWidth">
          <el-input v-model="myCourseScoreForm.teacherName" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="院系" prop="departmentName" :label-width="formLabelWidth">
          <el-select v-model="myCourseScoreForm.departmentName" clearable placeholder="请选择" :disabled="flag">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学号" prop="studentId" :label-width="formLabelWidth">
          <el-input v-model="myCourseScoreForm.studentId" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName" :label-width="formLabelWidth">
          <el-input v-model="myCourseScoreForm.studentName" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="成绩" prop="score" :label-width="formLabelWidth">
          <el-input v-model="myCourseScoreForm.score" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveScore">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import myCourseApi from '@/api/teacherManage'
import allScoreAPI from "@/api/allScore";
import teacherAPI from "@/api/teacherManage";
import {int} from "mockjs/src/mock/random/basic";

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
        department_name: '',
        score: '',
        pageNo: 1,
        pageSize: 10,
      },
      total: 0,
      title: '',
      flag: false,
      scoreList: [],
      myCourseList: [],
      formLabelWidth: '130px',
      dialogFormVisible: false,
      options2: [{
        value: '数学院',
        label: '数学院'
      }, {
        value: '物理学院',
        label: '物理学院'
      }, {
        value: '医学院',
        label: '医学院'
      }, {
        value: '计算机学院',
        label: '计算机学院'
      }, {
        value: '音乐学院',
        label: '音乐学院'
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
      myCourseScoreForm: {
        courseId: '',
        courseName: '',
        teacherName: '',
        departmentName: '',
        studentId: '',
        studentName: '',
        score: '',
      },

    }
  },
  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getMyCourseScoreList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getMyCourseScoreList()
    },
    getMyCourseList(){
      const teacherId = this.$store.state.user.name
      myCourseApi.getMyCourseByTeacherId(teacherId).then(response => {
        this.myCourseList = response.data.courseList
      }).catch(error => {
        console.log(error)
      })
    },
    saveScore(){
      // 先发出警告
      this.$confirm('此操作将永久修改'+ this.myCourseScoreForm.studentName +'的成绩, 是否继续?', '注意', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 先触发表单验证
        this.$refs.myCourseScoreFormRef.validate((valid) => {
          if (valid) {
            // 为后端封装一个 Score 对象
            let ScoreToBack = {
              courseId: this.myCourseScoreForm.courseId,
              studentId: this.myCourseScoreForm.studentId,
              score: this.myCourseScoreForm.score
            }
            // 提交请求给后台
            allScoreAPI.saveScore(ScoreToBack, this.title).then(response => {
              // 给出成功提示
              this.$message({
                message: response.message,
                type: 'success',
              });
              // 关闭窗口
              this.dialogFormVisible = false;
              // 刷新列表
              this.getMyCourseScoreList();
            })
          } else {
            console.log('error submit!!')
            return false
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消保存'
        });
      });
    },
    getTeacherName(){
      const teacher_id = this.$store.state.user.name
      teacherAPI.getTeacherNameById(teacher_id).then(response => {
        this.searchModel.teacher_name = response.message
        console.log(this.searchModel.teacher_name)
      }).catch(error => {
        console.log('获取教师姓名失败！' )
      })
    },
    getMyCourseScoreList(){
      console.log("调用了getMyCourseScoreList")
      allScoreAPI.getScoreList(this.searchModel).then(response => {
        this.scoreList = response.data.rows
        this.total = response.data.total
      }).catch(error => {
        console.log('获取成绩列表失败！' )
      })
    },
    openEditUI(course_id, student_id) {
      if(student_id == null){
        this.flag = false;
        this.title = "新增成绩单";
      }else{
        this.flag = true;
        this.title = "修改成绩单";
        // 根据student_id查询用户信息
        this.searchModel.course_id = course_id;
        this.searchModel.student_id = student_id;
        allScoreAPI.getScoreList(this.searchModel).then(res => {
          console.log(res.data)
          this.myCourseScoreForm = res.data.rows[0]
          console.log(this.myCourseScoreForm)
        });
        this.searchModel.course_id = '';
        this.searchModel.student_id = '';
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


    this.getTeacherName()

    this.getMyCourseList()

    // this.getMyCourseScoreList()

  },
  mounted() {
    setTimeout(() => {
      this.getMyCourseScoreList();
    }, 300); // 延迟1秒执行

  },
}
</script>

<style scoped>
#search .el-input {
  width: 125px !important;
  margin-right: 10px;
  margin-bottom: 5px;
}

#search .el-select {
  width: 125px !important;
  margin-right: 10px;
  margin-bottom: 5px;
}
</style>
