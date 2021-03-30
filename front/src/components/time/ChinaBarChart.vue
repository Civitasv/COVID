<template>
  <div id="china-bar-trend"></div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  model: {
    prop: "province",
    event: "changeProvince",
  },
  props: {
    province: String, // 如果指定province, 则显示province下的所有市级数据, 否则显示全部province数据
    timestamp: Number, // 日期时间戳
  },
  data() {
    return {
      chart: null,
      name: "中国病例详情",
    };
  },
  computed: {
    ...mapState({
      countryConfirmed: (state) => state.virus.countryConfirmed,
      countryRecovered: (state) => state.virus.countryRecovered,
      countryDeaths: (state) => state.virus.countryDeaths,
      provinceConfirmed: (state) => state.virus.provinceConfirmed,
      provinceRecovered: (state) => state.virus.provinceRecovered,
      provinceDeaths: (state) => state.virus.provinceDeaths,
    }),
    isProvince() {
      return this.province != "";
    },
  },
  watch: {
    province() {
      this.drawGraph();
    },
    timestamp() {
      this.drawGraph();
    },
  },
  methods: {
    ...mapActions("virus", [
      "getCountryConfirmedVirusData",
      "getCountryRecoveredVirusData",
      "getCountryDeathsVirusData",
      "getProvinceConfirmedVirusData",
      "getProvinceRecoveredVirusData",
      "getProvinceDeathsVirusData",
    ]),
    initChart() {
      this.chart = this.$echarts.init(
        document.querySelector("#china-bar-trend"),
        "light"
      );
    },
    // Echarts 的 resize 方法
    resizeHandler() {
      this.chart.resize();
    },
    async drawGraph() {
      if (this.province == "") {
        if (
          !this.countryConfirmed["China"] ||
          !this.countryConfirmed["China"][this.timestamp]
        )
          await this.getCountryConfirmedVirusData({
            country: "China",
            timestamp: this.timestamp,
          });
        if (
          !this.countryRecovered["China"] ||
          !this.countryRecovered["China"][this.timestamp]
        )
          await this.getCountryRecoveredVirusData({
            country: "China",
            timestamp: this.timestamp,
          });
        if (
          !this.countryDeaths["China"] ||
          !this.countryDeaths["China"][this.timestamp]
        )
          await this.getCountryDeathsVirusData({
            country: "China",
            timestamp: this.timestamp,
          });

        const option = {
          tooltip: {
            trigger: "axis",
          },
          legend: {
            data: ["确诊", "治愈", "死亡"],
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
          yAxis: {
            type: "category",
            interval: 3,
            data: this.countryConfirmed["China"][this.timestamp].map((d) => {
              return d.combinedKey;
            }),
            axisLabel: {
              fontSize: 12,
              textStyle: {
                color: "white",
              },
            },
          },
          xAxis: {
            type: "value",
            name: "Number",
            nameTextStyle: {
              color: "rgba(255, 255, 255, 0.69)",
              fontSize: 12,
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
          dataZoom: [
            {
              type: "inside",
              show: false,
              yAxisIndex: [0],
              start: 0,
              end: 20,
            },
          ],
          series: [
            {
              name: "确诊",
              data: this.countryConfirmed["China"][this.timestamp].map((d) => {
                return d.val;
              }),
              type: "bar",
              itemStyle: {
                color: "#c23531",
                opacity: 1,
              },
              stack: "virus",
            },
            {
              name: "治愈",
              data: this.countryRecovered["China"][this.timestamp].map((d) => {
                return d.val;
              }),
              type: "bar",
              itemStyle: {
                color: "#26da6f",
                opacity: 1,
              },
              stack: "virus",
            },
            {
              name: "死亡",
              data: this.countryDeaths["China"][this.timestamp].map((d) => {
                return d.val;
              }),
              type: "bar",
              itemStyle: {
                opacity: 1,
                color: "#949fa5",
              },
              stack: "virus",
            },
          ],
        };
        this.chart.setOption(option);
      } else {
        if (
          !this.provinceConfirmed["China"] ||
          !this.provinceConfirmed["China"][this.province] ||
          !this.provinceConfirmed["China"][this.province][this.timestamp]
        )
          await this.getProvinceConfirmedVirusData({
            country: "China",
            province: this.province,
            timestamp: this.timestamp,
          });
        if (
          !this.provinceRecovered["China"] ||
          !this.provinceRecovered["China"][this.province] ||
          !this.provinceRecovered["China"][this.province][this.timestamp]
        )
          await this.getProvinceRecoveredVirusData({
            country: "China",
            province: this.province,
            timestamp: this.timestamp,
          });
        if (
          !this.provinceDeaths["China"] ||
          !this.provinceDeaths["China"][this.province] ||
          !this.provinceDeaths["China"][this.province][this.timestamp]
        )
          await this.getProvinceDeathsVirusData({
            country: "China",
            province: this.province,
            timestamp: this.timestamp,
          });

        let x, confirmed, recovered, deaths;
        if (
          !this.provinceConfirmed["China"][this.province][this.timestamp] ||
          this.provinceConfirmed["China"][this.province][this.timestamp]
            .length == 0
        ) {
          x = [this.province];
          if (
            !this.countryConfirmed["China"] ||
            !this.countryConfirmed["China"][this.timestamp]
          )
            await this.getCountryConfirmedVirusData({
              country: "China",
              timestamp: this.timestamp,
            });
          if (
            !this.countryRecovered["China"] ||
            !this.countryRecovered["China"][this.timestamp]
          )
            await this.getCountryRecoveredVirusData({
              country: "China",
              timestamp: this.timestamp,
            });
          if (
            !this.countryDeaths["China"] ||
            !this.countryDeaths["China"][this.timestamp]
          )
            await this.getCountryDeathsVirusData({
              country: "China",
              timestamp: this.timestamp,
            });
          confirmed = [
            this.countryConfirmed["China"][this.timestamp][
              this.countryConfirmed["China"][this.timestamp].findIndex(
                (item) => {
                  return item.combinedKey == this.province;
                }
              )
            ].val,
          ];
          recovered = [
            this.countryRecovered["China"][this.timestamp][
              this.countryRecovered["China"][this.timestamp].findIndex(
                (item) => {
                  return item.combinedKey == this.province;
                }
              )
            ].val,
          ];
          deaths = [
            this.countryDeaths["China"][this.timestamp][
              this.countryDeaths["China"][this.timestamp].findIndex((item) => {
                return item.combinedKey == this.province;
              })
            ].val,
          ];
        } else {
          x = this.provinceConfirmed["China"][this.province][
            this.timestamp
          ].map((d) => {
            return d.combinedKey;
          });
          confirmed = this.provinceConfirmed["China"][this.province][
            this.timestamp
          ].map((d) => {
            return d.val;
          });
          if (this.provinceRecovered["China"][this.province][this.timestamp])
            recovered = this.provinceRecovered["China"][this.province][
              this.timestamp
            ].map((d) => {
              return d.val;
            });
          if (this.provinceDeaths["China"][this.province][this.timestamp])
            deaths = this.provinceDeaths["China"][this.province][
              this.timestamp
            ].map((d) => {
              return d.val;
            });
        }
        const option = {
          tooltip: {
            trigger: "axis",
          },
          legend: {
            data: ["确诊", "治愈", "死亡"],
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
          yAxis: {
            type: "category",
            interval: 3,
            data: x,
            axisLabel: {
              fontSize: 12,
              textStyle: {
                color: "white",
              },
            },
          },
          xAxis: {
            type: "value",
            name: "Number",
            nameTextStyle: {
              color: "rgba(255, 255, 255, 0.69)",
              fontSize: 12,
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
          dataZoom: [
            {
              type: "inside",
              start: 0,
              end: 20,
            },
            {
              type: "slider",
              show: true,
              yAxisIndex: 0,
              start: 0,
              end: 20,
              left: "93%",
            },
          ],
          series: [
            {
              name: "确诊",
              data: confirmed,
              type: "bar",
              emphasis: {
                focus: "series",
              },
              itemStyle: {
                color: "#c23531",
                opacity: 1,
              },
              stack: "virus",
            },
            {
              name: "治愈",
              data: recovered,
              emphasis: {
                focus: "series",
              },
              type: "bar",
              itemStyle: {
                color: "#26da6f",
                opacity: 1,
              },
              stack: "virus",
            },
            {
              name: "死亡",
              data: deaths,
              emphasis: {
                focus: "series",
              },
              type: "bar",
              itemStyle: {
                opacity: 1,
                color: "#949fa5",
              },
              stack: "virus",
            },
          ],
        };
        this.chart.setOption(option);
      }

      this.chart.on("click", (params) => {
        if (!this.isProvince) {
          this.$emit("changeProvince", params.name);
        }
      });
    },
  },
  mounted() {
    this.initChart();
    this.drawGraph();
    // 绑定监听事件
    window.addEventListener("resize", this.resizeHandler);
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
#china-bar-trend {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>