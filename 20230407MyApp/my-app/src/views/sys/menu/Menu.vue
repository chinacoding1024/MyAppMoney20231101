<template>
  <div>
    <el-table
      :data="tableData"
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      border
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="名称" sortable min-width="20%">
      </el-table-column>
      <el-table-column prop="path" label="访问路径" sortable min-width="15%">
      </el-table-column>

      <el-table-column
        prop="component"
        label="组件路径"
        sortable
        min-width="15%"
      >
      </el-table-column>
      <el-table-column
        prop="permissionValue"
        label="权限值"
        sortable
        min-width="15%"
      >
      </el-table-column>
      <el-table-column prop="status" label="状态" sortable min-width="10%">
      </el-table-column>
      <el-table-column prop="icon" label="图标" sortable min-width="10%">
      </el-table-column>

      <el-table-column label="操作" min-width="15%">
        <template slot-scope="scope">
          <!-- 第一层和第二层可以添加菜单，第三层添加功能，第四层修改功能 -->
          <el-button
            v-if="scope.row.level === 1 || scope.row.level === 2"
            type="text"
            size="mini"
            @click="addOrEditMenuDialog(scope.row, 'add')"
            >添加菜单</el-button
          >
          <el-button
            v-if="scope.row.level === 3"
            type="text"
            size="mini"
            @click="
              () => {
                dialogPermissionFormVisible = true;
                permissionForm = { ...PERMISSION_FORM };
                permissionForm.pid = scope.row.id;
                permissionTitle = '添加功能';
              }
            "
          >
            添加功能</el-button
          >
          <el-button
            v-if="scope.row.level === 4"
            type="text"
            size="mini"
            @click="updatePermissionDialog(scope.row)"
            >修改功能</el-button
          >
          <el-button
            v-if="scope.row.level !== 1 && scope.row.level !== 4"
            type="text"
            size="mini"
            @click="addOrEditMenuDialog(scope.row, 'edit')"
            >修改</el-button
          >
          <el-button
            v-if="scope.row.level !== 1"
            @click="deleteSubmit(scope.row)"
            type="text"
            size="mini"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加菜单的窗口 -->
    <el-dialog
      :title="title"
      :label-position="labelPosition"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        ref="menuForm"
        :model="menuForm"
        :rules="menuFormRules"
        label-width="120px"
      >
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="menuForm.name"></el-input>
        </el-form-item>
        <el-form-item label="访问路径" prop="path" v-if="showFlag">
          <el-input v-model="menuForm.path"></el-input>
        </el-form-item>
        <el-form-item label="组件路径" prop="component" v-if="showFlag">
          <el-input v-model="menuForm.component"></el-input>
        </el-form-item>
        <!-- <el-form-item label="权限值" prop="permissionValue">
          <el-input v-model="menuForm.permissionValue"></el-input>
        </el-form-item> -->
        <el-form-item label="图标" prop="icon">
          <el-input v-model="menuForm.icon"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetMenu()">取 消</el-button>
        <el-button type="primary" @click="addMenuFormSubmit()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加功能的窗口 -->
    <el-dialog
      :title="permissionTitle"
      :label-position="labelPosition"
      :visible.sync="dialogPermissionFormVisible"
    >
      <el-form
        ref="permissionForm"
        :model="permissionForm"
        :rules="permissionFormRules"
        label-width="120px"
      >
        <el-form-item label="功能名称" prop="name">
          <el-input v-model="permissionForm.name"></el-input>
        </el-form-item>
        <el-form-item label="访问路径" prop="path">
          <el-input v-model="permissionForm.path"></el-input>
        </el-form-item>
        <el-form-item label="组件路径" prop="component">
          <el-input v-model="permissionForm.component"></el-input>
        </el-form-item>
        <el-form-item label="功能权限值" prop="permissionValue">
          <el-input v-model="permissionForm.permissionValue"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="permissionForm.icon"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetPermission()">取 消</el-button>
        <el-button type="primary" @click="addPermissonFormSubmit()"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getData, updateMenuSubmit, deleteSubmit } from "@/api/permission.js";
