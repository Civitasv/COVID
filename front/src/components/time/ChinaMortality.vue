<template>
  <div id="ChinaModeralityChart">
    <Header :name="name"></Header>
    <div class="main"></div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const Header = () => import("../header/Header");

export default {
  data() {
    return {
      chart: null,
      name: "死亡率(China)"
    };
  },
  components: { Header },
  computed: {
    ...mapState({
      timeData: state => state.virus.allChinaVirusDivideByTime
    })
  },
  methods: {
    ...mapActions("virus", ["getAllChinaVirusDivideByTime"]),
    // Echarts 的 resize 方法
    resizeHandler() {
      this.chart.resize();
    },
    async drawGraph() {
      if (this.timeData == "") await this.getAllChinaVirusDivideByTime();
      this.chart = this.$echarts.init(
        document.querySelector("#ChinaModeralityChart .main"),
        "light"
      );

      const option = {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["Confirmed", "Deaths", "Recovery"],
          textStyle: {
            color: "rgba(255, 255, 255, 0.69)"
          }
        },
        xAxis: {
          type: "category",
          data: this.timeData.map(function(d) {
            return d.public_date;
          }),

          axisLabel: {
            fontSize: 12,
            textStyle: {
              color: "whitesmoke"
            }
          }
        },
        yAxis: {
          type: "value",
          axisLabel: {
            textStyle: {
              color: "whitesmoke",
              fontSize: 12
            },
            fontWeight: "bold"
          },
          splitLine: {
            lineStyle: {
              color: ["rgba(230, 230, 230, 0.2)"]
            }
          }
        },
        series: [
          {
            name: "Mortality Rate",
            data: this.timeData.map(function(d) {
              return (d.deaths * 1.0) / d.confirmed;
            }),
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#a32222",
              opacity: 1
            },
            lineStyle: {
              width: 3
            }
          }
        ]
      };
      this.chart.setOption(option);
    }
  },
  mounted() {
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
#ChinaModeralityChart,
#ChinaModeralityChart .main {
  width: 100%;
  height: calc(100% - 20px);
}
</style>