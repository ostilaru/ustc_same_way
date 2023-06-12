<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="22">
          <el-input v-model="searchModel.course_id" placeholder="课程号" clearable></el-input>
          <el-input v-model="searchModel.course_name" placeholder="课程名" clearable></el-input>
          <el-input v-model="searchModel.teacher_name" placeholder="授课教师" clearable></el-input>
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
          width="100">
        </el-table-column>
        <el-table-column
          prop="departmentId"
          label="院系"
          width="70">
        </el-table-column>
        <el-table-column
          prop="studentId"
          label="学号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="studentName"
          label="学生姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="score"
          label="成绩"
          width="100">
        </el-table-column>
        <el-table-column
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button @click="openEditUI(scope.row.studentId)" type="primary" size="mini" icon="el-icon-edit" circle></el-button>
            <el-button @click="deleteStudent(scope.row)" type="danger" size="mini" icon="el-icon-delete" circle></el-button>
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
      <el-form :model="studentForm" ref="studentFormRef" :rules="rules">
        <el-form-item label="学号" prop="studentId" :label-width="formLabelWidth" >
          <el-input v-model="studentForm.studentId" autocomplete="off" @change="updateClassId" :disabled="flag" placeholder="PBxxxxxxxxx"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
          <el-input v-model="studentForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓别" prop="gender" :label-width="formLabelWidth">
          <el-select v-model="studentForm.gender" clearable placeholder="请选择">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="院系号" prop="departmentId" :label-width="formLabelWidth">
          <el-select v-model="studentForm.departmentId" clearable placeholder="请选择">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级号" prop="classId" :label-width="formLabelWidth">
          <el-input v-model="studentForm.classId" autocomplete="off" placeholder="PB后的6位数字为班级号"></el-input>
        </el-form-item>
        <el-form-item label="注册时间" prop="enrollmentDate" :label-width="formLabelWidth">
          <el-date-picker
            v-model="studentForm.graduationDate"
            type="date"
            placeholder="选择日期"
            :disabled="flag"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="毕业时间" prop="graduationDate" :label-width="formLabelWidth">
          <el-date-picker
            v-model="studentForm.graduationDate"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone" :label-width="formLabelWidth">
          <el-input v-model="studentForm.contactPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系邮箱" prop="contactMail" :label-width="formLabelWidth">
          <el-input v-model="studentForm.contactMail" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="在校状态" :label-width="formLabelWidth">
          <el-switch
            v-model="studentForm.status"
            :active-value="'在校'"
            :inactive-value="'休学'"
            active-color="#13ce66"
          >
          </el-switch>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveStudent">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import stuApi from "@/api/studentManage";

export default {
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
      scoreList: [],
      scoreForm: {
        courseId: '',
        courseName: '',
        studentId: '',
        studentName: '',
        teacherName: '',
        departmentId: '',
        score: '',
      },
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

    },
    openEditUI(student_id) {
      console.log(student_id);
      // 根据student_id查询用户信息
      stuApi.getStudentById(student_id).then(res => {
        this.studentForm = res.data;
      });
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
    saveScore() {
      this.$refs.scoreFormRef.validate((valid) => {
        if (valid) {
          stuApi.saveStudent(this.scoreForm).then(res => {
            this.$message({
              message: res.message,
              type: 'success'
            });
            this.dialogFormVisible = false;
            this.getScoreList();
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
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

</style>
