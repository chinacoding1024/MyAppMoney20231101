<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="searchData.roleName" placeholder="角色" />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="pageList()"
          >查询</el-button
        >
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>

    <div>
      <el-button type="primary" size="mini" @click="add()">添加</el-button>
      <!-- <el-button type="danger" size="mini" @click="batchRemove()"
        >批量删除</el-button
      > -->
    </div>
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      stripe
      style="margin-top: 20px"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" />
      <el-table-column label="ID" width="50">
        <!-- 使用连续的序号 -->
        <template slot-scope="scope">
          {{ (currentPage - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="名称" width="200" />

      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <router-link :to="'/sys/role/distribution/' + scope.row.id">
            <el-button type="info" size="mini" icon="el-icon-info"></el-button
          ></router-link>

          <el-button
            @click="handleEdit(scope.row)"
            type="primary"
            size="mini"
            icon="el-icon-edit"
            style="margin-left: 10px"
            >编辑</el-button
          >
          <!-- <router-link :to="'/acl/role/update/' + scope.row.id">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              style="margin-left:10px;margin-right:10px;"
            ></el-button
          ></router-link> -->
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeById(scope.row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页组件-->
    <el-pagination
      :current-page="currentPage"
      :page-size="limit"
      :total="total"
      :page-sizes="[5, 10, 15, 20]"
      style="padding: 12px 8px; text-align: center"
      layout="sizes, prev, pager, next, jumper, ->, total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

    <el-dialog :title="title" :visible.sync="saveBtnDisabled" width="35%">
      <!-- 用户表单信息 -->
      <el-form
        ref="model"
        :model="model"
        :inline="true"
        label-width="80px"
        :rules="rules"
      >
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="model.roleName"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="saveBtnDisabled = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
   
  
  <script>
import roleApi from "@/api/role";
export default {
  data() {
    return {
      list: [], //列表数据
      total: 0,
      currentPage: 1, //当前页
      limit: 10, //每页记录数
      searchData: {},
      idList: null,
      saveBtnDisabled: false,

      //讲师对象
      model: {
        roleName: "",
      },
    };
  },
  created() {
    this.pageList();
  },
  methods: {
    handleEdit(val) {
      this.title = "编辑";
      this.saveBtnDisabled = true;
      this.model = Object.assign({}, val);

      this.dialogVisible = true;
    },
    //新增
    save() {
      roleApi.save(this.model).then((res) => {
        this.saveBtnDisabled = false;
        this.pageList();
        if (res.code === 20000 && res.data) {
          this.$message({
            type: "info",
            message: "添加成功",
          });
          this.$router.push({ path: "/role" });
        } else {
          this.$message({
            type: "info",
            message: "添加失败",
          });
        }
      });
    },
    //修改
    update() {
      roleApi.update(this.model).then((res) => {
        this.saveBtnDisabled = false;
        this.pageList();
        if (res.code === 20000 && res.data) {
          this.$message({
            type: "info",
            message: "修改成功",
          });
          this.$router.push({ path: "/role" });
        } else {
          this.$message({
            type: "info",
            message: "修改失败",
          });
        }
      });
    },

    //跳转到修改页面
    add() {
      this.saveBtnDisabled = true;
      this.title = "新增";
      this.model = Object.assign({}, {});
      //this.$router.push({ path: `/acl/role/form/` });
    },
    // //批量删除
    // batchRemove() {
    //   if (!this.idList || this.idList.length <= 0) {
    //     this.$message({
    //       type: "info",
    //       message: "请先选择要删除的数据！",
    //     });
    //     return false;
    //   }
    //   let arrayIds = [];
    //   this.idList.forEach((element) => {
    //     arrayIds.push(element.id);
    //   });

    //   this.$confirm("此操作将永久删除, 是否继续?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning",
    //   })
    //     .then(() => {
    //       //删除api
    //       roleApi.batchRemove(arrayIds).then((res) => {
    //         this.pageList();
    //         if (res.code === 20000 && res.data) {
    //           this.$message({
    //             type: "info",
    //             message: "刪除成功: ",
    //           });
    //           this.pageList();
    //         } else {
    //           this.$message({
    //             type: "info",
    //             message: "刪除失败: ",
    //           });
    //         }
    //       });
    //     })
    //     .catch(() => {
    //       this.$message({
    //         type: "info",
    //         message: "已取消删除",
    //       });
    //     });
    // },
    //多选
    handleSelectionChange(idList) {
      this.idList = idList;
    },
    //删除
    removeById(data) {
      this.$confirm(
        "此操作将永久删除" + data.roleName + ", 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          //删除api
          roleApi.removeById(data.id).then((res) => {
            this.pageList();
            if (res.code === 20000 && res.data) {
              this.$message({
                type: "info",
                message: "刪除成功: ",
              });
              this.pageList();
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
    resetData() {
      this.searchData = {};
      this.pageList();
    },
    //改变数量
    handleSizeChange(size) {
      this.limit = size;
      this.pageList();
    },
    //改变页码
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      this.pageList();
    },

    //分页查询
    pageList() {
      roleApi
        .pageList(this.currentPage, this.limit, this.searchData)
        .then((res) => {
          if (res.code === 20000 && res.data) {
            this.$message({
              type: "info",
              message: "查询成功: ",
            });
            this.list = res.data.list;
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
    },
  },
};
</script>
  
  