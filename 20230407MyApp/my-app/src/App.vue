<template>
  <div id="app">
    <el-row>
      <!-- 路由出口 -->
      <!-- 路由匹配到的组件将渲染在这里 -->
      <router-view></router-view>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style lang="less">
//清除默认样式
html,
body,h3,p{
  margin: 0;
  padding: 0;
}
</style>>

 
