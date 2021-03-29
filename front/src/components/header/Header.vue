<template>
  <div class="header">
    <div class="avatar">
      <img src="../../assets/images/virus.png" />
    </div>
    <div class="title">
      <span class="content">GIS战疫——传染病多尺度智能检测系统</span>
    </div>
    <div class="nav">
      <router-link
        to="/dashboard/first"
        tag="span"
        active-class="active"
        class="nav-item"
        ><i class="el-icon-help"></i>全球战疫</router-link
      >
      <router-link
        to="/dashboard/second"
        tag="span"
        active-class="active"
        class="nav-item"
        ><i class="el-icon-help"></i>多维监测</router-link
      >
    </div>
  </div>
</template>

<style scoped>
.header {
  border: 1px solid #363636;
  background-color: #222222;
  display: flex;
  align-items: center;
  justify-content: right;
}
.header .avatar {
  width: min-content;
  padding: 2px 0px 2px 10px;
}

.header .avatar > img {
  width: 45px;
  height: 45px;
}
.header .title {
  margin-left: 10px;
}
.header .title .content {
  color: white;
  font-size: 20px;
}
.nav {
  flex: 1;
  text-align: left;
  margin-left: 10%;
}
.nav .nav-item {
  color: white;
  font-size: 15px;
  cursor: pointer;
}

.active {
  color: #76d7c4 !important;
  font-weight: bold;
}
.nav i {
  margin-right: 5px;
}

.nav span {
  margin-left: 20px;
}
</style>


<script>
export default {
  data() {
    return {};
  },
};
</script>