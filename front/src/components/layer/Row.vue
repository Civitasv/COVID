<template>
  <div>
    <div class="switch">
      <el-switch
        v-model="value"
        active-color="#984d4d"
        inactive-color="#324654"
        @change="switchChoice"
      ></el-switch>
    </div>
    <div class="layerManage">
      <label>{{graphType}}</label>
      <div class="operation">
        <span :class="['btn',confirm?'active':'']" @click="toggleConfirm">确诊</span>
        <span :class="['btn',recovery?'active':'']" @click="toggleRecovery">治愈</span>
        <span :class="['btn',death?'active':'']" @click="toggleDeath">死亡</span>
      </div>
    </div>
    <div class="lengend">
      <span
        v-for="(item,index) in legend"
        :key="index"
        :style="'background:'+item.color"
      >{{item.value}}</span>
    </div>
  </div>
</template>

<style scoped>
.switch {
  position: absolute;
  width: 50px;
  bottom: 0;
  left: 0;
  top: 0;
  background: rgba(0, 0, 0, 0.15);
}
.operation,
.layerManage {
  display: inline;
  background: rgba(0, 0, 0, 0.1);
}
.layerManage label {
  cursor: pointer;
  display: inline-block;
  font-size: 14px;
  color: whitesmoke;
  position: absolute;
  left: 50px;
  z-index: 10;
}
.layerManage .operation {
  position: absolute;
  right: 0;
  z-index: 0;
  top: 0;
}
.lengend {
  position: absolute;
  left: 50px;
  right: 0;
  z-index: 0;
  bottom: 0;
  height: 12px;
  line-height: 12px;
  text-align: right;
  padding: 0;
  background: rgba(0, 0, 0, 0.1);
  display: flex;
}
.lengend span {
  flex-grow: 1;
  border-top: 1px solid #252525;
  display: inline-block;
  font-size: 10px;
  vertical-align: middle;
  height: 12px;
  line-height: 12px;
  padding: 0 3px;
  color: #000;
  text-shadow: -1px -1px 0 #fff, 1px 1px 0 #fff, -1px 1px 0 #fff,
    1px -1px 0 #fff;
  opacity: 1;
  text-align: center;
  box-sizing: border-box;
}
.layerManage .btn {
  cursor: pointer;
  margin-top: 5px;
  background: rgba(100, 191, 255, 0.2);
  border-left: 1px solid rgba(0, 0, 0, 0.2);
  border-right: 1px solid rgba(0, 0, 0, 0.2);
  margin-left: -1px;
  color: #fff;
  font-size: 12px;
  height: 15px;
  line-height: 15px;
  padding: 4px 10px;
}
.layerManage .active {
  border-right: rgba(100, 191, 255, 0.8);
  border-left: rgba(100, 191, 255, 0.8);
  background: rgba(100, 191, 255, 0.8);
}
</style>

<script>
export default {
  props: {
    graphType: String,
    legend: Array
  },
  data() {
    return {
      value: true,
      confirm: true,
      recovery: false,
      death: false
    };
  },
  methods: {
    toggleConfirm() {
      this.confirm = true;
      this.recovery = false;
      this.death = false;
      if (this.graphType == "热力图")
        this.$emit("changeVisualData", "new_diagnosis");
      else if (this.graphType == "集合")
        this.$emit("changeClusterVisualData", "new_diagnosis");
      else if (this.graphType == "填色图")
        this.$emit("changeFillVisualData", "confirmed");
    },
    toggleRecovery() {
      this.confirm = false;
      this.recovery = true;
      this.death = false;
      if (this.graphType == "热力图")
        this.$emit("changeVisualData", "new_recovery");
      else if (this.graphType == "集合")
        this.$emit("changeClusterVisualData", "new_recovery");
      else if (this.graphType == "填色图")
        this.$emit("changeFillVisualData", "recovery");
    },
    toggleDeath() {
      this.confirm = false;
      this.recovery = false;
      this.death = true;
      if (this.graphType == "热力图")
        this.$emit("changeVisualData", "new_death");
      else if (this.graphType == "集合")
        this.$emit("changeClusterVisualData", "new_death");
      else if (this.graphType == "填色图")
        this.$emit("changeFillVisualData", "deaths");
    },
    switchChoice() {
      if (this.value) {
        this.confirm = true;
        if (this.graphType == "热力图")
          this.$emit("changeVisualData", "new_diagnosis");
        else if (this.graphType == "集合")
          this.$emit("changeClusterVisualData", "new_diagnosis");
        else if (this.graphType == "填色图")
          this.$emit("changeFillVisualData", "confirmed");
      } else {
        this.confirm = false;
        this.recovery = false;
        this.death = false;
        if (this.graphType == "热力图") this.$emit("changeVisualData", "");
        else if (this.graphType == "集合")
          this.$emit("changeClusterVisualData", "");
        else if (this.graphType == "填色图")
          this.$emit("changeFillVisualData", "");
      }
    }
  }
};
</script>