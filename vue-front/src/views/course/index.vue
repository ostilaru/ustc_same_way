<template>
  <div>
    <!--  <div>-->
    <!--    学生管理（开发中）这里直接展示学生表（学号，姓名，性别，年龄，院系，班级，注册时间，毕业时间，联系电话，联系邮箱，在校状态）-->
    <!--  </div>-->
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="22">
          <el-input v-model="searchModel.course_id" placeholder="课程号" clearable></el-input>
          <el-input v-model="searchModel.course_name" placeholder="课程名" clearable></el-input>
          <el-input v-model="searchModel.credit_points" placeholder="学分" clearable></el-input>
          <el-input v-model="searchModel.class_schedule" placeholder="上课时间" clearable></el-input>
          <el-input v-model="searchModel.classroom_location" placeholder="上课地点" clearable></el-input>
          <el-input v-model="searchModel.teacher_id" placeholder="授课教师" clearable></el-input>
          <el-select v-model="searchModel.department_id" clearable placeholder="开课院系">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button @click="getCourseList" type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>

        <el-col :span="2" align="right">
          <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" round>新增</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="courseList"
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
          width="80">
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
          prop="teacherId"
          label="授课教师"
          width="120">
        </el-table-column>
        <el-table-column
          prop="departmentId"
          label="开课院系"
          width="120">
        </el-table-column>
        <el-table-column
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button @click="openEditUI(scope.row.courseId)" type="primary" size="mini" icon="el-icon-edit" circle></el-button>
            <el-button @click="deleteCourse(scope.row)" type="danger" size="mini" icon="el-icon-delete" circle></el-button>
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
      <el-form :model="courseForm" ref="courseFormRef" :rules="rules">
        <el-form-item label="课程号" prop="courseId" :label-width="formLabelWidth" >
          <el-input v-model="courseForm.courseId" autocomplete="off" :disabled="flag"></el-input>
        </el-form-item>
        <el-form-item label="课程名" prop="courseName" :label-width="formLabelWidth">
          <el-input v-model="courseForm.courseName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="学分" prop="creditPoints" :label-width="formLabelWidth">
          <el-input v-model="courseForm.creditPoints" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="上课时间" prop="classSchedule" :label-width="formLabelWidth">
          <el-input v-model="courseForm.classSchedule" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="上课地点" prop="classroomLocation" :label-width="formLabelWidth">
          <el-input v-model="courseForm.classroomLocation" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="授课教师" prop="teacherId" :label-width="formLabelWidth">
          <el-input v-model="courseForm.teacherId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="开课院系" prop="departmentId" :label-width="formLabelWidth">
          <el-select v-model="courseForm.departmentId" clearable placeholder="请选择">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveCourse">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
import courseApi from '@/api/courseManage'

export default {
  data() {
    var checkEmail = (rule, value, callback) => {
      var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
      if(!reg.test(value)){
        return callback(new Error('邮箱格式错误'));
      }
      callback();
    };
    return {
      searchModel: {
        course_id: '',
        course_name: '',
        credit_points: '',
        class_schedule: '',
        classroom_location: '',
        teacher_id: '',
        department_id: '',
        pageNo: 1,
        pageSize: 10
      },
      courseList: [],
      total: 0,
      courseForm: {
        courseId: '',
        courseName: '',
        creditPoints: '',
        classSchedule: '',
        classroomLocation: '',
        teacherId: '',
        departmentId: ''
      },
      title: '',
      flag: false,
      formLabelWidth:'130px',
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
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
        ],
        contactPhone: [
          { required: true, message: '请输入联系电话', trigger: 'blur' },
          { min: 11, max: 11, message: '长度为11个字符', trigger: 'blur' }
        ],
        contactMail: [
          { required: true, message: '请输入电子邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur'}
        ]
      }

    }
  },

  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getCourseList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getCourseList()
    },
    getCourseList() {
      courseApi.getCourseList(this.searchModel).then(res => {
        this.courseList = res.data.rows
        this.total = res.data.total
      })
    },
    openEditUI(course_id) {
      if(course_id == null){
        this.flag = false;
        this.title = "新增课程";
      }else{
        this.flag = true;
        this.title = "修改课程";
        // console.log(student_id);
        // 根据student_id查询用户信息
        courseApi.getCourseById(course_id).then(res => {
          this.courseForm = res.data;
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
      this.courseForm = {
        courseId: '',
        courseName: '',
        creditPoints: '',
        classSchedule: '',
        classroomLocation: '',
        teacherId: '',
        departmentId: ''
      };
      this.$refs.courseFormRef.clearValidate();
    },

    saveCourse(){
      // 先触发表单验证
      this.$refs.courseFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          courseApi.saveCourse(this.courseForm, this.title).then(response => {
            // 给出成功提示
            this.$message({
              message: response.message,
              type: 'success'
            });
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格
            this.getCourseList();
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      // 提交请求给后台
    },

    deleteCourse(course) {
      this.$confirm('确认要删除课程' + course.courseName + '吗?', '提示', {
        type: 'warning'
      }).then(() => {
        courseApi.deleteCourseById(course.courseId).then(response => {
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.getCourseList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

  },


  created() {
    this.getCourseList()
  }

}
</script>

<style>
#search .el-input {
  width: 125px !important;
  margin-right: 10px;
  margin-bottom: 5px;
}

</style>
