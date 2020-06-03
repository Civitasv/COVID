<template>
  <div class="dashboard">
    <div class="flex-container column">
      <div
        class="item one"
        @click="clickChart('1')"
        style="transform: translate(-22.4%,-33.5%) scale(0.33)"
      >
        <Time3></Time3>
      </div>
      <div
        class="item two"
        @click="clickChart('2')"
        style="transform: translate(-22.4%,0.5%) scale(0.33)"
      >
        <Time2></Time2>
      </div>
      <div
        class="item three"
        @click="clickChart('3')"
        style="transform: translate(-22.4%,34.5%) scale(0.33)"
      >
        <Time></Time>
      </div>
      <div
        class="item four active"
        @click="clickChart('4')"
        style="transform: translate(43.7%, 0) scale(1)"
      >
        <Virus></Virus>
      </div>
    </div>
  </div>
</template>

<script>
const Time2 = () => import("../time/Time2");
const Time3 = () => import("../time/Time3");
const Virus = () => import("../virus/Virus");
const Time = () => import("../time/Time");

export default {
  data() {
    return {
      items: []
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.items = document.querySelectorAll(".flex-container .item");
      for (let i = 0; i < this.items.length; i++) {
        this.items[i].dataset.order = i + 1;
      }
    },
    clickChart(clickIndex) {
      let activeItem = document.querySelector(".flex-container .active");
      let activeIndex = activeItem.dataset.order;
      let clickItem = this.items[clickIndex - 1];
      if (activeIndex === clickIndex) {
        return;
      }
      activeItem.classList.remove("active");
      clickItem.classList.add("active");
      this._setStyle(clickItem, activeItem);
    },
    _setStyle(el1, el2) {
      let transform1 = el1.style.transform;
      let transform2 = el2.style.transform;
      el1.style.transform = transform2;
      el2.style.transform = transform1;
    }
  },
  components: {
    Virus,
    Time,
    Time2,
    Time3
  }
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}

.point,
.multipleColumn,
.columnChart,
.line {
  height: 100% !important;
  width: 100% !important;
  background: none !important;
}
.item {
  padding: 0px;
  margin: 0px;
  width: 68%;
  height: 100%;
  position: absolute;
  transform: cale(0.33);
  text-align: center;
  transition: all 0.8s;
  background: rgba(32, 32, 35, 0.5);
}
.dashboard {
  position: relative;
  width: 100%;
  height: 100%;
  margin: 0px;
  padding: 0px;
  padding-top: 5%;
  background: url("../../assets/images/wvisual_bg.jpg");
  background-size: 100% 100%;
}
.dashboard h1 {
  color: whitesmoke;
  text-align: center;
  font-family: "Courier New", Courier, monospace;
  font-weight: 800;
}
.flex-container.column {
  position: relative;
  height: 90%;
  width: 90%;
  overflow: hidden;
  margin: 0 auto 0 auto;
  box-sizing: content-box;
}
.active {
  height: 100%;
  width: 69%;
  margin-left: 10px;
}
</style>
