<template>
  <div>
    <!--"middle-around"类型,三个子菜单，默认动画,灰色遮罩，方形默认开关按钮，自定义菜单颜色配置-->
    <div class="menu">
      <circle-menu
        type="bottom"
        :number="3"
        mask="black"
        btn
        :colors="[ '#267773', '#A7425C', '#620101', '#F3825F', '#F19584']"
      >
        <span slot="item_1" @click="showWorld">World</span>
        <span slot="item_2" @click="showChina">China</span>
        <span slot="item_3" class="fa fa-weibo fa-lg" @click="showMore">More+</span>
      </circle-menu>
    </div>
    <Dashboard2></Dashboard2>
  </div>
</template>

<style scoped>
.menu {
  position: absolute;
  left: 0;
}
.menu span {
  color: whitesmoke;
  cursor: pointer;
}
</style>

<script>
const Dashboard2 = () => import("../components/china/Dashboard");
//const Footer = () => import("../components/common/Footer");
import CircleMenu from "vue-circle-menu";

export default {
  data() {
    return {};
  },
  components: {
    //Footer,
    CircleMenu,
    Dashboard2
  },
  methods: {
    showWorld() {
      this.$router.push("/wvisual");
    },
    showChina() {
      this.$router.push("/cvisual");
    },
    showMore() {}
  }
};
</script>