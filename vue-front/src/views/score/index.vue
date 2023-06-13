<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="22">
          <el-input v-model="searchModel.course_id" placeholder="课程号" clearable></el-input>
          <el-input v-model="searchModel.course_name" placeholder="课程名" clearable></el-input>
          <el-input v-model="searchModel.teacher_name" placeholder="授课教师" clearable></el-input>
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
          <el-input v-model="searchModel.score" placeholder="分数" clearable></el-input>
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
          width="60">
          <template slot-scope="scope">
            <!-- (pageNo-1) * pageSize + index + 1 -->
            {{(searchModel.pageNo-1) * searchModel.pageSize + scope.$index + 1}}
          </template>
        </el-table-column>
        <el-table-column
          prop="courseId"
          label="课程号"
          width="100">
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
            <el-button @click="" type="danger" size="mini" icon="el-icon-delete" circle></el-button>
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
      <el-form :model="scoreForm" ref="ScoreFormRef" :rules="rules">
        <el-form-item label="课程号" prop="courseId" :label-width="formLabelWidth" >
          <el-input v-model="scoreForm.studentId" autocomplete="off" :disabled="flag" ></el-input>
        </el-form-item>
        <el-form-item label="课程名" prop="name" :label-width="formLabelWidth">
          <el-input v-model="scoreForm.courseName" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="授课教师" prop="teacherName" :label-width="formLabelWidth">
          <el-input v-model="scoreForm.teacherName" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="院系" prop="departmentName" :label-width="formLabelWidth">
          <el-select v-model="scoreForm.departmentName" clearable placeholder="请选择" :disabled="flag">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学号" prop="studentId" :label-width="formLabelWidth">
          <el-input v-model="scoreForm.studentId" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName" :label-width="formLabelWidth">
          <el-input v-model="scoreForm.studentName" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="成绩" prop="score" :label-width="formLabelWidth">
          <el-input v-model="scoreForm.score" autocomplete="off"></el-input>
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
import allScoreAPI from '@/api/allScore'

export default {
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
      scoreForm: {
        courseId: '',
        courseName: '',
        studentId: '',
        studentName: '',
        teacherName: '',
        departmentName: '',
        score: '',
      },
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
    getScoreList() {
      allScoreAPI.getScoreList(this.searchModel).then(res => {
        this.scoreList = res.data.rows
        this.total = res.data.total
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
          this.scoreForm = res.data.rows[0]
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
    saveScore(){
      // 先发出警告
      this.$confirm('此操作将永久修改'+ this.scoreForm.studentName +'的成绩, 是否继续?', '注意', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 先触发表单验证
        this.$refs.ScoreFormRef.validate((valid) => {
          if (valid) {
            // 为后端封装一个 Score 对象
            let ScoreToBack = {
              courseId: this.scoreForm.courseId,
              studentId: this.scoreForm.studentId,
              score: this.scoreForm.score
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
              this.getScoreList()
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
    deleteScore(score) {
      this.$confirm('是否删除该成绩单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        allScoreAPI.deleteScoreByStudentId_CourseId(score.student_id, score.course_id).then(response => {
          this.$message({
            message: response.message,
            type: 'success'
          })
          this.getScoreList()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  },

  created() {
    this.getScoreList()
  }
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
