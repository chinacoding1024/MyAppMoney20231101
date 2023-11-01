<template>
  <div class="user">
    <el-dialog :title="title" :visible.sync="dialogVisible" width="35%">
      <!-- 用户表单信息 -->
      <el-form
        ref="model"
        :model="model"
        :inline="true"
        label-width="80px"
        :rules="rules"
        :disabled="disabled"
      >
        <el-form-item label="用户名称" prop="username">
          <el-input v-model="model.username"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称" prop="nickName">
          <el-input v-model="model.nickName"></el-input>
        </el-form-item>
        <!-- <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="model.password"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="model.sex" placeholder="性别">
            <el-option label="男" value="1"></el-option>
            <el-option label="女" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建日期" prop="createTime">
          <el-date-picker
            v-model="model.createTime"
            type="date"
            placeholder="选择日期"
            format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item> -->
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </span>
    </el-dialog>
    <div class="user-header">
      <el-button type="primary" @click="handeleAdd" v-if="hasAuth('user.add')">+新增</el-button>
    </div>
    <el-card class="box-card" style="margin-top: 20px">
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange"
      >
        <!-- <div v-for="(val, key) in tableLabel" :key="key">
          <div v-if="key === 'a'">
            <el-table-column type="selection" :prop="key" :label="val" />
          </div>
          <div v-else><el-table-column :prop="key" :label="val" /></div>
        </div> -->
        <el-table-column
          v-for="(val, key) in tableLabel"
          :type="key === 'index' ? 'selection' : null"
          :prop="key"
          :label="val"
          :key="key"
        />
        <el-table-column fixed="right" label="操作" >
          <template slot-scope="scope">
            <el-button @click="doAssign(scope.row)" type="text" size="small" style="margin-left:5px;"
              >分配角色</el-button
            >
            <el-button @click="removeById(scope.row)" type="text" size="small"
              >刪除</el-button
            >
            <el-button @click="handleEdit(scope.row)" type="text" size="small" 
              >编辑</el-button
            >
          </template>
        </el-table-column>
        <!-- <el-table-column prop="date" label="日期" width="180">
              </el-table-column>
              <el-table-column prop="name" label="姓名" width="180">
              </el-table-column>
              <el-table-column prop="address" label="地址">
              </el-table-column>  -->
      </el-table>
      <div class="block">
        <span class="demonstration"></span>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination></div
    ></el-card>
  </div>
</template>
 <script>
import {
  getData,
  pageList,
  removeById,
  save,
  getDetail,
  update,
  listAllUsers,
  toAssign,
  doAssign,
} from "@/api/user.js";
import http from "@/utils/request";
export default {
  name: "User",
  data() {
    return {
      title: "新增",
      disabled: false,
      multipleSelection: [],
      total: 0,
      pageNum: 0,
      pageSize: 5,

      model: {
        username: "",
        password: "",
        nickName: "",
        sex: "",
        createTime: "",
      },
      tableLabel: {
        index: "",
        nickName: "昵称",

        username: "姓名",
        // phone: "电话",
        // address: "地址",
        gmtCreate: "创建日期",
      },
      tableData: [
        //   {
        //     date: "2016-05-02",
        //     name: "王小虎",
        //     address: " 1518 弄",
        //   },
        //   {
        //     date: "2016-05-04",
        //     name: "王小虎",
        //     address: " 1517 弄",
        //   },
        //   {
        //     date: "2016-05-01",
        //     name: "王小虎",
        //     address: " 1519 弄",
        //   },
        //   {
        //     date: "2016-05-03",
        //     name: "王小虎",
        //     address: " 1516 弄",
        //   },
      ],
      dialogVisible: false,
      rules: {
        username: [
          { required: true, message: "请输入用户名称", trigger: "blur" },
          {
            min: 1,
            max: 15,
            message: "长度在 1 到 15 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "change" },
        ],
        nickName: [
          { required: true, message: "请输入用户昵称", trigger: "blur" },
          {
            min: 1,
            max: 15,
            message: "长度在 1 到 15 个字符",
            trigger: "blur",
          },
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],

        createTime: [
          {
            type: "date",
            required: true,
            message: "请选择日期",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.getData();
  },
  methods: {
    //删除
    removeById(data) {
      this.$confirm(
        "此操作将永久删除" + data.username + ", 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          //删除api
          removeById(data.id).then((res) => {
            if (res.code === 20000 && res.data) {
              this.$message({
                type: "info",
                message: "刪除成功 ",
              });
              this.getData();
            } else {
              this.$message({
                type: "info",
                message: "刪除失败: ",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    saveOrUpdate() {
      if (this.model.id) {
        this.update();
      } else {
        this.save();
      }
    },
    update() {
      this.$refs.model.validate((valid) => {
        if (valid) {
          update(this.model).then((res) => {
            this.dialogVisible = false;
            this.getData();
          });
        }
      });
    },
    save() {
      this.$refs.model.validate((valid) => {
        if (valid) {
          save(this.model).then((res) => {
            this.dialogVisible = false;
            this.getData();
          });
        }
      });
    },
    //分配角色
    doAssign(row) {
      this.$router.push(`userForm/${row.id}`);
    },

    //获得分页数据
    getData() {
      pageList(this.pageNum, this.pageSize, {
        user: JSON.stringify(this.model),
      })
        .then((res) => {
          if (res.code === 20000 && res.data) {
            this.$message({
              type: "info",
              message: "查询成功: ",
            });
            this.tableData = res.data.list;
            this.total = res.data.total;
          } else {
            this.$message({
              type: "info",
              message: "查询失败: ",
            });
          }
        })
        .catch((e) => {
          this.$message({
            type: "info",
            message: "查询异常",
          });
        });
      //
      // http
      //   .post(
      //     "/admin/sys/user/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize,
      //     { user: JSON.stringify(this.model) }
      //   )
      //   .then((res) => {
      //
      //     console.log(res);
      //     this.tableData = res.records;
      //     this.total = res.total;
      //   }).catch(error => {
      //
      //   });
    },

    handleSizeChange(val) {
      this.pageSize = val;
      this.getData();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.getData();
    },
    //当选择项发生变化时会触发该事件,获取选中行的数据
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleDetails(val) {
      this.title = "详情";
      this.disabled = true;
      this.model = Object.assign({}, val);
      this.dialogVisible = true;
    },
    handleEdit(val) {
      this.title = "编辑";
      this.disabled = false;
      this.model = Object.assign({}, val);

      this.dialogVisible = true;
    },
    handeleAdd() {
      this.disabled = false;
      this.title = "新增";
      this.dialogVisible = true;
      this.model = Object.assign({}, {});
    },
  },
};
</script>
<style lang="less" scoped>
.el-el-input {
  width: 80%;
}
</style>