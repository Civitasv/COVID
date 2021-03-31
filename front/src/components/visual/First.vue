<template>
  <el-container id="sub_container">
    <el-aside class="left">
      <div class="top">
        <!-- 病例信息 -->
        <div class="info">
          <div class="title">
            {{ info_title }}
          </div>
          <div :class="caseClass" v-show="cases > 0">
            {{ cases }}
          </div>
        </div>
        <div class="virus_table">
          <div class="title">{{ table_title }}</div>
          <div class="table">
            <el-table
              height="375"
              v-show="tableIndex == 0"
              :data="tableCountryData"
              style="background-color: #222222"
              :show-header="false"
              :row-class-name="tableRowClassName"
              :cell-class-name="tableCellClassName"
              @row-click="tableRowClick"
            >
              <el-table-column :prop="table_attr" label="值"> </el-table-column>
              <el-table-column prop="combinedKey" label="地点">
              </el-table-column>
            </el-table>
            <el-table
              height="375"
              v-show="tableIndex == 1"
              :data="tableProvinceData"
              style="background-color: #222222"
              :show-header="false"
              :row-class-name="tableRowClassName"
              :cell-class-name="tableCellClassName"
              @row-click="tableRowClick"
            >
              <el-table-column :prop="table_attr" label="值"> </el-table-column>
              <el-table-column prop="combinedKey" label="地点">
              </el-table-column>
            </el-table>
            <el-table
              height="375"
              v-show="tableIndex == 2"
              :data="tableCityData"
              style="background-color: #222222"
              :show-header="false"
              :row-class-name="tableRowClassName"
              :cell-class-name="tableCellClassName"
              @row-click="tableRowClick"
            >
              <el-table-column :prop="table_attr" label="值"> </el-table-column>
              <el-table-column prop="combinedKey" label="地点">
              </el-table-column>
            </el-table>
          </div>
        </div>
        <div class="wrapper">
          <div class="table-switch">
            <SwitchKey
              :contents="table_switch_data"
              v-model="tableIndex"
            ></SwitchKey>
          </div>
        </div>
      </div>
      <!-- 时间-->
      <div class="bottom">
        <DateTime :date_time="date_time"></DateTime>
      </div>
    </el-aside>
    <el-main class="map">
      <div class="layers">
        <!-- 分布图 -->
        <div id="distribution"></div>
        <!-- 时间控件-->
        <div id="slider-wrapper">
          <div
            id="playButton"
            class="esri-widget esri-widget--button toggle-button"
          >
            <!-- <div>
                    <span class="toggle-button-icon esri-icon-play" aria-label="play icon"></span> Play
                </div> -->
            <div>
              <span
                class="toggle-button-icon esri-icon-play"
                aria-label="play icon"
              ></span>
              Play
            </div>
            <div>
              <span
                class="toggle-button-icon esri-icon-pause"
                aria-label="pause icon"
              ></span>
              Pause
            </div>
          </div>
          <div id="timeSlider" class="esri-widget"></div>
        </div>
      </div>
    </el-main>
    <el-aside class="right">
      <!-- 图层选择 -->
      <div id="layerNames"></div>
      <!-- 图表可视化 -->
      <div class="chart">
        <IncreaseChart
          :country="table_active_country"
          :province="table_active_province"
          :city="table_active_city"
          v-if="chartIndex == 0"
        ></IncreaseChart>
        <ConfirmedRecoveredChart
          :country="table_active_country"
          :province="table_active_province"
          :city="table_active_city"
          v-if="chartIndex == 1"
        ></ConfirmedRecoveredChart>
      </div>
      <div class="wrapper">
        <div class="chart-switch">
          <SwitchKey
            :contents="chart_switch_data"
            v-model="chartIndex"
          ></SwitchKey>
        </div>
      </div>
    </el-aside>
  </el-container>
</template>

