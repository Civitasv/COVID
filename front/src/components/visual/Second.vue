<template>
  <el-container id="sub_container">
    <el-aside class="left">
      <div class="chart">
        <ChinaBarChart
          v-model="province"
          :timestamp="date_timestamp"
        ></ChinaBarChart>
      </div>
      <!-- 时间-->
      <div class="bottom">
        <DateTime :date_time="date_time"></DateTime>
      </div>
    </el-aside>
    <el-main class="map">
      <div class="tags">
        <Tag tag="中国" @click="province = ''"></Tag>
        <Tag :tag="province"></Tag>
      </div>
      <div class="descrip">
        <Description
          :confirmed="confirmed"
          :recovered="recovered"
          :deaths="deaths"
        ></Description>
      </div>
      <div class="layers">
        <!-- 分布图 -->
        <div id="china-distribution"></div>
        <!-- 时间控件-->
        <div id="china-timeSlider"></div>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped>
#sub_container {
  padding-left: 20px;
  padding-right: 20px;
}
#sub_container .left {
  display: flex;
  flex-direction: column;
}
.left .chart {
  flex: 1;
  padding-top: 15px;
}
.left .bottom {
  margin-bottom: 20px;
}
.layers,
#china-distribution {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
}
.map .descrip {
  position: absolute;
  margin-top: 20px;
  margin-left: 10px;
  width: 120px;
  z-index: 99;
}
.map .tags {
  position: absolute;
  margin-top: 10px;
  margin-left: 20px;
  z-index: 99;
}
</style>

