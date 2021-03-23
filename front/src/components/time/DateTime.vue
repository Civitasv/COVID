<template>
  <div>
    <span>数据日期: {{ date_time }}</span>
  </div>
</template>

<style scoped>
div {
  text-align: center;
  padding-top: 15px;
  padding-bottom: 15px;
  padding-left: 40px;
  padding-right: 40px;
  background-color: #222;
  border: 1px solid #363636;
}
span {
  font-size: 16px;
  font-weight: bolder;
  color: white;
}
</style>

<script>
export default {
  props: {
    date_time: String,
  },
  data() {
    return {};
  },
};
</script>