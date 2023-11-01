<template>
  <el-form
    ref="form"
    label-width="70px"
    :inline="true"
    class="login-container"
    :model="form"
    :rules="rules"
  >
    <h3 class="login_title">系统登录</h3>
    <el-form-item label="用户名" prop="username">
      <el-input v-model="form.username" placeholder="请输入账号"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input
        type="password"
        v-model="form.password"
        placeholder="请输入密码"
      ></el-input>
    </el-form-item>
    <el-form-item label="验证码" prop="code">
      <el-input v-model="form.code" maxlength="6"></el-input>
    </el-form-item>
    <el-form-item>
      <el-image
        :src="captchaImg"
        class="captchaImg"
        style="margin-left: 15px"
        @click="getCaptcha"
      ></el-image>
    </el-form-item>
    <el-form-item>
      <el-button
        @click="submit"
        style="margin-left: 105px; margin-top: 10px"
        type="primary"
        :loading="loading"
        >登录</el-button
      >
      <!-- <el-button
        @click="submit1"
        style="margin-left: 105px; margin-top: 10px"
        type="primary"
        :loading="loading"
        >登录1</el-button
      > -->
    </el-form-item>
  </el-form>
</template>
<script>
// import Mock from 'mockjs'
// import Cookie from 'js-cookie'
// import { getMenu } from '../api'
import qs from "qs";
import http from "@/utils/request";
export default {
  data() {
    return {
      captchaImg: "",
      loading: false,
      form: {
        username: "admin",
        password: "111111",
        token: "aaaaa",
        code: "",
      },
      rules: {
        username: [
          { required: true, trigger: "blur", message: "请输入用户名" },
        ],
        password: [{ required: true, trigger: "blur", message: "请输入密码" }],
      },
    };
  },

  created() {
    this.getCaptcha();
  },
  methods: {
    submit1() {},
    getCaptcha() {
      http.get("/captcha").then((res) => {
        if(res.code === 20000){
          console.log("/captcha");
          console.log(res);
          this.form.token = res.data.data.token;
          this.captchaImg = res.data.data.captchaImg;
          //this.form.code = "";
        }

      });
    },
    // 登录
    submit() {
      let that = this;
      that.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$store
            .dispatch("Login", this.form)
            .then((res) => {
           
              if (res.code != 20000) {
                that.$message({
                  type: "warning",
                  message: res.message,
                });
                this.loading = false;
                return false;
              }
              this.loading = false;
              this.$router.push({ path: "/home" });
            })
            .catch(() => {
              this.loading = false;
            });
          // http
          //   .post("/login?" + qs.stringify(this.form))
          //   .then((res) => {
          //     if (res.code != 20000) {
          //       that.$message({
          //         type: "warning",
          //         message: res.message,
          //       });
          //     }
          //     console.log(res);

          //       const jwt = res.data.authorization;

          //      this.$store.commit("SET_TOKEN", jwt);
          //     //
          //     this.$router.push("/home");
          //   })
          //   .catch((e) => {});

          //this.loading = true;
          // that.$store
          //   .dispatch("Login", that.form)
          //   .then(() => {
          //     //this.loading = false;
          //     that.$router.push({ path: "/" });
          //   })
          //   .catch(() => {
          //     //this.loading = false;
          //   });
        } else {
          console.log("error submit!!");
          return false;
        }
      });

      // // token信息
      // const token = Mock.Random.guid()

      // 校验通过
      // this.$refs.form.validate((valid) => {
      //     if (valid) {
      //         getMenu(this.form).then(({ data }) => {
      //             console.log(data)
      //             if (data.code === 20000) {
      //                 // token信息存入cookie用于不同页面间的通信
      //                 Cookie.set('token', data.data.token)

      //                 // 获取菜单的数据，存入store中
      //                 this.$store.commit('setMenu', data.data.menu)
      //                 this.$store.commit('addMenu', this.$router)
      //                 // 跳转到首页
      //                 this.$router.push('/home')
      //             } else {
      //                 this.$message.error(data.data.message);
      //             }
      //         })
      //     }
      // })
    },
  },
};
</script>
<style lang="less" scoped>
.login-container {
  width: 650px;
  border: 1px solid #eaeaea;
  margin: 180px auto;
  padding: 35px 35px 15px 35px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 0 25px #cac6c6;
  box-sizing: border-box;
  .login_title {
    text-align: center;
    margin-bottom: 40px;
    color: #505458;
  }
  .el-input {
    width: 198px;
  }
}
</style>