<script>
import { loadModules } from "esri-loader";
import { mapState, mapActions } from "vuex";
import { DateStringToTimestamp } from "../../assets/js/DateUtil";
import basemap from "../../assets/basemap/china.json";
const DateTime = () => import("../time/DateTime");
const Description = () => import("../description/Description");
const ChinaBarChart = () => import("../time/ChinaBarChart");
const Tag = () => import("../common/Tag");
export default {
  data() {
    return {
      date_time: "2021-01-12",
      date_timestamp: 1610380800,
      province: "",
      confirmed: 0,
      recovered: 0,
      deaths: 0,
      groupLayer: null,
      view: null,
      tags: { country: "中国", province: "" },
      showIndex: 0,
    };
  },
  watch: {
    date_timestamp() {
      this.setDescription();
    },
    province() {
      this.setDescription();
      if (this.province != "") this.showProvinceLayer();
      else this.showChinaLayer();
    },
  },
  computed: {
    ...mapState({
      chinaProvinceConfirmedVirus: (state) =>
        state.virus.chinaProvinceConfirmedVirus,
      countryConfirmed: (state) => state.virus.countryConfirmed,
      countryRecovered: (state) => state.virus.countryRecovered,
      countryDeaths: (state) => state.virus.countryDeaths,
      provinceConfirmed: (state) => state.virus.provinceConfirmed,
      provinceRecovered: (state) => state.virus.provinceRecovered,
      provinceDeaths: (state) => state.virus.provinceDeaths,
    }),
    confirmedUrl() {
      return "http://localhost/virus/china/confirmed/" + this.date_timestamp;
    },
    provinceConfirmedUrl() {
      return (
        "http://localhost/virus/china/confirmed/" +
        this.province +
        "/" +
        this.date_timestamp
      );
    },
    densityUrl() {
      return "http://localhost/virus/china/density/" + this.date_timestamp;
    },
    provinceDensityUrl() {
      return (
        "http://localhost/virus/china/density/" +
        this.province +
        "/" +
        this.date_timestamp
      );
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
      "getChinaProvinceConfirmedVirusByTimestamp",
    ]),
    async setDescription() {
      if (this.province == "") {
        if (
          !this.countryConfirmed["China"] ||
          !this.countryConfirmed["China"][this.date_timestamp]
        )
          await this.getCountryConfirmedVirusData({
            country: "China",
            timestamp: this.date_timestamp,
          });
        let confirmed = 0;
        this.countryConfirmed["China"][this.date_timestamp].forEach((item) => {
          confirmed += item.val;
        });
        this.confirmed = confirmed;

        if (
          !this.countryRecovered["China"] ||
          !this.countryRecovered["China"][this.date_timestamp]
        )
          await this.getCountryRecoveredVirusData({
            country: "China",
            timestamp: this.date_timestamp,
          });
        let recovered = 0;
        this.countryRecovered["China"][this.date_timestamp].forEach((item) => {
          recovered += item.val;
        });
        this.recovered = recovered;

        if (
          !this.countryDeaths["China"] ||
          !this.countryDeaths["China"][this.date_timestamp]
        )
          await this.getCountryDeathsVirusData({
            country: "China",
            timestamp: this.date_timestamp,
          });
        let deaths = 0;
        this.countryDeaths["China"][this.date_timestamp].forEach((item) => {
          deaths += item.val;
        });
        this.deaths = deaths;
      } else {
        if (
          !this.provinceConfirmed["China"] ||
          !this.provinceConfirmed["China"][this.province] ||
          !this.provinceConfirmed["China"][this.province][this.date_timestamp]
        )
          await this.getProvinceConfirmedVirusData({
            country: "China",
            province: this.province,
            timestamp: this.date_timestamp,
          });
        if (
          !this.provinceRecovered["China"] ||
          !this.provinceRecovered["China"][this.province] ||
          !this.provinceRecovered["China"][this.province][this.date_timestamp]
        )
          await this.getProvinceRecoveredVirusData({
            country: "China",
            province: this.province,
            timestamp: this.date_timestamp,
          });
        if (
          !this.provinceDeaths["China"] ||
          !this.provinceDeaths["China"][this.province] ||
          !this.provinceDeaths["China"][this.province][this.date_timestamp]
        )
          await this.getProvinceDeathsVirusData({
            country: "China",
            province: this.province,
            timestamp: this.date_timestamp,
          });
        let confirmed = 0,
          recovered = 0,
          deaths = 0;
        let arr = this.provinceConfirmed["China"][this.province][
          this.date_timestamp
        ];
        let arr2 = this.provinceRecovered["China"][this.province][
          this.date_timestamp
        ];
        let arr3 = this.provinceDeaths["China"][this.province][
          this.date_timestamp
        ];
        if (!arr || arr.length == 0) {
          if (
            !this.countryConfirmed["China"] ||
            !this.countryConfirmed["China"][this.date_timestamp]
          )
            await this.getCountryConfirmedVirusData({
              country: "China",
              timestamp: this.date_timestamp,
            });
          if (
            !this.countryRecovered["China"] ||
            !this.countryRecovered["China"][this.date_timestamp]
          )
            await this.getCountryRecoveredVirusData({
              country: "China",
              timestamp: this.date_timestamp,
            });
          if (
            !this.countryDeaths["China"] ||
            !this.countryDeaths["China"][this.date_timestamp]
          )
            await this.getCountryDeathsVirusData({
              country: "China",
              timestamp: this.date_timestamp,
            });
          arr = this.countryConfirmed["China"][this.date_timestamp];
          arr.forEach((item) => {
            if (item.combinedKey == this.province) {
              confirmed = item.val;
            }
          });
          arr2 = this.countryRecovered["China"][this.date_timestamp];
          arr2.forEach((item) => {
            if (item.combinedKey == this.province) {
              recovered = item.val;
            }
          });
          arr3 = this.countryDeaths["China"][this.date_timestamp];
          arr3.forEach((item) => {
            if (item.combinedKey == this.province) {
              deaths = item.val;
            }
          });
        } else {
          arr.forEach((item) => {
            confirmed += item.val;
          });
          arr2.forEach((item) => {
            recovered += item.val;
          });
          arr3.forEach((item) => {
            deaths += item.val;
          });
        }
        this.confirmed = confirmed;
        this.recovered = recovered;
        this.deaths = deaths;
      }
    },
    showProvinceLayer() {
      const options = {
        url: "https://js.arcgis.com/4.18/",
        css: "https://js.arcgis.com/4.18/esri/themes/dark/main.css",
      };
      loadModules(
        ["esri/layers/GeoJSONLayer", "esri/renderers/DotDensityRenderer"],
        options
      ).then(([GeoJSONLayer, DotDensityRenderer]) => {
        const provinceConfirmedRenderer = {
          type: "simple", // autocasts as new SimpleRenderer()
          symbol: {
            type: "simple-fill",
            outline: {
              color: "#f0f5f9",
              width: 1,
            },
          },
          visualVariables: [
            {
              type: "color",
              field: "confirmed",
              legendOptions: {
                title: "累计确诊人数",
              },
              stops: [
                { color: "#fae3d9", value: 0 },
                { color: "#ffd0a6", value: 10 },
                { color: "#ffaa7f", value: 100 },
                { color: "#ff704e", value: 500 },
                { color: "#f04040", value: 1000 },
                { color: "#b50a09", value: 10000 },
              ],
            },
          ],
        };
        const provinceDotDensityRenderer = new DotDensityRenderer({
          dotValue: 10,
          outline: null,
          referenceScale: null,
          dotBlendingEnabled: true,
          legendOptions: {
            unit: "cases",
          },
          attributes: [
            {
              field: "active",
              color: "#c23531",
              label: "Active",
            },
            {
              field: "recovered",
              color: "#38ea82",
              label: "Recovered",
            },
            {
              field: "deaths",
              color: "#949fa5",
              label: "Deaths",
            },
          ],
        });
        const provinceConfirmedLayer = new GeoJSONLayer({
          url: this.provinceConfirmedUrl,
          renderer: provinceConfirmedRenderer,
          title: "COVID-19累计确诊（填色图）",
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
        const provinceDotDensityLayer = new GeoJSONLayer({
          url: this.provinceDensityUrl,
          renderer: provinceDotDensityRenderer,
          title: "COVID-19（密度图）",
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
                  {
                    fieldName: "recovered",
                    label: "治愈",
                  },
                  {
                    fieldName: "deaths",
                    label: "死亡",
                  },
                ],
              },
            ],
          },
        });
        this.groupLayer.removeAll();
        if (this.showIndex == 0)
          this.groupLayer.addMany([
            provinceDotDensityLayer,
            provinceConfirmedLayer,
          ]);
        else {
          this.groupLayer.addMany([
            provinceConfirmedLayer,
            provinceDotDensityLayer,
          ]);
        }
        this.addGroupLayerListener();
        provinceConfirmedLayer.queryExtent().then((res) => {
          this.view
            .goTo({
              center: res.extent,
            })
            .catch(function (error) {
              if (error.name != "AbortError") {
                console.error(error);
              }
            });
        });
      });
    },
    showChinaLayer() {
      const options = {
        url: "https://js.arcgis.com/4.18/",
        css: "https://js.arcgis.com/4.18/esri/themes/dark/main.css",
      };
      loadModules(
        ["esri/layers/GeoJSONLayer", "esri/renderers/DotDensityRenderer"],
        options
      ).then(([GeoJSONLayer, DotDensityRenderer]) => {
        // 默认marker
        const confirmedSym = {
          type: "simple-fill",
          outline: {
            width: 0,
          },
        };

        const confirmedRenderer = {
          type: "simple", // autocasts as new SimpleRenderer()
          symbol: confirmedSym,
          visualVariables: [
            {
              type: "color",
              field: "confirmed",
              legendOptions: {
                title: "累计确诊人数",
              },
              stops: [
                { color: "#fae3d9", value: 0 },
                { color: "#ffd0a6", value: 10 },
                { color: "#ffaa7f", value: 100 },
                { color: "#ff704e", value: 500 },
                { color: "#f04040", value: 1000 },
                { color: "#b50a09", value: 10000 },
              ],
            },
          ],
        };
        const dotDensityRenderer = new DotDensityRenderer({
          dotValue: 5,
          outline: null,
          referenceScale: null,
          dotBlendingEnabled: true,
          legendOptions: {
            unit: "cases",
          },
          attributes: [
            {
              field: "active",
              color: "#c23531",
              label: "Active",
            },
            {
              field: "recovered",
              color: "#38ea82",
              label: "Recovered",
            },
            {
              field: "deaths",
              color: "#949fa5",
              label: "Deaths",
            },
          ],
        });
        const confirmedLayer = new GeoJSONLayer({
          url: this.confirmedUrl,
          renderer: confirmedRenderer,
          title: "COVID-19累计确诊（填色图）",
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
        const dotDensityLayer = new GeoJSONLayer({
          url: this.densityUrl,
          renderer: dotDensityRenderer,
          title: "COVID-19（密度图）",
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
                  {
                    fieldName: "recovered",
                    label: "治愈",
                  },
                  {
                    fieldName: "deaths",
                    label: "死亡",
                  },
                ],
              },
            ],
          },
        });
        this.groupLayer.removeAll();
        if (this.showIndex == 0)
          this.groupLayer.addMany([dotDensityLayer, confirmedLayer]);
        else {
          this.groupLayer.addMany([confirmedLayer, dotDensityLayer]);
        }
        this.addGroupLayerListener();
        confirmedLayer.queryExtent().then((res) => {
          this.view
            .goTo({
              center: res.extent,
            })
            .catch(function (error) {
              if (error.name != "AbortError") {
                console.error(error);
              }
            });
        });
      });
    },
    addGroupLayerListener() {
      this.groupLayer.layers.forEach((item) => {
        item.watch("visible", async (visible) => {
          if (visible) {
            if (item.title == "COVID-19累计确诊（填色图）") {
              this.showIndex = 0;
            } else if (item.title == "COVID-19（密度图）") {
              this.showIndex = 1;
            }
          }
        });
        item.on("layerview-create-error", () => {
          this.groupLayer.remove(item);
        });
      });
    },
    showDistributionMap() {
      const options = {
        url: "https://js.arcgis.com/4.18/",
        css: "https://js.arcgis.com/4.18/esri/themes/dark/main.css",
      };
      loadModules(
        [
          "esri/config",
          "esri/Map",
          "esri/views/MapView",
          "esri/layers/GeoJSONLayer",
          "esri/widgets/Legend",
          "esri/widgets/Expand",
          "esri/widgets/TimeSlider",
          "esri/widgets/BasemapGallery",
          "esri/widgets/LayerList",
          "esri/layers/GroupLayer",
          "esri/layers/WebTileLayer",
        ],
        options
      )
        .then(
          ([
            esriConfig,
            Map,
            MapView,
            GeoJSONLayer,
            Legend,
            Expand,
            TimeSlider,
            BasemapGallery,
            LayerList,
            GroupLayer,
          ]) => {
            esriConfig.apiKey =
              "AAPK76568f5a4513446aac6d02353329dde4PazCllumV8HAu66zH3ufxMD4nXSQzUFbq5wn8g42wcIVDCaQNI9PWflyYANc7P_L";

            this.groupLayer = new GroupLayer({
              title: "中国COVID-19数据可视化",
              visible: true,
              visibilityMode: "exclusive",
            });
            const blob = new Blob([JSON.stringify(basemap)], {
              type: "application/json",
            });
            var chinaBaseLayer = new GeoJSONLayer({
              url: URL.createObjectURL(blob),
              title: "中国地图边界",
              renderer: {
                type: "simple", // autocasts as new SimpleRenderer()
                symbol: {
                  type: "simple-line",
                  color: "#f0f5f9",
                  width: 1,
                  style: "solid",
                },
              },
            });
            const map = new Map({
              basemap: {
                portalItem: {
                  // autocasts as new PortalItem()
                  id: "82e5feb147f349edb0564a17800ad6f7",
                },
              },
              layers: [this.groupLayer, chinaBaseLayer],
            });

            this.view = new MapView({
              container: "china-distribution",
              map: map,
              center: [114, 38.54],
              zoom: 3,
            });

            this.view.when().then(() => {
              // 隐藏播放按钮
              document.getElementsByClassName(
                "esri-time-slider__animation"
              )[0].style.display = "none";
              this.showChinaLayer();
            });
            // time slider widget initialization
            const timeSlider = new TimeSlider({
              container: "china-timeSlider",
              view: this.view,
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

            // 定义函数
            timeSlider.watch("values", async (values) => {
              var date = values[0];
              this.date_time =
                date.getFullYear() +
                "-" +
                (date.getMonth() + 1) +
                "-" +
                date.getDate();
              this.date_timestamp = DateStringToTimestamp(this.date_time);
              if (this.province == "") {
                this.showChinaLayer();
              } else {
                this.showProvinceLayer();
              }
            });
            var basemapGallery = new BasemapGallery({
              view: this.view,
              source: [
                {
                  portalItem: {
                    // autocasts as new PortalItem()
                    id: "82e5feb147f349edb0564a17800ad6f7",
                  },
                },
                {
                  portalItem: {
                    // autocasts as new PortalItem()
                    id: "98c8be317cf042a0baa47be9e4cca484",
                  },
                },
                {
                  portalItem: {
                    // autocasts as new PortalItem()
                    id: "5f913b7ecc3c474e872f532cd7a643a5",
                  },
                },
                {
                  portalItem: {
                    // autocasts as new PortalItem()
                    id: "542752ee082e4c019ecc30748b490290",
                  },
                },
              ],
            });

            this.view.ui.add(
              new Expand({
                content: basemapGallery,
                view: this.view,
                expanded: false,
              }),
              "top-right"
            );
            this.view.ui.add(
              new Expand({
                content: new Legend({
                  view: this.view,
                  style: {
                    type: "classic",
                    layout: "auto",
                  },
                }),
                view: this.view,
                expanded: false,
              }),
              "top-right"
            );
            this.view.ui.add(
              new Expand({
                content: timeSlider.container,
                view: this.view,
                expanded: false,
              }),
              "bottom-left"
            );
            this.view.ui.add(
              new Expand({
                content: new LayerList({
                  view: this.view,
                }),
                view: this.view,
                expanded: false,
              }),
              "bottom-right"
            );
            this.view.ui.remove("zoom"); //清除放大缩小按钮
          }
        )
        .catch((err) => {
          console.log("地图创建失败" + err);
        });
    },
  },
  mounted() {
    this.showDistributionMap();
    this.setDescription();
  },
  components: {
    DateTime,
    Description,
    ChinaBarChart,
    Tag,
  },
};
</script>