import http from "@/utils/request";
const MENU_FORM = {
  name: "",
  path: "",
  component: "",
  // status: 0,
  icon: "setting",
  pid: 0,
  type: "1",
  id: "",
};
const PERMISSION_FORM = {
  name: "",
  path: "",
  component: "",
  permissionValue: "",
  // status: 0,
  icon: "setting",
  pid: 0,
  type: "2",
  id: "",
};
export default {
  data() {
    return {
      showFlag: true,
      labelPosition: "left",
      tableData: [], //获取递归取得的全部菜单
      dialogFormVisible: false, //添加修改菜单窗口开关
      dialogPermissionFormVisible: false, //添加修改功能窗口开关
      title: "添加菜单", //添加修改菜单标题
      permissionTitle: "添加功能", //添加修改菜单标题
      menuForm: MENU_FORM, //添加修改菜单内容
      permissionForm: PERMISSION_FORM, //添加修改功能内容
      //表单验证
      menuFormRules: {
        name: [
          { required: true, message: "请输入菜单名称", trigger: "blur" },
          {
            min: 3,
            max: 30,
            message: "长度在 3 到 30 个字符",
            trigger: "blur",
          },
        ],
        path: [
          { message: "请输入访问路径", trigger: "blur" },
          {
            min: 3,
            max: 18,
            message: "长度在 3 到 18 个字符",
            trigger: "blur",
          },
        ],
        component: [
          { message: "请输入组件路径", trigger: "blur" },
          {
            min: 3,
            max: 18,
            message: "长度在 3 到 18 个字符",
            trigger: "blur",
          },
        ],
      },
      //功能表单验证
      permissionFormRules: {
        name: [
          { required: true, message: "请输入菜单名称", trigger: "blur" },
          { min: 3, max: 8, message: "长度在 3 到 8 个字符", trigger: "blur" },
        ],

        permissionValue: [
          { required: true, message: "请输入权限值", trigger: "blur" },
          {
            min: 3,
            max: 18,
            message: "长度在 3 到 18 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },

  created() {
    //获取递归取得的全部菜单
    this.getAllMenuList();
  },
  methods: {
    //修改功能
    updatePermissionDialog(val) {
      this.dialogPermissionFormVisible = true;
      this.permissionForm = val;
      this.permissionTitle = "修改功能";
    },
    //取消菜单添加或修改
    resetMenu() {
      this.dialogFormVisible = false;
      this.menuForm = {};
    },

    //取消功能添加或修改
    resetPermission() {
      this.dialogPermissionFormVisible = false;
      this.permissionForm = {};
    },
    //添加修改下级功能
    addPermissonFormSubmit() {
      let that = this;
      this.$refs.permissionForm.validate((valid) => {
        if (valid) {
          if (that.permissionForm.id) {
            //修改
            updateMenuSubmit(that.permissionForm).then((res) => {
              if (res.code === 20000) {
                that.$message({
                  type: "success",
                  message: "修改功能成功",
                });
                //刷新页面
                that.getAllMenuList();
                that.dialogPermissionFormVisible = false;
                that.permissionForm = { ...PERMISSION_FORM };
              }
            });
          } else {
            //新增

            //let permission = that.menuForm;
            http({
              url: "/permission/save",
              method: "post",
              data: that.permissionForm,
            }).then((res) => {
              if (res.code === 20000) {
                that.$message({
                  type: "success",
                  message: "添加功能成功",
                });
                //刷新页面
                that.getAllMenuList();
                that.dialogPermissionFormVisible = false;
                that.permissionForm = { ...PERMISSION_FORM };
              }
            });
          }
        } else {
          return false;
        }
      });
    },
    //删除
    deleteSubmit(val) {
      let that = this;
      this.$confirm("此操作将永久删除该记录，是否继续?", "提升", {
        distinguishCancelAndClose: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(() => {
          deleteSubmit(val.id).then((res) => {
            if (res.code === 20000) {
              that.$message({
                type: "success",
                message: "删除成功",
              });
              //刷新页面
              that.getAllMenuList();
            }
          });
          this.$message({
            type: "info",
            message: "保存修改",
          });
        })
        .catch((action) => {
          this.$message({
            type: "info",
            message:
              action === "cancel" ? "放弃保存并离开页面" : "停留在当前页面",
          });
        });
      // let that = this;
      // this.$confirm("此操作将永久删除该记录，是否继续?", "提升", {
      //   cofirmButtonText: "确定",
      //   cancelButtonText: "取消",
      //   type: "warning",
      // }).then(() => {
      //   deleteSubmit(val.id).then((res) => {
      //     if (res.code === 20000) {
      //       that.$message({
      //         type: "success",
      //         message: "删除成功",
      //       });
      //       //刷新页面
      //       that.getAllMenuList();
      //     }
      //   });
      // });
    },
    //修改
    updateMenuSubmit() {
      let that = this;
      updateMenuSubmit(that.menuForm).then((res) => {
        if (res.code === 20000) {
          that.$message({
            type: "success",
            message: "修改菜单成功",
          });
          //刷新页面
          that.getAllMenuList();
          that.dialogFormVisible = false;
        }
      });
    },
    //新增
    addMenuSubmit() {
      let that = this;
      //let permission = that.menuForm;
      http({
        url: "/admin/sys/permission/save",
        method: "post",
        data: that.menuForm,
      }).then((res) => {
        if (res.code === 20000) {
          that.$message({
            type: "success",
            message: "添加菜单成功",
          });
          //刷新页面
          that.getAllMenuList();
          that.dialogFormVisible = false;
        }
      });
    },
    //提交添加菜单的数据
    addMenuFormSubmit() {
      let that = this;
      this.$refs.menuForm.validate((valid) => {
        if (valid) {
          if (that.menuForm.id) {
            that.title = "修改菜单";
            //修改
            that.updateMenuSubmit();
          } else {
            that.title = "添加菜单";
            //新增
            that.addMenuSubmit();
          }
        } else {
          return false;
        }
      });
    },
    //显示添加菜单
    addOrEditMenuDialog(val, flag) {
      if (val.level === 1) {
        this.showFlag = false;
      } else {
        this.showFlag = true;
      }

      if ("add" === flag) {
        //新增的话，页面的值初始化为定义好的常量
        this.menuForm = { ...MENU_FORM };
        //新增的话，新增的菜单的pid为当前选中的行的id
        this.menuForm.pid = val.id;
        this.title = "添加菜单";
      } else {
        this.title = "修改菜单";
        //修改的话，页面的值初始化为列表的具体的内容
        Object.assign(this.menuForm, val);
        //修改
        if (val.level === 2) {
          this.showFlag = false;
        }
      }

      this.dialogFormVisible = true;
    },
    //获取递归取得的全部菜单
    getAllMenuList() {
      getData().then((res) => {
        if (res.code === 20000 && res.data) {
          this.tableData = res.data.data;
          //console.log('JSON.stringify(this.tableData) ==='+JSON.stringify(this.tableData))
        }
      });
    },
  },
};
</script>