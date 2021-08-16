<template>
  <div class="home">
    <div class="wrapper">
      <el-card class="card">
        <h1>基于Redis的时空数据可视化系统：以COVID-19为例</h1>
        <p>
          数据来源:
          <a
            href="https://github.com/CSSEGISandData/COVID-19"
            style="color: #00ffa9"
            >JHU CSSE COVID-19 Data</a
          >
        </p>
        <p>Author: 胡森</p>
        <div class="pages">
          <router-link
            to="/dashboard/first"
            class="link"
            data-title="ENTER NOW"
          ></router-link>
          <!-- <el-button :style="btnstyle" class="btn">
            <router-link to="/dashboard/second" class="link" tag="label"
              >中国数据可视化</router-link
            >
          </el-button> -->
          <!-- <el-button :style="btnstyle" class="btn">
            <router-link to="/manage" class="link" tag="label"
              >疫情数据管理</router-link
            >
          </el-button> -->
        </div>
      </el-card>
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
.wrapper {
  height: 100%;
  width: 100%;
  background-color: #00000069;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.link {
  position: relative;
  display: inline-block;
  padding: 1.2em 2em;
  text-decoration: none;
  text-align: center;
  cursor: pointer;
  user-select: none;
  color: white;
}

.link::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background: linear-gradient(135deg, #81828275, #71717a96);
  border-radius: 4px;
  transition: box-shadow 0.5s ease, transform 0.2s ease;
  will-change: transform;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  transform: translateY(var(--ty, 0)) rotateX(var(--rx, 0))
    rotateY(var(--ry, 0)) translateZ(var(--tz, -12px));
}
.link:hover {
  background: linear-gradient(135deg, #00f8f875, #0000ee96);
}
.link:hover::before {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.link::after {
  position: relative;
  display: inline-block;
  content: attr(data-title);
  transition: transform 0.2s ease;
  font-weight: bold;
  letter-spacing: 0.01em;
  will-change: transform;
  transform: translateY(var(--ty, 0)) rotateX(var(--rx, 0))
    rotateY(var(--ry, 0));
}

.pages {
  margin-top: 100px;
}
.card {
  text-align: center;
  background-color: transparent;
  border-radius: 0%;
  border-width: 0;
  color: whitesmoke;
}

.home h1 {
  padding-top: 10vh;
  color: azure;
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
      btnstyle: "background:transparent;color:whitesmoke;",
    };
  },
  components: {
    Footer,
  },
};
</script>