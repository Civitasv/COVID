<template>
  <div id="new-increase-trend"></div>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  props: {
    country: String,
    province: String,
    city: String,
  },
  data() {
    return {
      chart: null,
      name: "每日新增趋势",
    };
  },
  computed: {
    ...mapState({
      trendData: (state) => state.virus.newIncrease,
    }),
    location() {
      return this.country + this.province + this.city == ""
        ? "world"
        : this.country + this.province + this.city;
    },
  },
  watch: {
    location() {
      this.drawGraph();
    },
  },
  methods: {
    ...mapActions("virus", ["getWorldNewIncreaseVirusData"]),

    // Echarts 的 resize 方法
    resizeHandler() {
      this.chart.resize();
    },
    async drawGraph() {
      if (!this.trendData[this.location])
        await this.getWorldNewIncreaseVirusData({
          country: this.country,
          province: this.province,
          city: this.city,
        });
      this.chart = this.$echarts.init(
        document.querySelector("#new-increase-trend"),
        "light"
      );

      const option = {
        tooltip: {
          trigger: "axis",
        },
        legend: {
          data: ["新增确诊", "新增死亡", "新增治愈"],
          textStyle: {
            color: "rgba(255, 255, 255, 0.69)",
          },
        },
        grid: {
          left: "5%",
          right: "5%",
          bottom: "0%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          interval: 3,
          data: this.trendData[this.location].map(function (d) {
            var date = new Date(d.timestamp * 1000 + 8 * 3600 * 1000);
            return date.toJSON().substr(0, 10).replace("T", " ");
          }),
          axisLabel: {
            fontSize: 12,
            textStyle: {
              color: "white",
            },
          },
        },
        yAxis: {
          type: "value",
          name: "Number",
          nameTextStyle: {
            color: "rgba(255, 255, 255, 0.69)",
          },
          splitLine: {
            lineStyle: {
              color: "#353535",
              type: "dotted",
            },
          },
          axisLabel: {
            textStyle: {
              color: "white",
              fontSize: 12,
            },
            interval: 4,
            rotate: 30,
          },
        },

        series: [
          {
            name: "新增确诊",
            data: this.trendData[this.location].map(function (d) {
              return d.newConfirmed;
            }),
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#c23531",
              opacity: 1,
            },
            lineStyle: {
              width: 2,
            },
          },
          {
            name: "新增死亡",
            data: this.trendData[this.location].map(function (d) {
              return d.newDeaths;
            }),
            type: "line",
            smooth: true,
            itemStyle: {
              opacity: 1,
              color: "#949fa5",
            },
            lineStyle: {
              width: 2,
            },
          },
          {
            name: "新增治愈",
            data: this.trendData[this.location].map(function (d) {
              return d.newRecovered;
            }),
            type: "line",
            smooth: true,
            itemStyle: {
              color: "#26da6f",
              opacity: 1,
            },
            lineStyle: {
              width: 2,
            },
          },
        ],
      };
      this.chart.setOption(option);
    },
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
  },
};
</script>
<style scoped>
#new-increase-trend {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>