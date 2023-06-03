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
          <el-input v-model="searchModel.department_id" placeholder="院系号" clearable></el-input>
          <el-input v-model="searchModel.class_id" placeholder="班级号" clearable></el-input>
          <el-input v-model="searchModel.contact_phone" placeholder="电话" clearable></el-input>
          <el-input v-model="searchModel.contact_email" placeholder="邮箱" clearable></el-input>
          <el-button @click="getStudentList" type="primary" round icon="el-icon-search">查询</el-button>
        </el-col>

        <el-col :span="2" align="right">
          <el-button @click="" type="primary" icon="el-icon-plus" round>新增</el-button>
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
              <el-button @click="" type="primary" size="mini" icon="el-icon-edit" circle></el-button>
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

    </div>

</template>

<script>
import stuApi from '@/api/studentManage'
export default {
  data() {
    return {
      searchModel: {
        student_id: '',
        name: '',
        department_id: '',
        class_id: '',
        contact_phone: '',
        contact_email: '',
        pageNo: 1,
        pageSize: 10
      },
      studentList: [],
      total: 0

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
    }
  },

  created() {
    this.getStudentList()
  }

}
</script>

<style>
.el-input {
  width: 148px !important;
  margin-right: 10px;
  margin-bottom: 5px;
}

</style>
