<template>
  <div class="app-container">
    <el-button
      :disabled="saveBtnDisabled"
      type="primary"
      size="mini"
      @click="save"
      >保存</el-button
    >
    <el-checkbox-group v-model="checkedRoles" style="margin-top: 20px">
      <el-checkbox v-for="role in roles" :key="role.id" :label="role.id">{{
        role.roleName
      }}</el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<script>
import { toAssign, doAssign } from "@/api/user.js";
export default {
  data() {
    return {
      saveBtnDisabled: false,
      checkedRoles: [],
      roles: [],
    };
  },
  watch: {
    $route(to, from) {
      this.init();
    },
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.roleId = this.$route.params.id;
        this.getDataList(this.roleId);
      }
    },
    getDataList(roleId) {
      toAssign(roleId).then((res) => {
        if (res.code === 20000 && res.data.data) {
          this.roles = res.data.data.allRoleList;
          var jsonObject = res.data.data.assignRoles;
          this.checkedRoles = this.getJsonList(jsonObject, "id");
        }
      });
    },
    //获取所有的角色id列表
    getJsonList(json, key) {
      var list = JSON.parse(JSON.stringify(json));
      var strText = [];
      for (var i = 0; i < list.length; i++) {
        strText.push(list[i][key]);
      }
      return strText;
    },
    save() {
      this.saveBtnDisabled = true;
      var ids = this.checkedRoles.join(",");
      if (ids === ",") {
        ids = [];
      }

      doAssign(this.roleId, ids).then((res) => {
        if (res.code === 20000 && res.success) {
          this.$message({
            type: "info",
            message: "保存成功！",
          });
          this.$router.push({ path: "/user" });
        } else {
          this.$message({
            type: "info",
            message: "保存失败！",
          });
        }
      });
    },
  },
};
</script>

<style>
</style>