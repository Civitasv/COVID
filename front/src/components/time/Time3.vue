<template>
  <div id="dayAddedChart">
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
      name: "感染治愈趋势(World)"
    };
  },
  computed: {
    ...mapState({
      timeData: state => state.virus.allVirusDivideByTime
    })
  },
  components: {
    Header
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
        document.querySelector("#dayAddedChart .main"),
        "light"
      );

      const option = {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["现存确诊", "治愈", "死亡"],
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
          name: "Number",
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
            name: "现存确诊",
            data: this.timeData.map(function(d) {
              return d.confirmed;
            }),
            type: "bar",
            itemStyle: {
              color: "#c23531",
              opacity: 1
            },
            stack: "virus"
          },
          {
            name: "治愈",
            data: this.timeData.map(function(d) {
              return d.recovery;
            }),
            type: "bar",
            itemStyle: {
              color: "#26da6f",
              opacity: 1
            },
            stack: "virus"
          },
          {
            name: "死亡",
            data: this.timeData.map(function(d) {
              return d.deaths;
            }),
            type: "bar",
            itemStyle: {
              opacity: 1,
              color: "#949fa5"
            },
            stack: "virus"
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
#dayAddedChart,
#dayAddedChart .main {
  width: 100%;
  height: calc(100% - 20px);
}
</style>