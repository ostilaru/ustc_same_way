<template>
  <div>
    <!--  <div>-->
    <!--    学生管理（开发中）这里直接展示学生表（学号，姓名，性别，年龄，院系，班级，注册时间，毕业时间，联系电话，联系邮箱，在校状态）-->
    <!--  </div>-->
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="22">
          <el-input v-model="searchModel.student_id" placeholder="学号" clearable></el-input>
          <el-input v-model="searchModel.name" placeholder="姓名" clearable></el-input>
          <el-select v-model="searchModel.gender" clearable placeholder="性别">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-select v-model="searchModel.department_id" clearable placeholder="院系">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="searchModel.class_id" placeholder="班级号" clearable></el-input>
          <el-input v-model="searchModel.contact_phone" placeholder="电话" clearable></el-input>
          <el-input v-model="searchModel.contact_email" placeholder="邮箱" clearable></el-input>
          <el-button @click="getStudentList" type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>

        <el-col :span="2" align="right">
          <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" round>新增</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="studentList"
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
          prop="studentId"
          label="学号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          width="80">
        </el-table-column>
        <el-table-column
          prop="gender"
          label="性别"
          width="60">
        </el-table-column>
        <el-table-column
          prop="departmentId"
          label="院系"
          width="70">
        </el-table-column>
        <el-table-column
          prop="classId"
          label="班级"
          width="80">
        </el-table-column>
        <el-table-column
          prop="enrollmentDate"
          label="注册时间"
          width="110">
        </el-table-column>
        <el-table-column
          prop="graduationDate"
          label="毕业时间"
          width="110">
        </el-table-column>
        <el-table-column
          prop="contactPhone"
          label="联系电话"
          width="120">
        </el-table-column>
        <el-table-column
          prop="contactMail"
          label="联系邮箱"
          width="200">
        </el-table-column>
        <el-table-column
          prop="status"
          label="在校状态"
          width="80">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == '在校'" type="success" effect="light">在校</el-tag>
            <el-tag v-else type="danger" effect="light">休学</el-tag>
          </template>
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
            v-model="studentForm.enrollmentDate"
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
import stuApi from '@/api/studentManage'

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
        student_id: '',
        name: '',
        gender: '',
        department_id: '',
        class_id: '',
        contact_phone: '',
        contact_email: '',
        pageNo: 1,
        pageSize: 10
      },
      studentList: [],
      total: 0,
      studentForm: {
        studentId: '',
        name: '',
        gender: '',
        departmentId: '',
        classId: '',
        enrollmentDate: '',
        graduationDate: '',
        contactPhone: '',
        contactMail: '',
        status: ''
      },
      title: '',
      flag: false,
      formLabelWidth:'130px',
      dialogFormVisible: false,
      options1: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],
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
      this.getStudentList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getStudentList()
    },
    getStudentList() {
      stuApi.getStudentList(this.searchModel).then(res => {
        this.studentList = res.data.rows
        this.total = res.data.total
      })
    },
    openEditUI(student_id) {
      if(student_id == null){
        this.flag = false;
        this.title = "新增学生";
      }else{
        this.flag = true;
        this.title = "修改学生";
        console.log(student_id);
        // 根据student_id查询用户信息
        stuApi.getStudentById(student_id).then(res => {
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
      this.studentForm = {
        student_id: '',
        name: '',
        gender: '',
        department_id: '',
        class_id: '',
        contact_phone: '',
        contact_email: '',
      };
      this.$refs.studentFormRef.clearValidate();
    },
    updateClassId() {
      if(this.studentForm.studentId.length === 11){
        this.studentForm.classId = this.studentForm.studentId.substring(2,8);
      }
    },

    saveStudent(){
      // 先触发表单验证
      this.$refs.studentFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          stuApi.saveStudent(this.studentForm, this.title).then(response => {
            // 给出成功提示
            this.$message({
              message: response.message,
              type: 'success'
            });
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格
            this.getStudentList();
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      // 提交请求给后台
    },

    deleteStudent(student) {
      this.$confirm('确认要开除学生' + student.name + '吗?', '提示', {
        type: 'warning'
      }).then(() => {
        stuApi.deleteStudentById(student.studentId).then(response => {
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.getStudentList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消开除'
        });
      });
    },

  },


  created() {
    this.getStudentList()
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
