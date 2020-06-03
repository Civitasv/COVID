<template>
  <div class="home">
    <vue-particles
      color="#dedede"
      :particleOpacity="1"
      :particlesNumber="80"
      shapeType="circle"
      :particleSize="4"
      linesColor="#dedede"
      :linesWidth="1.5"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
    ></vue-particles>
    <div class="logo">
      <i class="el-icon-location-outline"></i>
    </div>
    <el-card class="card">
      <h1>新冠疫情数据可视化与管理平台</h1>
      <p>2020年WebGIS实习作品，数据来源：澎湃新闻美数课</p>
      <br />
      <p>武汉大学：胡森 2017301110153</p>
    </el-card>

    <div class="pages">
      <el-button class="btn" :style="btnstyle">
        <label>INTRO</label>
      </el-button>
      <el-button :style="btnstyle" class="btn">
        <router-link to="/wvisual" class="link" tag="label">VISUALIZE</router-link>
      </el-button>

      <el-button :style="btnstyle" class="btn">
        <router-link to="/manage" class="link" tag="label">MANAGE</router-link>
      </el-button>

      <el-button :style="btnstyle" class="btn">
        <label>ABOUT</label>
      </el-button>
    </div>
    <Footer></Footer>
  </div>
</template>

<style scoped>
.home {
  background: url("../assets/images/bg.jpg") no-repeat;
  height: 100%;
  width: 100%;
  overflow: hidden;
  background-size: cover;
}
.link {
  cursor: pointer;
}
.logo {
  position: absolute;
  top: 2vh;
  left: 2vh;
  right: 0;
  bottom: 0;
  font-size: 2rem;
  color: #c1c1c1;
}

.pages {
  text-align: center;
  margin: auto;
  position: absolute;
  top: 75vh;
  left: 0;
  right: 0;
  bottom: 0;
}
.card {
  text-align: center;
  height: 50vh;
  width: 90vw;
  margin: auto;
  position: absolute;
  top: -15vh;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: transparent;
  border-color: #ffffff;
  border-radius: 0%;
  border-right-width: 0;
  border-left-width: 0;
  color: whitesmoke;
}

.home h1 {
  padding-top: 10vh;
  color: azure;
  font-family: "Courier New", Courier, monospace;
  font-size: 3rem;
  font-weight: 800;
  margin: 0 0 1rem 0;
}
</style>

<script>
const Footer = () => import("../components/common/Footer");
export default {
  data() {
    return {
      btnstyle: "background:transparent;color:whitesmoke;"
    };
  },
  components: {
    Footer
  }
};
</script>