<template>
  <div class="home">
    <Card class="card">
      <h1>疫情数据分析平台</h1>
      <Button type="success" ghost class="btn">
        <router-link to="/upload" class="link" tag="label">疫情数据上传</router-link>
      </Button>
      <Button type="info" class="btn">
        <router-link to="/virus" class="link" tag="label">疫情数据热力图</router-link>
      </Button>

      <Button type="warning" ghost class="btn">
        <router-link to="/visual" class="link" tag="label">疫情数据分析</router-link>
      </Button>
      <router-view></router-view>
    </Card>
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
.card {
  text-align: center;
  width: 50vw;
  height: 50vh;
  margin: auto;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: transparent;
  border-color: #d86a1a;
  box-shadow: -1px 2px 3px #888888;
}
.card .btn {
  margin: 5px;
}
.home h1 {
  padding-top: 10vh;
  padding-bottom: 15vh;
  color: azure;
}
</style>