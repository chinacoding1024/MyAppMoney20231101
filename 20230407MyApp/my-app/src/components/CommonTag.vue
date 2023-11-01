<template>
  <div class="tag-content">
    <el-tag
    size="small"
      v-for="(item, index) in tagList"
      :key="item.label"
      :closable="item.name !== 'home'"
      :effect="item.name === $route.name ? 'dark' : 'plain'"
      @click="changeTag(item)"
      @close="handleClose(item, index)"
    >
      {{ item.label }}
    </el-tag>
  </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "CommonTag",
  data() {
    return {};
  },
  methods: {
    ...mapMutations(["closeTag"]),

    //点击删除标签
    handleClose(item, index) {
      //
      //const length = this.tagList.length -1
      //this.$store.commit('')

      //调用store中的mutations
      this.closeTag(item);
      const length = this.tagList.length;
      //删除之后的跳转功能
      //1、如果要删除的tag不是当前高亮选中的路由，不做处理
      if (item.name !== this.$route.name) {
        return;
      }
      //2、如果要删除的tag和length相等，表示删除的是最后一项，自动向左移动，跳转到前一级
      if (index === length) {
        this.$router.push({ name: this.tagList[index - 1].name });
      }
      //3、如果要删除的tag是中间的，自动向后移动，跳转到后一级
      else {
        this.$router.push({ name: this.tagList[index].name });
      }
    },

    //点击tag跳转页面
    changeTag(item) {
      //this.$router.push('/home')
      this.$router.push({ name: item.name });
    },
  },
  computed: {
    ...mapState({
      tagList: (state) => state.menu.breadcrumbList, //这里实际和面包屑是一个数据
    }),
  },
};
</script>
<style lang="less" scoped>
.tag-content {
    padding: 20px;
    .el-tag {
        margin-right: 15px;
        cursor: pointer;
    }
}
</style> 