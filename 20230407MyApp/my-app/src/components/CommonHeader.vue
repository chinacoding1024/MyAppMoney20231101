<template>
  <div class="header-main">
    <div class="l-content">
      <el-button
        icon="el-icon-menu"
        @click="handleIsCollapse"
        size="mini"
        style="margin-right: 20px"
      ></el-button>
      <!-- 面包屑 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
          v-for="item in breadcrumbList"
          :key="item.path"
          :to="{ path: item.path }"
          >{{ item.label }}</el-breadcrumb-item
        >
      </el-breadcrumb>
    </div>
    <div class="r-content">
      <el-dropdown @command="logout">
        <img src="@/assets/img/user.jpg" class="user" />
        <el-dropdown-menu slot="dropdown">
          <!-- <el-dropdown-item>个人中心</el-dropdown-item> -->
          <el-dropdown-item command="a">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import http from "@/utils/request";
export default {
  data() {
    return {};
  },
  methods: {
    logout() {
      let that = this;
      // http.post("/logout").then(res => {
          
      //    debugger
      //     // commit('SET_TOKEN', '')// 清空前端vuex中存储的数据
      //     // commit('SET_ROLES', [])// 清空前端vuex中存储的数据
      //     // commit('SET_BUTTONS', [])
      //     // removeToken()// 清空cookie
      //     // resolve()
      //   })
      that.$store
        .dispatch("LogOut")
        .then((res) => {
 debugger

          this.$router.push({ path: "/login" });
        })
        .catch(() => {});
    },
    handleIsCollapse() {
      this.$store.commit("handleCollapse");
    },
  },
  computed: {
    ...mapState({
      breadcrumbList: (state) => state.menu.breadcrumbList,
    }),
  },
};
</script>
<style lang="less" scoped>
.header-main {
  padding: 0 20px;
  background-color: #333;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .text {
    color: #ffff;
    font-size: 14px;
    margin-left: 10px;
  }
  .r-content {
    .user {
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
  }
  .l-content {
    display: flex;
    align-items: center;
    ///deep/样式穿刺
    /deep/.el-breadcrumb__item {
      .el-breadcrumb__inner {
        font-weight: normal;
        //设置面包屑字体为灰色，最后一个除外
        //<span role="link" class="el-breadcrumb__inner is-link">菜单管理</span>
        &.is-link {
          color: #666;
        }
      }
      //设置最后一个面包屑字体为白色
      //<span role="link" class="el-breadcrumb__inner is-link">用户管理</span>
      &:last-child {
        .el-breadcrumb__inner {
          color: #fff;
        }
      }
    }
  }
}
</style>