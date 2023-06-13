<template>
<!--  <div>-->
<!--    教师管理（开发中），这里直接展示教师表（教师号，教师姓名，教师性别，教师院系，联系电话，联系邮箱）-->
<!--  </div>-->
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="22">
          <el-input v-model="searchModel.teacher_id" placeholder="工号" clearable></el-input>
          <el-input v-model="searchModel.teacher_name" placeholder="姓名" clearable></el-input>
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
          <el-input v-model="searchModel.contact_phone" placeholder="电话" clearable></el-input>
          <el-input v-model="searchModel.contact_email" placeholder="邮箱" clearable></el-input>
          <el-button @click="getTeacherList" type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>

        <el-col :span="2" align="right">
          <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" round>新增</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="teacherList"
        stripe
        style="width: 100%">
        <el-table-column
          label="#"
          width="80">
          <template slot-scope="scope">
            <!-- (pageNo-1) * pageSize + index + 1 -->
            {{(searchModel.pageNo-1) * searchModel.pageSize + scope.$index + 1}}
          </template>
        </el-table-column>
        <el-table-column
          prop="teacherId"
          label="工号"
          width="120">
        </el-table-column>
        <el-table-column
          prop="teacherName"
          label="姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="gender"
          label="性别"
          width="100">
        </el-table-column>
        <el-table-column
          prop="departmentId"
          label="院系"
          width="100">
        </el-table-column>
        <el-table-column
          prop="contactPhone"
          label="联系电话"
          width="150">
        </el-table-column>
        <el-table-column
          prop="contactMail"
          label="联系邮箱"
          width="200">
        </el-table-column>
        <el-table-column
          prop="status"
          label="在职状态"
          width="150">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status == '在校'" type="success" effect="light">休假</el-tag>
            <el-tag v-else type="success" effect="light">在职</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="150">
          <template slot-scope="scope">
            <el-button @click="openEditUI(scope.row.teacherId)" type="primary" size="mini" icon="el-icon-edit" circle></el-button>
            <el-button @click="deleteTeacher(scope.row)" type="danger" size="mini" icon="el-icon-delete" circle></el-button>
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
      <el-form :model="teacherForm" ref="teacherFormRef" :rules="rules">
        <el-form-item label="工号" prop="teacherId" :label-width="formLabelWidth" >
          <el-input v-model="teacherForm.teacherId" autocomplete="off" :disabled="flag" placeholder="TExxxx"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="teacherName" :label-width="formLabelWidth">
          <el-input v-model="teacherForm.teacherName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓别" prop="gender" :label-width="formLabelWidth">
          <el-select v-model="teacherForm.gender" clearable placeholder="请选择">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="院系号" prop="departmentId" :label-width="formLabelWidth">
          <el-select v-model="teacherForm.departmentId" clearable placeholder="请选择">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone" :label-width="formLabelWidth">
          <el-input v-model="teacherForm.contactPhone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系邮箱" prop="contactMail" :label-width="formLabelWidth">
          <el-input v-model="teacherForm.contactMail" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="在职状态" :label-width="formLabelWidth">
          <el-switch
            v-model="teacherForm.status"
            :active-value="'在校'"
            :inactive-value="'休假'"
            active-color="#13ce66"
          >
          </el-switch>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveTeacher">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import teacherApi from '@/api/teacherManage'

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
        teacher_id: '',
        teacher_name: '',
        gender: '',
        department_id: '',
        contact_phone: '',
        contact_mail: '',
        pageNo: 1,
        pageSize: 10
      },
      teacherList: [],
      total: 0,
      teacherForm: {
        teacherId: '',
        teacherName: '',
        gender: '',
        departmentId: '',
        contactPhone: '',
        contactMail: '',
        status: ''
      },
      title: '',
      flag: false,
      formLabelWidth: '130px',
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

      rules: {
        teacherId: [
          { required: true, message: '请输入工号', trigger: 'blur' },
          { min: 6, max: 6, message: '长度为6个字符', trigger: 'blur' }
        ],
        teacherName: [
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
      this.getTeacherList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getTeacherList()
    },
    getTeacherList() {
      teacherApi.getTeacherList(this.searchModel).then(res => {
        this.teacherList = res.data.rows
        this.total = res.data.total
      })
    },
    openEditUI(teacher_id) {
      if(teacher_id == null){
        this.flag = false;
        this.title = "新增教师";
      }else{
        this.flag = true;
        this.title = "修改教师";
        // 根据teacher_id查询用户信息
        teacherApi.getTeacherById(teacher_id).then(res => {
          this.teacherForm = res.data;
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
      this.teacherForm = {
        teacher_id: '',
        teacher_name: '',
        gender: '',
        department_id: '',
        class_id: '',
        contact_phone: '',
        contact_email: '',
      };
      this.$refs.teacherFormRef.clearValidate();
    },

    saveTeacher(){
      // 先触发表单验证
      this.$refs.teacherFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          teacherApi.saveTeacher(this.teacherForm, this.title).then(response => {
            // 给出成功提示
            this.$message({
              message: response.message,
              type: 'success'
            });
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格
            this.getTeacherList();
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      // 提交请求给后台
    },

    deleteTeacher(teacher) {
      this.$confirm('确认要辞退教师' + teacher.teacherName + '吗?', '提示', {
        type: 'warning'
      }).then(() => {
        teacherApi.deleteTeacherById(teacher.teacherId).then(response => {
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.getTeacherList();
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消辞退'
        });
      });
    },
  },

  created() {
    this.getTeacherList()
  }
}
</script>

<style>
#search .el-input {
  width: 140px !important;
  margin-right: 10px;
  margin-bottom: 5px;
}

</style>
