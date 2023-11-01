<template>
  <div style="margin: 29px">
    <el-tree
      ref="tree"
      :data="data"
      show-checkbox
      default-expand-all
      node-key="id"
      highlight-current
      :props="defaultProps"
    />
    <el-button :disabled="saveBtnDisabled" type="primary" @click="save"
      >保存</el-button
    >
  </div>
</template>

<script>
import {
  toAssign,
  doAssign,
} from "@/api/permission";
export default {
  data() {
    return {
      saveBtnDisabled: false,
      data: [],
      roleId: "",
      defaultProps: {
        children: "children",
        label: "name",
      },
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
          this.data = res.data.data;
          var list = [];
          var jsonList = JSON.parse(JSON.stringify(this.data));
          var llll = JSON.stringify(this.data);
          console.log("this.data=========================  " + this.data);
          console.log("jsonList=========================  " + jsonList);
          console.log(
            "JSON.stringify(this.data)=========================  " +
              JSON.stringify(this.data)
          );
          this.getJsonList(list, jsonList[0]["children"]);
          this.$refs.tree.setCheckedKeys(list);
        }
      });
    },
    //获取所有的角色的权限
    getJsonList(list, jsonList) {
      for (var i = 0; i < jsonList.length; i++) {
        if (
          jsonList[i]["hasSelect"] == true &&
          jsonList[i]["children"].length === 0
        ) {
          list.push(jsonList[i]["id"]);
        }
        if (jsonList[i]["children"].length > 0) {
          this.getJsonList(list, jsonList[i]["children"]);
        }
      }
    },
    save() {
      this.saveBtnDisabled = true;
      var beforeIds = this.$refs.tree.getHalfCheckedKeys().join(",");
      var afterIds = this.$refs.tree.getCheckedKeys().join(",");
      var ids = beforeIds + "," + afterIds;
      if (ids === ",") {
        ids = [];
      }
      console.log(ids);
      doAssign(this.roleId, ids).then((res) => {
        if (res.code === 20000 && res.success) {
          this.$message({
            type: "info",
            message: "保存成功！",
          });
          this.$router.push({ path: "/role" });
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