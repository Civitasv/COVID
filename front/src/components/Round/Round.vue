<template>
  <div id="roundchart"></div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      chart: null
    };
  },
  computed: {
    ...mapState({
      countryData: state => state.virus.countryData
    })
  },
  methods: {
    // Echarts 的 resize 方法
    resizeHandler() {
      this.chart.resize();
    },
    drawGraph() {
      this.chart = this.$echarts.init(
        document.getElementById("roundchart"),
        "dark"
      );
      const option = {
        backgroundColor: "#fff",
        title: {
          text: "Top 10 Countries (Recovery Cases)",
          left: "center",
          top: 20,
          textStyle: {
            color: "#000"
          }
        },

        visualMap: {
          show: false,
          min: 0,
          max: 100000,
          inRange: {
            colorLightness: [0.8, 0.3]
          }
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name: "疫情数据",
            data: this.countryData,
            radius: "55%",
            center: ["50%", "50%"],
            type: "pie",
            roseType: "radius",
            label: {
              color: "rgba(0, 0, 0, 0.8)",
              fontSize: 16,
              fontWeight: "bold"
            },
            labelLine: {
              lineStyle: {
                color: "rgba(0,0, 0, 0.3)"
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            },
            itemStyle: {
              color: "#168c92"
            }
          }
        ]
      };
      this.chart.setOption(option);
    },
    ...mapActions("virus", ["getCountryData"])
  },
  async mounted() {
    if (this.countryData == "") await this.getCountryData();
    this.drawGraph();
    var _this = this;
    // 绑定监听事件
    window.addEventListener("resize", _this.resizeHandler);
  },
  beforeDestroy() {
    // 清理工作 避免内存泄漏
    // 销毁监听事件
    window.removeEventListener("resize", this.resizeHandler);
    // 销毁 Echarts 实例
    this.chart.dispose();
  }
};
</script>

<style scoped>
#roundchart {
  width: 100%;
  height: 100vh;
}
</style>