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
  padding: 10px 10px 5px 15px;
}
.content {
  color: white;
  font-size: 14px;
  flex: 2;
}
</style>
<script>
const ArrowLeft = () => import("../switch/ArrowLeft");
const ArrowRight = () => import("../switch/ArrowRight");
export default {
  props: {
    contents: Array,
  },
  data() {
    return {
      index: 0,
    };
  },
  methods: {
    toggleLeft() {
      this.index = --this.index % this.contents.length;
    },
    toggleRight() {
      this.index = ++this.index % this.contents.length;
    },
  },
  components: {
    ArrowLeft,
    ArrowRight,
  },
};
</script>