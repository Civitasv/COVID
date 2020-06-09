<template>
  <div id="poster">
    <el-form class="login-container" label-position="left">
      <h3 class="login_title">请登录</h3>
      <el-form-item>
        <el-input type="text" v-model="userInfo.username" auto-complete="off" placeholder="账号" />
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="userInfo.password" auto-complete="off" placeholder="密码" />
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="checked">记住密码</el-checkbox>
      </el-form-item>

      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 100%;background: #505458;border: none"
          v-on:click="login"
        >登录</el-button>
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 100%;background: #051a37;border: none"
          v-on:click="toRegist"
        >没有账号？立即注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import user from "../network/common/api/user";
var md5 = require("md5");
export default {
  name: "Login",
  data() {
    return {
      userInfo: {
        username: "",
        password: ""
      },
      checked: false
    };
  },
  mounted() {
    if (window.localStorage.getItem("username")) {
      this.userInfo.username = window.localStorage.getItem("username");
    }
    if (window.localStorage.getItem("password")) {
      this.userInfo.password = window.localStorage.getItem("password");
    }
  },
  methods: {
    login() {
      if (this.checked) {
        window.localStorage.setItem("username", this.userInfo.username);
        window.localStorage.setItem("password", this.userInfo.password);
      }

      user
        .login(this.userInfo.username, md5(this.userInfo.password))
        .then(res => {
          if (res.data.token) {
            // 如果存在token
            var path = this.$route.query.redirect;
            window.localStorage.setItem("JWT_TOKEN", res.data.token);

            this.$router.replace({
              path: path === "/" || path === undefined ? "/manage" : path
            });
          } else if (res.data.message == "登录失败,用户不存在") {
            alert("账号或密码错误，请重新输入！");
          }
        })
        .catch(function(res) {
          console.log(res);
        });
    },
    toRegist() {
      this.$router.push({
        path: "/regist"
      });
    }
  }
};
</script>

<style>
</style>

