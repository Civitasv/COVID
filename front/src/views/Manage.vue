<template>
  <div>{{content}}</div>
</template>

<script>
import user from "../network/common/api/user";
export default {
  data() {
    return {
      content: ""
    };
  },
  computed: {},
  mounted() {
    user.add().then(res => {
      console.log(res.data);
      this.content = res.data;
    });
  }
};
</script>