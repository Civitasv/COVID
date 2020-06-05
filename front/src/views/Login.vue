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
      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 100%;background: #505458;border: none"
          v-on:click="validateToken"
        >登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import user from "../network/common/api/user";
export default {
  name: "Login",
  data() {
    return {
      userInfo: {
        username: "",
        password: ""
      }
    };
  },
  methods: {
    validateToken() {
      user
        .validateLogin(this.userInfo.username, this.userInfo.password)
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
    }
  }
};
</script>

<style>
#poster {
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  margin: 0px;
}
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 150px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>

