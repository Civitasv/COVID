<template>
  <div id="ModeralityChart">
    <Header :name="name"></Header>
    <div class="main"></div>
  </div>
</template>

<script>
const Header = () => import("../header/Header");
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      chart: null,
      name: "死亡率(World)"
    };
  },
  components: {
    Header
  },
  computed: {
    ...mapState({
      timeData: state => state.virus.allVirusDivideByTime
    })
  },
  methods: {
    ...mapActions("virus", ["getAllVirusDivideByTime"]),
    // Echarts 的 resize 方法
    resizeHandler() {
      this.chart.resize();
    },
    async drawGraph() {
      if (this.timeData == "") await this.getAllVirusDivideByTime();
      this.chart = this.$echarts.init(
        document.querySelector("#ModeralityChart .main"),
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
          name: "Date",
          nameTextStyle: {
            color: "rgba(255, 255, 255, 0.69)",
            fontWeight: "bold",
            fontSize: 16
          },
          axisLabel: {
            fontWeight: "bold",
            fontSize: 14,
            textStyle: {
              color: "white"
            }
          }
        },
        yAxis: {
          type: "value",
          name: "Mortality Rate",
          nameTextStyle: {
            color: "rgba(255, 255, 255, 0.69)",
            fontWeight: "bold",
            fontSize: 16
          },
          axisLabel: {
            textStyle: {
              color: "white",
              fontSize: 14
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
            name: "死亡率",
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
#ModeralityChart,
#ModeralityChart .main {
  width: 100%;
  height: calc(100% - 20px);
}
</style>