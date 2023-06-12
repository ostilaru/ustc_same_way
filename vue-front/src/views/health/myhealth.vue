<template>
  <div>
    <!-- 搜索栏 -->
    <el-card id="search">
      <el-row>
        <el-col :span="22">
          <el-input v-model="searchModel.student_id" placeholder="学号" clearable></el-input>
          <el-input v-model="searchModel.name" placeholder="姓名" clearable></el-input>
          <el-input v-model="searchModel.gender" placeholder="姓别" clearable></el-input>
          <el-input v-model="searchModel.height" placeholder="身高" clearable></el-input>
          <el-input v-model="searchModel.weight" placeholder="体重" clearable></el-input>
          <el-input v-model="searchModel.vision" placeholder="视力" clearable></el-input>
          <el-input v-model="searchModel.lung_capacity" placeholder="肺活量" clearable></el-input>
          <!--        <el-input v-model="searchModel.exam_date" placeholder="体检日期" clearable></el-input>-->
          <el-button @click="getPhysicalExamList" type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>

        <el-col :span="2" align="right">
          <el-button @click="openEditUI(null)" type="primary" icon="el-icon-plus" round>新增</el-button>
        </el-col>
      </el-row>
    </el-card>

    <!-- 结果表格 -->
    <el-card>
      <el-table
        :data="physicalExamList"
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
          width="80">
        </el-table-column>
        <el-table-column
          prop="height"
          label="身高"
          width="100">
        </el-table-column>
        <el-table-column
          prop="weight"
          label="体重"
          width="100">
        </el-table-column>
        <el-table-column
          prop="vision"
          label="视力"
          width="100">
        </el-table-column>
        <el-table-column
          prop="lungCapacity"
          label="肺活量"
          width="100">
        </el-table-column>
        <el-table-column
          prop="examDate"
          label="体检日期"
          width="150">
        </el-table-column>
        <el-table-column
          label="操作"
          width="150">
          <template slot-scope="scope">
            <el-button @click="openEditUI(scope.row.studentId)" type="primary" size="mini" icon="el-icon-edit" circle></el-button>
            <el-button @click="deletePhysicalExam(scope.row)" type="danger" size="mini" icon="el-icon-delete" circle></el-button>
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
      <el-form :model="physicalExamForm" ref="physicalExamFormRef" :rules="rules">
        <el-form-item label="学号" prop="studentId" :label-width="formLabelWidth" >
          <el-input v-model="physicalExamForm.studentId" autocomplete="off" :disabled="flag" placeholder="PBxxxxxxxxx"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
          <el-input v-model="physicalExamForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="姓别" prop="gender" :label-width="formLabelWidth">
          <el-select v-model="physicalExamForm.gender" clearable placeholder="请选择">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身高" prop="height" :label-width="formLabelWidth">
          <el-input v-model="physicalExamForm.height" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="体重" prop="weight" :label-width="formLabelWidth">
          <el-input v-model="physicalExamForm.weight" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="视力" prop="vision" :label-width="formLabelWidth">
          <el-input v-model="physicalExamForm.vision" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="肺活量" prop="lungCapacity" :label-width="formLabelWidth">
          <el-input v-model="physicalExamForm.lungCapacity" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="体检日期" prop="examDate" :label-width="formLabelWidth">
          <el-date-picker
            v-model="physicalExamForm.examDate"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePhysicalExam">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import healthApi from '@/api/physicalExamManage'

export default {
  data() {
    return {
      searchModel: {
        student_id: '',
        name: '',
        gender: '',
        height: '',
        weight: '',
        vision: '',
        lung_capacity: '',
        exam_date: '',
        pageNo: 1,
        pageSize: 10
      },
      physicalExamList: [],
      total: 0,
      physicalExamForm: {
        studentId: '',
        name: '',
        gender: '',
        height: '',
        weight: '',
        vision: '',
        lungCapacity: '',
        examDate: ''
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
      rules: {
        studentId: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          { min: 11, max: 11, message: '长度为11个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 1, max: 16, message: '长度在 1 到 16 个字符', trigger: 'blur' }
        ],

      },

    }
  },

  methods: {
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getPhysicalExamList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getPhysicalExamList()
    },
    getPhysicalExamList() {
      healthApi.getPhysicalExamList(this.searchModel).then(res => {
        this.physicalExamList = res.data.rows
        this.total = res.data.total
      })
    },
    openEditUI(student_id) {
      if(student_id == null){
        this.flag = false;
        this.title = "新增体检表";
      }else{
        this.flag = true;
        this.title = "修改体检表";
        // 根据student_id查询用户信息
        healthApi.getPhysicalExamById(student_id).then(res => {
          this.physicalExamForm = res.data;
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
      this.physicalExamForm = {
        student_id: '',
        name: '',
        gender: '',
        height: '',
        weight: '',
        vision: '',
        lung_capacity: '',
        exam_date: '',
      };
      this.$refs.physicalExamFromRef.clearValidate();
    },

    savePhysicalExam(){
      // 先触发表单验证
      this.$refs.physicalExamFormRef.validate((valid) => {
        if (valid) {
          // 提交请求给后台
          healthApi.savePhysicalExam(this.physicalExamForm, this.title).then(response => {
            // 给出成功提示
            this.$message({
              message: response.message,
              type: 'success'
            });
            // 关闭对话框
            this.dialogFormVisible = false;
            // 刷新表格
            this.getPhysicalExamList();
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      // 提交请求给后台
    },

    deletePhysicalExam(physicalExam) {
      this.$confirm('确认要删除学生' + physicalExam.name + '的体检表吗?', '提示', {
        type: 'warning'
      }).then(() => {
        healthApi.deletePhysicalExamById(physicalExam.studentId).then(response => {
          this.$message({
            message: response.message,
            type: 'success'
          });
          this.getPhysicalExamList();
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
    this.getPhysicalExamList()
  }
}
</script>

<style scoped>
#search .el-input {
  width: 120px !important;
  margin-right: 10px;
  margin-bottom: 5px;
}

</style>
