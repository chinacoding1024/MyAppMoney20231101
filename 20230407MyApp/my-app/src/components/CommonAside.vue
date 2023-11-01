<template>
  <div>
    <!-- <el-menu
      default-active="1-4-1"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="isCollapse"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <h3>{{ isCollapse ? "后台" : "通用后台管理系统" }}</h3>
      <el-menu-item
        v-for="item in noChildren"
        :key="item.name"
        :index="item.path"
        @click="clickMenu(item)"
      >
        <i :class="`el-icon-${item.icon}`"></i>
        <span slot="title">{{ item.label }}</span>
      </el-menu-item>
      <el-submenu
        v-for="item in hasChildren"
        :key="item.name"
        :index="item.name"
      >
        <template slot="title">
          <i :class="`el-icon-${item.icon}`"></i>
          <span slot="title">{{ item.label }}</span>
        </template>
        <el-menu-item-group
          v-for="subItem in item.children"
          :key="subItem.path"
        >
          <el-menu-item :index="subItem.path" @click="clickMenu(subItem)">{{
            subItem.label
          }}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu> -->
    <el-menu
      default-active="1-4-1"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="isCollapse"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <router-link to="/home">
        <el-menu-item
          index="Inde123x"
          @click="clickMenu({ name: 'home', title: '首页', path: '/home' })"
        >
          <template slot="title">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
          </template>
        </el-menu-item>
      </router-link>
      <el-submenu :index="menu.name" v-for="menu in menuData" :key="menu.name" v-if="!menu.hidden">
   
        <template slot="title">
          <i :class="`el-icon-${menu.icon}`"></i>
          <span>{{ menu.label }}</span>
        </template>

        <router-link
          :to="item.path"
          v-for="item in menu.children"
          :key="item.name"
          v-if="!item.hidden"
        >
          <el-menu-item :index="item.name" @click="clickMenu(item)">
            <template slot="title">
              <i :class="`el-icon-${item.icon}`"></i>
              <span slot="title">{{ item.label }}</span>
            </template>
          </el-menu-item>
        </router-link>
     
      </el-submenu>
    </el-menu>
  </div>
</template>
<style lang="less" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-menu {
  //height: calc(100vh - 0px);
  border-right: 0px;
  height: 100vh;
  h3 {
    color: #fff;
    text-align: center;
    line-height: 48px;
    font-size: 16px;
    font-weight: 400px;
  }
}
</style>
<script>
export default {
  data() {
    return {
      //isCollapse: false,

      menuData11: [
        // {
        //   path: "/",
        //   name: "home",
        //   label: "首页",
        //   icon: "s-home",
        //   url: "Home/Home",
        // },

        {
          label: "权限管理",
          name: "system",
          icon: "location",
          children: [
            {
              path: "/user",
              name: "user",
              label: "用户管理",
              icon: "setting",
              url: "Other/PageOne",
            },
            {
              path: "/role",
              name: "role",
              label: "角色管理",
              icon: "setting",
              url: "Other/PageTwo",
            },
            {
              path: "/menu",
              name: "menu",
              label: "菜单管理",
              icon: "setting",
              url: "Other/PageTwo",
            },
          ],
        },
      ],
    };
  },
  created() {
 
  },
  methods: {

    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    clickMenu(item) {
      //当页面的路由和跳转的路由不一致才允许跳转
      //this.$route.path 上一个路径，item.path下一个路径
      
      if (
        this.$route.path !== item.path &&
        !(this.$route.path === "/home" && item.path === "/")
      ) {
        this.$router.push(item.path);
      }
      this.$store.commit("breadcrumbChange", item);
    },
  },
  computed: {
    // //没有子菜单
    // noChildren() {
    //   return this.menuData.filter((item) => !item.children);
    // },
    // //有子菜单
    // hasChildren() {
    //   return this.menuData.filter((item) => item.children);
    // },
    //vuex取得菜单展开收起的值，但是data里不能重复定义
    isCollapse() {
      return this.$store.state.menu.isCollapse;
    },
    // menuData11: {
    //   get() {
    //     return this.$store.state.menu.menuList;
    //   },
    // },
    menuData() {
      return  this.$store.state.menu.menuList;
    },
  },
};
</script>