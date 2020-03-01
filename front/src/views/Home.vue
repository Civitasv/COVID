<template>
  <div>home</div>
</template>

<style>
</style>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      articleList: []
    };
  },
  // state.模块名.属性名
  computed: mapState({ count: state => state.blog.count }),
  mounted() {}
};
</script>