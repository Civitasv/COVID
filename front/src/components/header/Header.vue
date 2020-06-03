<template lang="html">
  <div class="title">
    <h1>{{name}}</h1>
  </div>
</template>

<script>
export default {
  props: {
    name: String
  },
  created() {},
  data() {
    return {};
  },
  methods: {}
};
</script>

<style scoped>
h1 {
  flex: 0 0 120px;
  font-size: 21px;
  font-weight: bold;
  padding-left: 20px;
  color: whitesmoke;
  text-align: left;
}
</style>
