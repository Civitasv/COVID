<template>
  <svg
    xmlns:xlink="http://www.w3.org/1999/xlink"
    xmlns="http://www.w3.org/2000/svg"
    height="16px"
    width="16px"
    viewBox="0 0 16 16"
    class="arrow-left"
    @click="toggle"
  >
    <path d="M11 15.5l-7-7 7-7zM10 3.914L5.414 8.5 10 13.086z"></path>
  </svg>
</template>

<style scoped>
.arrow-left {
  fill: white;
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