<template>
  <el-container id="container">
    <el-header>
      <div class="header">
        <div class="avatar">
          <el-avatar
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
          />
        </div>
        <div class="title">
          <span class="content">新冠疫情数据可视化与管理平台</span>
        </div>
      </div>
    </el-header>
    <el-container id="sub_container">
      <el-aside class="left">
        <div class="top">
          <!-- 病例信息 -->
          <div class="info">
            <div class="title">
              {{ info_title }}
            </div>
            <div class="number">
              {{ number }}
            </div>
          </div>
          <div class="virus_table">
            <div class="title">{{ table_title }}</div>
            <div class="table">
              <el-table
                :data="tableData"
                style="width: 100%; background-color: #222222"
                :show-header="false"
                :row-class-name="tableRowClassName"
                :cell-class-name="tableCellClassName"
                :highlight-current-row="true"
              >
                <el-table-column :prop="table_attr" label="值">
                </el-table-column>
                <el-table-column prop="combinedKey" label="地点">
                </el-table-column>
              </el-table>
            </div>
            <div class="switch">
              <SwitchKey :contents="table_switch_data"></SwitchKey>
            </div>
          </div>
        </div>
        <!-- 时间-->
        <div class="bottom">
          <DateTime :date_time="date_time"></DateTime>
        </div>
      </el-aside>
      <el-main class="map">
        <!-- 分布图 -->
        <div id="distribution"></div>
      </el-main>
      <el-aside class="right"><div class="aside">aside</div></el-aside>
    </el-container>
  </el-container>
</template>

<style scoped>
#container {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
  background: black;
}
#sub_container {
  padding-left: 20px;
  padding-right: 20px;
}
#sub_container .left,
#sub_container .right {
  min-height: 100%;
  display: flex;
  flex-direction: column;
  flex: 1;
}
#sub_container .map {
  padding: 0px 10px 10px 10px;
  flex: 3;
}
.header {
  border: 1px solid #363636;
  background-color: #222222;
  display: flex;
  align-items: center;
}
.header .avatar {
  width: min-content;
  padding: 5px 0px 0px 10px;
}
.header .title {
  margin-left: 10px;
}
.header .title .content {
  color: white;
  font-size: 20px;
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
.info .number {
  color: rgb(230, 0, 0);
  stroke-width: 2;
  font-size: 30px;
  line-height: normal;
  font-weight: bold;
  margin: 0 auto;
}
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
  border: 1px solid #363636;
  background-color: #222222;
  margin-top: 10px;
  margin-bottom: 10px;
  padding: 20px 0px 0px 0px;
  display: flex;
  flex-direction: column;
}
.virus_table .title {
  color: white;
  font-size: 20px;
}
.virus_table .table {
  flex: 1;
}
</style>

<script>
import { loadModules } from "esri-loader";
const DateTime = () => import("../components/time/DateTime");
const SwitchKey = () => import("../components/switch/SwitchKey");

export default {
  data() {
    return {
      visualProperty: "confirmed",
      info_title: "累积确诊（全球）",
      number: 99999,
      date_time: "2021-01-12",
      table_title: "数据一览",
      // 表格展示数据
      table_attr: "confirmed",
      tableData: [
        {
          combinedKey: "东京",
          confirmed: 1,
        },
        {
          combinedKey: "东京",
          confirmed: 1,
        },
      ],
      table_switch_data: ["东京", "中国", "美国"],
    };
  },
  computed: {},
  methods: {
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
        ],
        options
      )
        .then(([Map, MapView, GeoJSONLayer, Legend, Expand]) => {
          const url = "http://localhost/virus/latest";
          // 默认marker
          const defaultSym = {
            type: "simple-marker", // autocasts as new SimpleMarkerSymbol()
            style: "circle",
            color: [255, 0, 0, 0.5],
            outline: {
              color: [255, 0, 0, 0.5],
              width: 1,
            },
          };
          const renderer = {
            type: "simple", // autocasts as new SimpleRenderer()
            symbol: defaultSym,
            visualVariables: [
              {
                type: "size",
                field: this.visualProperty,
                legendOptions: {
                  title: "累积确诊人数",
                },
                stops: [
                  { value: 1000, size: 1, label: "<1000" },
                  {
                    value: 3000,
                    size: 6,
                    label: "<3000",
                  },
                  {
                    value: 20000,
                    size: 8,
                    label: "<20000",
                  },
                  {
                    value: 50000,
                    size: 10,
                    label: "<50000",
                  },
                  {
                    value: 100000,
                    size: 14,
                    label: "<100000",
                  },
                  {
                    value: 250000,
                    size: 18,
                    label: "<250000",
                  },
                  {
                    value: 400000,
                    size: 24,
                    label: "<400000",
                  },
                  {
                    value: 500000,
                    size: 25,
                    label: "<500000",
                  },
                  {
                    value: 1000000,
                    size: 26,
                    label: "<1000000",
                  },
                  {
                    value: 5000000,
                    size: 28,
                    label: "<5000000",
                  },
                ],
              },
            ],
          };
          const casesLayer = new GeoJSONLayer({
            url: url,
            renderer: renderer,
            title: "全球确诊人数",
            popupTemplate: {
              // autocasts as new PopupTemplate()
              title: "<font color='#FFFFFF'>{combined_key}",
              content: [
                {
                  type: "fields",
                  fieldInfos: [
                    {
                      fieldName: "confirmed",
                      label: "累积确诊",
                    },
                    {
                      fieldName: "recovered",
                      label: "累积治愈",
                    },
                    { fieldName: "deaths", label: "累积死亡" },
                  ],
                },
              ],
            },
          });

          const map = new Map({
            basemap: "dark-gray-vector",
            layers: [casesLayer],
          });

          const view = new MapView({
            container: "distribution",
            map: map,
            center: [114, 38.54],
            zoom: 3,
          });
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
              expanded: true,
            }),
            "top-right"
          );
        })
        .catch((err) => {
          console.log("地图创建失败" + err);
        });
    },
    tableRowClassName() {
      return "table-row";
    },
    tableCellClassName({ columnIndex }) {
      if (columnIndex == 0) {
        return "table-cell-red";
      } else return "table-cell ";
    },
  },
  mounted() {
    this.showDistributionMap();
  },
  components: {
    DateTime,
    SwitchKey,
  },
};
</script>