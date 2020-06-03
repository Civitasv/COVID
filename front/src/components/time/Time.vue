<template>
  <div id="dayChart">
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
      name: "新增趋势(World)"
    };
  },
  components: {
    Header
  },
  computed: {
    ...mapState({
      timeData: state => state.virus.virusDivideByTime
    })
  },
  methods: {
    ...mapActions("virus", ["getVirusDivideByTime"]),

    // Echarts 的 resize 方法
    resizeHandler() {
      this.chart.resize();
    },
    async drawGraph() {
      if (this.timeData == "") await this.getVirusDivideByTime();
      this.chart = this.$echarts.init(
        document.querySelector("#dayChart .main"),
        "light"
      );

      const option = {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          data: ["新增确诊", "新增死亡", "新增治愈"],
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
            name: "新增确诊",
            data: this.timeData.map(function(d) {
              return d.new_diagnosis;
            }),
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#c23531",
              opacity: 1
            },
            lineStyle: {
              width: 3
            }
          },
          {
            name: "新增死亡",
            data: this.timeData.map(function(d) {
              return d.new_death;
            }),
            type: "line",
            smooth: true,
            itemStyle: {
              opacity: 1,
              color: "#949fa5"
            },
            lineStyle: {
              width: 3
            }
          },
          {
            name: "新增治愈",
            data: this.timeData.map(function(d) {
              return d.new_recovery;
            }),
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#26da6f",
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
#dayChart,
#dayChart .main {
  width: 100%;
  height: calc(100% - 20px);
}
</style>