<style scoped>
#sub_container {
  padding-left: 20px;
  padding-right: 20px;
}
#sub_container .left,
#sub_container .right {
  display: flex;
  flex-direction: column;
}
#sub_container .left {
  flex: 2;
}
#sub_container .right {
  flex: 3;
}
.right #layerNames {
  border: 1px solid #363636;
  background-color: #222222;
  flex: 3;
}
.right .chart {
  border-width: 1px 1px 0px 1px;
  border-color: #363636;
  border-style: solid;
  background-color: #222222;
  flex: 2;
  margin-top: 10px;
}
.right .chart-switch {
  border-width: 0 1px 1px 1px;
  border-color: #363636;
  border-style: solid;
  background-color: #222222;
  margin-top: -1px;
}
.right .wrapper {
  border-top: 1px solid #363636;
  padding: 0 20px 10px 20px;
}
#sub_container .map {
  padding: 0px 10px 10px 10px;
  flex: 6;
}
.info {
  border: 1px solid #363636;
  background-color: #222222;
  padding: 20px 0px 20px 0px;
}
.info .title {
  color: white;
  font-size: 20px;
}
.info .case {
  stroke-width: 2;
  font-size: 30px;
  line-height: normal;
  font-weight: bold;
  margin: 0 auto;
}
.layers,
#distribution {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
}
.top {
  flex: 1;
  display: flex;
  flex-direction: column;
}
.bottom {
  padding-bottom: 10px;
}
.virus_table {
  flex: 1;
  border-width: 1px 1px 0px 1px;
  border-color: #363636;
  border-style: solid;
  background-color: #222222;
  margin-top: 10px;
  padding: 20px 0px 0px 0px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.virus_table .title {
  color: white;
  font-size: 20px;
}
.virus_table .table {
  width: 100%;
}
.left .table-switch {
  border-width: 0 1px 1px 1px;
  border-color: #363636;
  border-style: solid;
  background-color: #222222;
  margin-top: -1px;
}
.left .wrapper {
  border-top: 1px solid #363636;
  padding: 0 20px 10px 20px;
  margin-bottom: 10px;
}
.text-red {
  color: #e60000;
}
.text-green {
  color: #26da6f;
}
.text-gray {
  color: #949fa5;
}

#slider-wrapper {
  display: flex;
  align-items: center;
  background-color: #242424;
  z-index: 100;
}
/*Play/Stop toggle button */

#playButton {
  height: 100%;
  width: 100%;
  margin: 5px;
  min-width: 60px;
  background-color: #242424;
}

.toggle-button {
  display: flex;
}

.toggle-button.toggled .toggle-button-icon {
  color: #cc1b1b;
}

.toggle-button .toggle-button-icon {
  color: #1bcc1b;
}

.toggle-button > :nth-child(2) {
  display: none;
}

.toggle-button.toggled > :nth-child(1) {
  display: none;
}

.toggle-button.toggled > :nth-child(2) {
  display: block;
}
</style>

<script>
import { loadModules } from "esri-loader";
import { mapState, mapActions } from "vuex";
import { DateStringToTimestamp } from "../../assets/js/DateUtil";
const DateTime = () => import("../time/DateTime");
const SwitchKey = () => import("../switch/SwitchKey");
const IncreaseChart = () => import("../time/NewIncreaseTrendChart");
const ConfirmedRecoveredChart = () =>
  import("../time/ConfirmedRecoveredTrendChart");
