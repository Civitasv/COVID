<template>
  <div id="poster">
    <el-form class="login-container" label-position="left">
      <h3 class="login_title">请注册</h3>
      <el-form-item style="margin-bottom: 10px">
        <el-input
          type="text"
          v-model="userInfo.username"
          auto-complete="off"
          placeholder="账号"
          @blur="checkUsername"
        />
      </el-form-item>
      <div
        class="checkName"
        v-show="nameTip.flag"
        :class="[nameTip.success ? 'success' : 'fail']"
      >
        <label style="padding-left: 15px">{{ message }}</label>
        <i class="el-icon-close" @click="closeNameTip" id="closeBtn"></i>
      </div>
      <el-form-item style="margin-bottom: 10px">
        <el-input
          type="password"
          v-model="userInfo.password"
          auto-complete="off"
          placeholder="密码"
          @blur="checkPwd"
        />
      </el-form-item>
      <div
        class="checkPwd"
        v-show="pwdTip.flag"
        :class="[pwdTip.success ? 'success' : 'fail']"
      >
        <label style="padding-left: 15px">{{ message2 }}</label>
        <i class="el-icon-close" @click="closePwdTip" id="closeBtn"></i>
      </div>
      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 100%; background: #505458; border: none"
          v-on:click="regist"
          >注册</el-button
        >
      </el-form-item>
      <el-form-item style="width: 100%">
        <el-button
          type="primary"
          style="width: 100%; background: #051a37; border: none"
          v-on:click="toLogin"
          >已有账号？立即登录</el-button
        >
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
        password: "",
      },
      message: "",
      message2: "",
      nameTip: {
        success: false,
        flag: false,
      },
      pwdTip: {
        success: false,
        flag: false,
      },
    };
  },
  methods: {
    regist() {
      if (this.nameTip.success == false) {
        this.message = "请修改用户名以符合格式！";
        this.nameTip.success = false;
        this.nameTip.flag = true;
        return;
      }
      if (this.pwdTip.success == false) {
        this.pwdTip = "请修改密码以符合格式！";
        this.pwdTip.success = false;
        this.pwdTip.flag = true;
        return;
      }
      user
        .regist(this.userInfo.username, md5(this.userInfo.password))
        .then((res) => {
          if (res.data.token) {
            // 如果存在token
            var path = this.$route.query.redirect;
            window.localStorage.setItem("JWT_TOKEN", res.data.token);

            this.$router.replace({
              path: path === "/" || path === undefined ? "/manage" : path,
            });
          }
        });
    },
    toLogin() {
      this.$router.push({
        path: "/login",
      });
    },
    checkUsername() {
      if (this.userInfo.username == "") {
        this.message = "用户名不能为空！";
        this.nameTip.success = false;
        this.nameTip.flag = true;
        return;
      }
      user.checkUsername(this.userInfo.username).then((res) => {
        this.message = res.data.mes;
        this.nameTip.flag = true;

        if (this.message == "该用户名已被注册！") {
          this.nameTip.success = false;
        } else {
          this.nameTip.success = true;
        }
      });
    },
    checkPwd() {
      var regExp = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,21}$/;
      if (this.userInfo.password == "") {
        this.message2 = "密码不能为空！";
        this.pwdTip.success = false;
        this.pwdTip.flag = true;
      } else if (!regExp.test(this.userInfo.password)) {
        this.message2 = "密码应由6-21位字母和数字组成！";
        this.pwdTip.success = false;
        this.pwdTip.flag = true;
      } else {
        this.message2 = "该密码符合格式！";
        this.pwdTip.success = true;
        this.pwdTip.flag = true;
      }
    },
    closeNameTip() {
      this.nameTip.flag = false;
    },
    closePwdTip() {
      this.pwdTip.flag = false;
    },
  },
};
</script>

<style>
.checkName,
.checkPwd {
  margin-bottom: 10px;
  color: #fff;
  border-radius: 5px;
}
.success {
  background-color: #67c23a;
}
.fail {
  background-color: #f56c6c;
}
#closeBtn {
  cursor: pointer;
}
</style>

