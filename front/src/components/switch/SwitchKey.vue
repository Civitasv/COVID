<template>
  <div class="switch">
    <ArrowLeft @toggle="toggleLeft"></ArrowLeft>
    <div class="content">
      {{ contents[index] }}
    </div>
    <ArrowRight @toggle="toggleRight"></ArrowRight>
  </div>
</template>

<style scoped>
.switch {
  display: flex;
  align-items: center;
  padding: 10px 10px 5px 10px;
}
.content {
  color: white;
  font-size: 14px;
  flex: 1;
}
</style>
<script>
const ArrowLeft = () => import("../switch/ArrowLeft");
const ArrowRight = () => import("../switch/ArrowRight");
export default {
  model: {
    prop: "chartIndex",
    event: "changeChartIndex",
  },
  props: {
    contents: Array,
    chartIndex: Number,
  },
  data() {
    return {
      index: this.chartIndex,
    };
  },
  watch: {
    chartIndex() {
      this.index = this.chartIndex;
    },
  },
  methods: {
    toggleLeft() {
      this.index--;
      if (this.index == -1) {
        this.index = this.contents.length - 1;
      }
      this.$emit("changeChartIndex", this.index);
    },
    toggleRight() {
      this.index = ++this.index % this.contents.length;
      this.$emit("changeChartIndex", this.index);
    },
  },
  components: {
    ArrowLeft,
    ArrowRight,
  },
};
</script>