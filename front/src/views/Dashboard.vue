<template>
  <el-container id="container">
    <el-header>
      <Header></Header>
    </el-header>
    <router-view></router-view>
  </el-container>
</template>

<style scoped>
#container {
  padding: 0;
  margin: 0;
  width: 100vw;
  height: 100vh;
  background: black;
}
</style>

<script>
const Header = () => import("../components/header/Header");
export default {
  data() {
    return {};
  },
  components: {
    Header,
  },
};
</script>