export default {
  data() {
    return {
      info_title: "累计确诊",
      cases: 0,
      date_time: "2021-01-12",
      date_timestamp: 1610380800,
      table_title: "数据一览",
      // 表格展示数据
      table_attr: "val",
      tableCountryData: [],
      tableProvinceData: [],
      tableCityData: [],
      table_switch_data: ["Country", "Province/State", "City/County"],
      tableIndex: 0,
      chart_switch_data: ["每日新增趋势", "感染治愈趋势"],
      chartIndex: 0,
      table_active_country_index: -1,
      table_active_country: "",
      table_active_province_index: -1,
      table_active_province: "",
      table_active_city_index: -1,
      table_active_city: "",
      showIndex: 0,
      isPlaying: false,
    };
  },
  watch: {
    tableIndex() {
      this.showTableData();
    },
  },
  computed: {
    ...mapState({
      worldConfirmedTable: (state) => state.virus.worldConfirmed,
      worldRecoveredTable: (state) => state.virus.worldRecovered,
      worldDeathsTable: (state) => state.virus.worldDeaths,
      worldActiveTable: (state) => state.virus.worldActive,
      worldDeathsRatioTable: (state) => state.virus.worldDeathsRatio,
      countryConfirmedTable: (state) => state.virus.countryConfirmed,
      countryRecoveredTable: (state) => state.virus.countryRecovered,
      countryDeathsTable: (state) => state.virus.countryDeaths,
      countryActiveTable: (state) => state.virus.countryActive,
      countryDeathsRatioTable: (state) => state.virus.countryDeathsRatio,
      provinceConfirmedTable: (state) => state.virus.provinceConfirmed,
      provinceRecoveredTable: (state) => state.virus.provinceRecovered,
      provinceDeathsTable: (state) => state.virus.provinceDeaths,
      provinceActiveTable: (state) => state.virus.provinceActive,
      provinceDeathsRatioTable: (state) => state.virus.provinceDeathsRatio,
    }),
    confirmedUrl() {
      return "http://localhost/virus/confirmed/" + this.date_timestamp;
    },
    recoveredUrl() {
      return "http://localhost/virus/recovered/" + this.date_timestamp;
    },
    deathsUrl() {
      return "http://localhost/virus/deaths/" + this.date_timestamp;
    },
    activeUrl() {
      return "http://localhost/virus/active/" + this.date_timestamp;
    },
    deathsRatioUrl() {
      return "http://localhost/virus/deathsRatio/" + this.date_timestamp;
    },
    caseClass() {
      if (this.info_title == "累计确诊" || this.info_title == "现存确诊") {
        return "case text-red";
      } else if (this.info_title == "累计治愈") {
        return "case text-green";
      } else if (this.info_title == "累计死亡" || this.info_title == "死亡率") {
        return "case text-gray";
      }
      return "case";
    },
  },
  methods: {
    ...mapActions("virus", [
      "getWorldConfirmedVirusData",
      "getWorldRecoveredVirusData",
      "getWorldDeathsVirusData",
      "getWorldActiveVirusData",
      "getWorldDeathsRatioData",
      "getCountryConfirmedVirusData",
      "getCountryRecoveredVirusData",
      "getCountryDeathsVirusData",
      "getCountryActiveVirusData",
      "getCountryDeathsRatioData",
      "getProvinceConfirmedVirusData",
      "getProvinceRecoveredVirusData",
      "getProvinceDeathsVirusData",
      "getProvinceActiveVirusData",
      "getProvinceDeathsRatioData",
    ]),
    sleep(ms) {
      return new Promise((resolve) => setTimeout(resolve, ms));
    },
    async showTableData() {
      this.date_timestamp = DateStringToTimestamp(this.date_time);
      if (this.tableIndex == 0) {
        // 展示世界各国家数据
        if (this.info_title == "累计确诊") {
          if (!this.worldConfirmedTable[this.date_timestamp])
            // 等待执行完毕，相当于this.getWorldConfirmedVirusData(this.date_timestamp).then(()=>{this.tableData = this.worldConfirmedTable[this.date_timestamp];...})
            await this.getWorldConfirmedVirusData(this.date_timestamp);
          this.tableCountryData = this.worldConfirmedTable[this.date_timestamp];
        } else if (this.info_title == "累计治愈") {
          if (!this.worldRecoveredTable[this.date_timestamp])
            await this.getWorldRecoveredVirusData(this.date_timestamp);
          this.tableCountryData = this.worldRecoveredTable[this.date_timestamp];
        } else if (this.info_title == "累计死亡") {
          if (!this.worldDeathsTable[this.date_timestamp])
            await this.getWorldDeathsVirusData(this.date_timestamp);
          this.tableCountryData = this.worldDeathsTable[this.date_timestamp];
        } else if (this.info_title == "现存确诊") {
          if (!this.worldActiveTable[this.date_timestamp])
            await this.getWorldActiveVirusData(this.date_timestamp);
          this.tableCountryData = this.worldActiveTable[this.date_timestamp];
        } else if (this.info_title == "死亡率") {
          if (!this.worldDeathsRatioTable[this.date_timestamp])
            await this.getWorldDeathsRatioData(this.date_timestamp);
          this.tableCountryData = this.worldDeathsRatioTable[
            this.date_timestamp
          ];
        }
        if (this.tableCountryData.length == 0) return;
        var sum = 0;
        this.tableCountryData.forEach((item) => {
          sum += item.val;
        });
        if (this.info_title != "死亡率") {
          this.cases = sum;
        } else {
          this.cases = 0;
        }
      } else if (this.tableIndex == 1) {
        if (this.table_active_country === "") {
          this.tableProvinceData = [];
          return;
        }
        // 展示国家各省份数据
        if (this.info_title == "累计确诊") {
          if (
            !this.countryConfirmedTable[this.table_active_country] ||
            !this.countryConfirmedTable[this.table_active_country][
              this.date_timestamp
            ]
          )
            await this.getCountryConfirmedVirusData({
              country: this.table_active_country,
              timestamp: this.date_timestamp,
            });
          this.tableProvinceData = this.countryConfirmedTable[
            this.table_active_country
          ][this.date_timestamp];
        } else if (this.info_title == "累计治愈") {
          if (
            !this.countryRecoveredTable[this.table_active_country] ||
            !this.countryRecoveredTable[this.table_active_country][
              this.date_timestamp
            ]
          )
            await this.getCountryRecoveredVirusData({
              country: this.table_active_country,
              timestamp: this.date_timestamp,
            });
          this.tableProvinceData = this.countryRecoveredTable[
            this.table_active_country
          ][this.date_timestamp];
        } else if (this.info_title == "累计死亡") {
          if (
            !this.countryDeathsTable[this.table_active_country] ||
            !this.countryDeathsTable[this.table_active_country][
              this.date_timestamp
            ]
          )
            await this.getCountryDeathsVirusData({
              country: this.table_active_country,
              timestamp: this.date_timestamp,
            });
          this.tableProvinceData = this.countryDeathsTable[
            this.table_active_country
          ][this.date_timestamp];
          if (this.tableProvinceData.length == 0) return;
        } else if (this.info_title == "现存确诊") {
          if (
            !this.countryActiveTable[this.table_active_country] ||
            !this.countryActiveTable[this.table_active_country][
              this.date_timestamp
            ]
          )
            await this.getCountryActiveVirusData({
              country: this.table_active_country,
              timestamp: this.date_timestamp,
            });
          this.tableProvinceData = this.countryActiveTable[
            this.table_active_country
          ][this.date_timestamp];
          if (this.tableProvinceData.length == 0) return;
        } else if (this.info_title == "死亡率") {
          if (
            !this.countryDeathsRatioTable[this.table_active_country] ||
            !this.countryDeathsRatioTable[this.table_active_country][
              this.date_timestamp
            ]
          )
            await this.getCountryDeathsRatioData({
              country: this.table_active_country,
              timestamp: this.date_timestamp,
            });
          this.tableProvinceData = this.countryDeathsRatioTable[
            this.table_active_country
          ][this.date_timestamp];
          if (this.tableProvinceData.length == 0) return;
        }
        this.cases = this.tableCountryData[this.table_active_country_index].val;
      } else if (this.tableIndex == 2) {
        if (
          this.table_active_country === "" ||
          this.table_active_province === ""
        ) {
          this.tableCityData = [];
          return;
        }
        // 展示国家各省份数据
        if (this.info_title == "累计确诊") {
          if (
            !this.provinceConfirmedTable[this.table_active_country] ||
            !this.provinceConfirmedTable[this.table_active_country][
              this.table_active_province
            ] ||
            !this.provinceConfirmedTable[this.table_active_country][
              this.table_active_province
            ][this.date_timestamp]
          )
            await this.getProvinceConfirmedVirusData({
              country: this.table_active_country,
              province: this.table_active_province,
              timestamp: this.date_timestamp,
            });
          this.tableCityData = this.provinceConfirmedTable[
            this.table_active_country
          ][this.table_active_province][this.date_timestamp];
        } else if (this.info_title == "累计治愈") {
          if (
            !this.provinceRecoveredTable[this.table_active_country] ||
            !this.provinceRecoveredTable[this.table_active_country][
              this.table_active_province
            ] ||
            !this.provinceRecoveredTable[this.table_active_country][
              this.table_active_province
            ][this.date_timestamp]
          )
            await this.getProvinceRecoveredVirusData({
              country: this.table_active_country,
              province: this.table_active_province,
              timestamp: this.date_timestamp,
            });
          this.tableCityData = this.provinceRecoveredTable[
            this.table_active_country
          ][this.table_active_province][this.date_timestamp];
        } else if (this.info_title == "累计死亡") {
          if (
            !this.provinceDeathsTable[this.table_active_country] ||
            !this.provinceDeathsTable[this.table_active_country][
              this.table_active_province
            ] ||
            !this.provinceDeathsTable[this.table_active_country][
              this.table_active_province
            ][this.date_timestamp]
          )
            await this.getProvinceDeathsVirusData({
              country: this.table_active_country,
              province: this.table_active_province,
              timestamp: this.date_timestamp,
            });
          this.tableCityData = this.provinceDeathsTable[
            this.table_active_country
          ][this.table_active_province][this.date_timestamp];
        } else if (this.info_title == "现存确诊") {
          if (
            !this.provinceActiveTable[this.table_active_country] ||
            !this.provinceActiveTable[this.table_active_country][
              this.table_active_province
            ] ||
            !this.provinceActiveTable[this.table_active_country][
              this.table_active_province
            ][this.date_timestamp]
          )
            await this.getProvinceActiveVirusData({
              country: this.table_active_country,
              province: this.table_active_province,
              timestamp: this.date_timestamp,
            });
          this.tableCityData = this.provinceActiveTable[
            this.table_active_country
          ][this.table_active_province][this.date_timestamp];
        } else if (this.info_title == "死亡率") {
          if (
            !this.provinceDeathsRatioTable[this.table_active_country] ||
            !this.provinceDeathsRatioTable[this.table_active_country][
              this.table_active_province
            ] ||
            !this.provinceDeathsRatioTable[this.table_active_country][
              this.table_active_province
            ][this.date_timestamp]
          )
            await this.getProvinceDeathsRatioData({
              country: this.table_active_country,
              province: this.table_active_province,
              timestamp: this.date_timestamp,
            });
          this.tableCityData = this.provinceDeathsRatioTable[
            this.table_active_country
          ][this.table_active_province][this.date_timestamp];
        }
        this.cases = this.tableProvinceData[
          this.table_active_province_index
        ].val;
      }
    },
    showDistributionMap() {
      const options = {
        url: "https://js.arcgis.com/4.18/",
        css: "https://js.arcgis.com/4.18/esri/themes/dark/main.css",
      };
      loadModules(
        [
          "esri/Map",
          "esri/views/MapView",
          "esri/layers/GeoJSONLayer",
          "esri/widgets/Legend",
          "esri/widgets/Expand",
          "esri/widgets/TimeSlider",
          "esri/widgets/BasemapGallery",
          "esri/Basemap",
          "esri/widgets/LayerList",
          "esri/layers/GroupLayer",
        ],
        options
      )
        .then(
          ([
            Map,
            MapView,
            GeoJSONLayer,
            Legend,
            Expand,
            TimeSlider,
            BasemapGallery,
            Basemap,
            LayerList,
            GroupLayer,
          ]) => {
            // 默认marker
            const confirmedSym = {
              type: "simple-marker", // autocasts as new SimpleMarkerSymbol()
              style: "circle",
              color: [255, 0, 0, 0.5],
              outline: {
                color: [255, 0, 0, 0.5],
                width: 1,
              },
            };
            const recoveredSym = {
              type: "simple-marker", // autocasts as new SimpleMarkerSymbol()
              style: "circle",
              color: [38, 218, 111, 0.5],
              outline: {
                color: [38, 218, 111, 0.5],
                width: 1,
              },
            };
            const deathsSym = {
              type: "simple-marker", // autocasts as new SimpleMarkerSymbol()
              style: "circle",
              color: [148, 159, 165, 0.5],
              outline: {
                color: [148, 159, 165, 0.5],
                width: 1,
              },
            };
            const activeSym = {
              type: "simple-marker", // autocasts as new SimpleMarkerSymbol()
              style: "circle",
              color: [250, 204, 219, 0.5],
              outline: {
                color: [250, 204, 219, 0.5],
                width: 1,
              },
            };
            const confirmedRenderer = {
              type: "simple", // autocasts as new SimpleRenderer()
              symbol: confirmedSym,
              visualVariables: [
                {
                  type: "size",
                  field: "confirmed",
                  legendOptions: {
                    title: "累计确诊人数",
                  },
                  stops: [
                    { value: 0, size: 0 },
                    { value: 1, size: "2px" },
                    { value: 100, size: "4px" },
                    { value: 1000, size: "8px" },
                    { value: 10000, size: "10px" },
                    { value: 200000, size: "30px" },
                    { value: 300000, size: "120px" },
                  ],
                },
              ],
            };
            const recoveredRenderer = {
              type: "simple", // autocasts as new SimpleRenderer()
              symbol: recoveredSym,
              visualVariables: [
                {
                  type: "size",
                  field: "recovered",
                  legendOptions: {
                    title: "累计治愈人数",
                  },
                  stops: [
                    { value: 0, size: 0 },
                    { value: 1, size: "2px" },
                    { value: 100, size: "4px" },
                    { value: 1000, size: "8px" },
                    { value: 10000, size: "10px" },
                    { value: 200000, size: "30px" },
                    { value: 300000, size: "120px" },
                  ],
                },
              ],
            };
            const deathsRenderer = {
              type: "simple", // autocasts as new SimpleRenderer()
              symbol: deathsSym,
              visualVariables: [
                {
                  type: "size",
                  field: "deaths",
                  legendOptions: {
                    title: "累计死亡人数",
                  },
                  stops: [
                    { value: 0, size: 0 },
                    { value: 1, size: "3px" },
                    { value: 100, size: "8px" },
                    { value: 1000, size: "18px" },
                    { value: 20000, size: "30px" },
                    { value: 30000, size: "100px" },
                  ],
                },
              ],
            };

            const activeRenderer = {
              type: "simple", // autocasts as new SimpleRenderer()
              symbol: activeSym,
              visualVariables: [
                {
                  type: "size",
                  field: "active",
                  legendOptions: {
                    title: "现存确诊人数",
                  },
                  stops: [
                    { value: 0, size: 0 },
                    { value: 1, size: "8px" },
                    { value: 100, size: "10px" },
                    { value: 1000, size: "15px" },
                    { value: 10000, size: "20px" },
                    { value: 500000, size: "120px" },
                  ],
                },
              ],
            };

            const activeClusterConfig = {
              type: "cluster",
              clusterRadius: "100px",
              // {cluster_count} is an aggregate field containing
              // the number of features comprised by the cluster
              popupTemplate: {
                content: "该聚类共包括 {cluster_avg_active} 个现存确诊病例.",
                fieldInfos: [
                  {
                    fieldName: "cluster_avg_active",
                    format: {
                      places: 0,
                      digitSeparator: true,
                    },
                  },
                ],
              },
              clusterMinSize: "24px",
              clusterMaxSize: "60px",
            };
            const deathsRatioRenderer = {
              type: "heatmap",
              field: "ratio",
              blurRadius: 8,
              colorStops: [
                { color: "rgba(0, 0, 0, 0)", ratio: 0 },
                { color: "#f7f7f7", ratio: 0.05 },
                { color: "#d9d9d9", ratio: 0.15 },
                { color: "#bdbdbd", ratio: 0.25 },
                { color: "#969696", ratio: 0.4 },
                { color: "#636363", ratio: 0.6 },
                { color: "#252525", ratio: 1 },
              ],
              maxPixelIntensity: 1,
              minPixelIntensity: 0,
            };
            var addLayers = async () => {
              const confirmedLayer = new GeoJSONLayer({
                url: this.confirmedUrl,
                renderer: confirmedRenderer,
                title: "COVID-19累计确诊（分布图）",
                popupTemplate: {
                  // autocasts as new PopupTemplate()
                  title: "<font color='#FFFFFF'>{combined_key}",
                  content: [
                    {
                      type: "fields",
                      fieldInfos: [
                        {
                          fieldName: "confirmed",
                          label: "累计确诊",
                        },
                      ],
                    },
                  ],
                },
              });
              const recoveredLayer = new GeoJSONLayer({
                url: this.recoveredUrl,
                renderer: recoveredRenderer,
                title: "COVID-19累计治愈（分布图）",
                popupTemplate: {
                  // autocasts as new PopupTemplate()
                  title: "<font color='#FFFFFF'>{combined_key}",
                  content: [
                    {
                      type: "fields",
                      fieldInfos: [
                        {
                          fieldName: "recovered",
                          label: "累计治愈",
                        },
                      ],
                    },
                  ],
                },
              });
              const deathsLayer = new GeoJSONLayer({
                url: this.deathsUrl,
                renderer: deathsRenderer,
                title: "COVID-19累计死亡（分布图）",
                popupTemplate: {
                  // autocasts as new PopupTemplate()
                  title: "<font color='#FFFFFF'>{combined_key}",
                  content: [
                    {
                      type: "fields",
                      fieldInfos: [
                        {
                          fieldName: "deaths",
                          label: "累计死亡",
                        },
                      ],
                    },
                  ],
                },
              });
              const clusterActiveLayer = new GeoJSONLayer({
                url: this.activeUrl,
                renderer: activeRenderer,
                title: "COVID-19现存确诊（聚合图）",
                featureReduction: activeClusterConfig,
                popupTemplate: {
                  // autocasts as new PopupTemplate()
                  title: "<font color='#FFFFFF'>{combined_key}",
                  content: [
                    {
                      type: "fields",
                      fieldInfos: [
                        {
                          fieldName: "active",
                          label: "现存确诊",
                        },
                      ],
                    },
                  ],
                },
              });
              const activeLayer = new GeoJSONLayer({
                url: this.activeUrl,
                renderer: activeRenderer,
                title: "COVID-19现存确诊（分布图）",
                popupTemplate: {
                  // autocasts as new PopupTemplate()
                  title: "<font color='#FFFFFF'>{combined_key}",
                  content: [
                    {
                      type: "fields",
                      fieldInfos: [
                        {
                          fieldName: "active",
                          label: "现存确诊",
                        },
                      ],
                    },
                  ],
                },
              });
              const deathsRatioLayer = new GeoJSONLayer({
                url: this.deathsRatioUrl,
                renderer: deathsRatioRenderer,
                title: "COVID-19死亡率（热力图）",
              });
              var data = [
                confirmedLayer,
                recoveredLayer,
                deathsLayer,
                activeLayer,
                clusterActiveLayer,
                deathsRatioLayer,
              ];
              groupLayer.removeAll();
              data.forEach((item, index) => {
                if (this.showIndex != index) {
                  groupLayer.add(item);
                }
                item.on("layerview-create-error", () => {
                  groupLayer.remove(item);
                });
              });
              groupLayer.add(data[this.showIndex]);
              await view.whenLayerView(data[this.showIndex]);
              await this.sleep(1000);
            };

            var groupLayer = new GroupLayer({
              title: "COVID-19数据可视化",
              visible: true,
              visibilityMode: "exclusive",
            });
            const map = new Map({
              basemap: "dark-gray-vector",
              layers: [groupLayer],
            });

            const view = new MapView({
              container: "distribution",
              map: map,
              center: [114, 38.54],
              zoom: 3,
            });
            var addGrouplayerListener = () => {
              groupLayer.layers.forEach((item) => {
                item.watch("visible", async (visible) => {
                  if (visible) {
                    if (item.title == "COVID-19累计确诊（分布图）") {
                      this.info_title = "累计确诊";
                      this.showIndex = 0;
                    } else if (item.title == "COVID-19累计治愈（分布图）") {
                      this.info_title = "累计治愈";
                      this.showIndex = 1;
                    } else if (item.title == "COVID-19累计死亡（分布图）") {
                      this.info_title = "累计死亡";
                      this.showIndex = 2;
                    } else if (
                      item.title == "COVID-19现存确诊（聚合图）" ||
                      item.title == "COVID-19现存确诊（分布图）"
                    ) {
                      this.info_title = "现存确诊";
                      this.showIndex =
                        item.title == "COVID-19现存确诊（聚合图）" ? 3 : 4;
                    } else if (item.title == "COVID-19死亡率（热力图）") {
                      this.showIndex = 5;
                      this.info_title = "死亡率";
                    }
                    // 返回国家数据，防止数据冲突
                    this.clearTableSelect(0);
                    this.clearTableSelect(1);
                    this.clearTableSelect(2);
                    this.tableIndex = 0;
                    await this.showTableData();
                  }
                });
              });
            };
            view
              .when(
                new LayerList({
                  container: "layerNames",
                  view: view,
                })
              )
              .then(() => {
                // 隐藏播放按钮
                document.getElementsByClassName(
                  "esri-time-slider__animation"
                )[0].style.display = "none";
                addLayers();
                addGrouplayerListener();
              });
            // time slider widget initialization
            const timeSlider = new TimeSlider({
              container: "timeSlider",
              view: view,
              timeVisible: true, // show the time stamps on the timeslider
              loop: true,
              fullTimeExtent: {
                start: new Date(2020, 0, 22),
                end: new Date(2021, 0, 12),
              },
              stops: {
                interval: {
                  value: 1,
                  unit: "days",
                },
              },
              values: [
                new Date(2021, 0, 12), // Initialize the current time for the beginning of the fullTimeExtent.
              ],
              mode: "instant",
              labelFormatFunction: (value, type, element) => {
                const normal = new Intl.DateTimeFormat("en-us");
                switch (type) {
                  case "min":
                  case "max":
                    element.setAttribute(
                      "style",
                      "font-family: 'Orbitron', sans-serif; font-size: 16px; color: white;"
                    );
                    element.innerText = normal.format(value);
                    break;
                  case "extent":
                    element.setAttribute(
                      `style`,
                      `font-family: 'Orbitron', sans-serif; font-size: 16px; color: white;font-weight:bold;`
                    );
                    element.innerText = "日期: " + normal.format(value[0]);
                    break;
                }
              },
            });
            // set vars for play button
            const playButton = document.getElementById("playButton");

            playButton.addEventListener("click", () => {
              if (playButton.classList.contains("toggled")) {
                this.isPlaying = false;
                playButton.classList.remove("toggled");
              } else {
                this.isPlaying = true;
                playButton.classList.add("toggled");
                startAnimation();
              }
            });

            // Starts the animation that cycle through the years
            var startAnimation = async () => {
              if (!this.isPlaying) return;
              timeSlider.next();
              var date = timeSlider.values[0];
              this.date_time =
                date.getFullYear() +
                "-" +
                (date.getMonth() + 1) +
                "-" +
                date.getDate();
              // 返回国家数据，防止数据冲突
              this.clearTableSelect(0);
              this.clearTableSelect(1);
              this.clearTableSelect(2);
              this.tableIndex = 0;
              await this.showTableData();
              // 更新地图
              await addLayers();
              addGrouplayerListener();
              startAnimation();
            };

            timeSlider.watch("values", async (values) => {
              if (this.isPlaying) return;
              var date = values[0];
              this.date_time =
                date.getFullYear() +
                "-" +
                (date.getMonth() + 1) +
                "-" +
                date.getDate();
              // 返回国家数据，防止数据冲突
              this.clearTableSelect(0);
              this.clearTableSelect(1);
              this.clearTableSelect(2);
              this.tableIndex = 0;
              await this.showTableData();
              // 更新地图
              await addLayers();
              addGrouplayerListener();
            });

            var basemapGallery = new BasemapGallery({
              view: view,
              source: [
                Basemap.fromId("topo-vector"),
                Basemap.fromId("hybrid"),
                Basemap.fromId("dark-gray-vector"),
                Basemap.fromId("gray"),
                Basemap.fromId("topo"),
              ],
            });

            view.ui.add(
              new Expand({
                content: basemapGallery,
                view: view,
                expanded: false,
              }),
              "top-right"
            );
            view.ui.add(
              new Expand({
                content: new Legend({
                  view: view,
                  style: {
                    type: "classic",
                    layout: "auto",
                  },
                }),
                view: view,
                expanded: false,
              }),
              "top-right"
            );
            view.ui.add(
              new Expand({
                content: document.getElementById("slider-wrapper"),
                view: view,
                expanded: false,
              }),
              "bottom-left"
            );
          }
        )
        .catch((err) => {
          console.log("地图创建失败" + err);
        });
    },
    tableRowClassName({ row, rowIndex }) {
      row.index = rowIndex;
      return "table-row";
    },
    tableCellClassName({ columnIndex }) {
      if (columnIndex == 0) {
        if (this.info_title == "累计确诊" || this.info_title == "现存确诊") {
          return "table-cell-red";
        } else if (this.info_title == "累计治愈") {
          return "table-cell-green";
        } else if (
          this.info_title == "累计死亡" ||
          this.info_title == "死亡率"
        ) {
          return "table-cell-gray";
        }
      } else return "table-cell";
    },
    tableRowClick(row) {
      if (this.tableIndex == 0) {
        if (
          this.table_active_country_index != -1 &&
          this.table_active_country_index != row.index
        )
          this.clearTableSelect(0);
      } else if (
        this.table_active_province_index != -1 &&
        this.tableIndex == 1
      ) {
        if (this.table_active_province_index != row.index)
          this.clearTableSelect(1);
      } else if (this.table_active_city_index != -1 && this.tableIndex == 2) {
        if (this.table_active_city_index != row.index) this.clearTableSelect(2);
      }
      var dom = document.getElementsByClassName("el-table__body")[
        this.tableIndex
      ];
      var tr = dom.getElementsByTagName("tr")[row.index];
      if (tr.classList[tr.classList.length - 1] === "active") {
        if (this.tableIndex == 0) {
          this.table_active_country = this.table_active_province = this.table_active_city =
            "";
          this.table_active_country_index = -1;
        } else if (this.tableIndex == 1) {
          this.table_active_province = this.table_active_city = "";
          this.table_active_province_index = -1;
        } else if (this.tableIndex == 2) {
          this.table_active_city = "";
          this.table_active_city_index = -1;
        }
        tr.classList.remove("active");
      } else {
        if (this.tableIndex == 0) {
          this.table_active_country = row.combinedKey;
          this.table_active_country_index = row.index;
        } else if (this.tableIndex == 1) {
          this.table_active_province = row.combinedKey;
          this.table_active_province_index = row.index;
        } else if (this.tableIndex == 2) {
          this.table_active_city = row.combinedKey;
          this.table_active_city_index = row.index;
        }
        tr.classList.add("active");
      }
    },
    clearTableSelect(tableIndex) {
      var dom = document.getElementsByClassName("el-table__body")[tableIndex];
      var tr;
      if (tableIndex == 0 && this.table_active_country_index != -1) {
        tr = dom.getElementsByTagName("tr")[this.table_active_country_index];
        this.table_active_country_index = -1;
        this.table_active_country = "";
        if (tr.classList[tr.classList.length - 1] === "active") {
          tr.classList.remove("active");
        }
      } else if (tableIndex == 1 && this.table_active_province_index != -1) {
        tr = dom.getElementsByTagName("tr")[this.table_active_province_index];
        this.table_active_province_index = -1;
        this.table_active_province = "";
        if (tr.classList[tr.classList.length - 1] === "active") {
          tr.classList.remove("active");
        }
      } else if (tableIndex == 2 && this.table_active_city_index != -1) {
        tr = dom.getElementsByTagName("tr")[this.table_active_city_index];
        this.table_active_city_index = -1;
        this.table_active_city = "";
        if (tr.classList[tr.classList.length - 1] === "active") {
          tr.classList.remove("active");
        }
      }
    },
  },
  mounted() {
    this.showDistributionMap();
    this.showTableData();
  },
  components: {
    DateTime,
    SwitchKey,
    IncreaseChart,
    ConfirmedRecoveredChart,
  },
};
</script>