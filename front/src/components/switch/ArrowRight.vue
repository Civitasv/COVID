<template>
  <svg
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns="http://www.w3.org/2000/svg"
    height="16px"
    width="16px"
    viewBox="0 0 16 16"
    class="arrow-right"
    @click="toggle"
  >
    <path d="M5 1.5l7 7-7 7zm1 11.586L10.586 8.5 6 3.914z"></path>
  </svg>
</template>

<style scoped>
.arrow-right {
  fill: white;
  cursor: pointer;
}
</style>

<script>
export default {
  methods: {
    toggle() {
      this.$emit("toggle"); // 调用父组件toggle
    },
  },
};
</script>