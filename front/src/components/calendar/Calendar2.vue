<template>
  <div id="calendarChart2">
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
      name: "日治愈量(China)"
    };
  },
  components: { Header },
  computed: {
    ...mapState({
      timeData: state => state.virus.chinaVirusDivideByTime
    })
  },
  methods: {
    ...mapActions("virus", ["getChinaVirusDivideByTime"]),
    // Echarts 的 resize 方法
    resizeHandler() {
      this.chart.resize();
    },

    async drawGraph() {
      if (this.timeData == "") await this.getChinaVirusDivideByTime();
      this.chart = this.$echarts.init(
        document.querySelector("#calendarChart2 .main"),
        "light"
      );

      const option = {
        tooltip: {
          trigger: "item"
        },
        legend: {
          textStyle: {
            color: "#fff"
          }
        },
        visualMap: {
          top: 65,
          orient: "horizontal",
          left: "center",
          show: false,
          min: 0,
          max: 3800,
          inRange: {
            colorLightness: [0.8, 0]
          }
        },

        calendar: {
          top: "center",
          left: "60",
          cellSize: ["auto", 25],
          range: ["2020-01-12", "2020-04-10"],
          splitLine: {
            show: true,
            lineStyle: {
              color: "#000",
              width: 2,
              type: "solid"
            }
          },
          itemStyle: {
            color: "#323c48",
            borderWidth: 1,
            borderColor: "#111"
          },
          monthLabel: {
            color: "whitesmoke"
          },
          dayLabel: {
            color: "whitesmoke"
          },
          yearLabel: {
            show: false
          }
        },
        series: [
          {
            type: "effectScatter",
            coordinateSystem: "calendar",
            data: this.timeData.map(function(d) {
              return [d.public_date, d.new_recovery];
            }),
            symbolSize: function(val) {
              if (val[1] < 100) return 2;
              else if (val[1] < 400) return 4;
              else if (val[1] < 2000) return 8;
              else return 16;
            },
            showEffectOn: "render",
            rippleEffect: {
              brushType: "stroke"
            },
            hoverAnimation: true,
            itemStyle: {
              color: "#0dc420",
              shadowBlur: 2,
              shadowColor: "#333"
            },
            zlevel: 1
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
#calendarChart2,
#calendarChart2 .main {
  width: 95%;
  height: 100%;
}
</style>