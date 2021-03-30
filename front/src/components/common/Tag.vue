<template>
  <span class="tag" v-show="show" @click="onClick">{{ tag }}</span>
</template>

<style scoped>
.tag {
  background: rgba(251, 103, 103, 0.5);
  border-radius: 3px 3px 3px 3px;
  color: white;
  display: inline-block;
  height: 26px;
  line-height: 26px;
  padding: 0 20px 0 23px;
  position: relative;
  margin: 0 10px 10px 0;
  text-decoration: none;
  transition: color 0.2s;
  font-size: 16px;
  font-weight: 700;
  cursor: pointer;
}

.tag::before {
  background: #fff;
  border-radius: 10px;
  box-shadow: inset 0 1px rgba(0, 0, 0, 0.25);
  content: "";
  height: 6px;
  left: 10px;
  position: absolute;
  width: 6px;
  top: 10px;
}

.tag:hover {
  background-color: rgba(148, 159, 165, 0.5);
  color: white;
}
</style>

<script>
export default {
  props: {
    tag: String,
  },
  data() {
    return {};
  },

  computed: {
    show() {
      return this.tag != "";
    },
  },
  methods: {
    onClick() {
      this.$emit("click");
    },
  },
};
</script>