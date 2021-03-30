<template>
  <div class="manage">
    <el-container style="height: 100%">
      <div class="nav">
        <el-aside style="height: 100%; width: 210px">
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            style="height: 100%; border: none"
            :background-color="menuColor"
            text-color="#fff"
            active-text-color="#ffd04b"
            @select="toggleMenu"
          >
            <el-menu-item index="1">
              <i class="el-icon-location"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-s-data"></i>
                <span>数据观星</span>
              </template>
              <el-menu-item index="2-1">
                <i class="el-icon-location"></i>
                <span slot="title">中国</span>
              </el-menu-item>
              <el-menu-item index="2-2">
                <i class="el-icon-location"></i>
                <span slot="title">世界</span>
              </el-menu-item>
            </el-submenu>
          </el-menu>
        </el-aside>
      </div>

      <el-container style="height: 100%">
        <el-header style="height: 10%; background: #343332">
          <div class="time">
            <i class="el-icon-time"></i>
            <label>当前数据时间：</label>
            <el-date-picker
              v-model="datatime"
              value-format="yyyy-MM-dd"
              align="right"
              type="date"
              placeholder="选择数据日期"
              :picker-options="pickerOptions"
              @change="changeTime"
              style="background: #d7d7d7"
              :clearable="false"
              :editable="false"
            ></el-date-picker>
          </div>
          <div class="add-feature">
            <el-tooltip
              class="item"
              effect="light"
              content="添加点"
              placement="bottom"
            >
              <el-button
                type="info"
                icon="el-icon-add-location"
                circle
                @click="addAction = !addAction"
              ></el-button>
            </el-tooltip>
          </div>
          <div class="search">
            <el-input
              placeholder="地点模糊查询"
              prefix-icon="el-icon-search"
              v-model="searchContent"
              @change="search"
              style="background: #d7d7d7"
            ></el-input>
          </div>
          <div class="user">
            <el-dropdown @command="handleCommand">
              <el-avatar
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                class="el-dropdown-link"
              ></el-avatar>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="quit" icon="el-icon-circle-close"
                  >退出登录</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <el-main style="padding: 0">
          <div id="map" :class="['map', addAction ? 'choose' : '']"></div>
          <div id="popup" class="ol-popup">
            <a
              id="popup-closer"
              class="ol-popup-closer"
              @click="popupClose"
            ></a>
            <div id="popup-content">
              <div class="virus-time">
                <div class="virus-time-title">
                  <i class="el-icon-time"></i>
                  <label>时间</label>
                </div>
                <div class="virus-time-content">
                  <span>
                    <el-date-picker
                      v-model="selectVirus.time"
                      value-format="yyyy-MM-dd"
                      align="right"
                      type="date"
                      placeholder="时间"
                      style="background: #d7d7d7"
                      :readonly="readonly"
                      size="mini"
                      :clearable="false"
                      :editable="false"
                    ></el-date-picker>
                  </span>
                </div>
              </div>
              <div class="content">
                <div class="title"></div>
                <div class="description"></div>
                <div class="address"></div>
                <div class="image"></div>
              </div>

              <div class="virus-time">
                <div class="virus-address-title">
                  <i class="el-icon-location"></i>
                  <label>地点</label>
                </div>
                <div class="virus-address-content">
                  <el-row>
                    <el-col :span="6" :offset="1">
                      <div class="country">
                        <el-tooltip
                          class="item"
                          effect="dark"
                          content="国家"
                          placement="left"
                        >
                          <el-input
                            v-model="selectVirus.country"
                            placeholder="国家"
                            :readonly="readonly"
                            size="mini"
                            style="background: #d7d7d7"
                          ></el-input>
                        </el-tooltip>
                      </div>
                    </el-col>
                    <el-col :span="6" :offset="2">
                      <div class="province">
                        <el-tooltip
                          class="item"
                          effect="dark"
                          content="省份"
                          placement="bottom"
                        >
                          <el-input
                            v-model="selectVirus.province"
                            placeholder="省份"
                            :readonly="readonly"
                            size="mini"
                            style="background: #d7d7d7"
                          ></el-input>
                        </el-tooltip>
                      </div>
                    </el-col>
                    <el-col :span="6" :offset="2">
                      <div class="city">
                        <el-tooltip
                          class="item"
                          effect="dark"
                          content="城市"
                          placement="right"
                        >
                          <el-input
                            v-model="selectVirus.city"
                            placeholder="城市"
                            :readonly="readonly"
                            size="mini"
                            style="background: #d7d7d7"
                          ></el-input>
                        </el-tooltip>
                      </div>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <div class="virus-info">
                <div class="virus-info-title">
                  <i class="el-icon-info"></i>
                  <label>疫情信息</label>
                </div>
                <div class="virus-info-content">
                  <div class="number">
                    <el-row>
                      <el-col :span="6" :offset="1">
                        <div class="confirmed">
                          <el-tooltip
                            class="item"
                            effect="dark"
                            content="确诊"
                            placement="left"
                          >
                            <el-input
                              v-model="selectVirus.confirmed"
                              placeholder="确诊"
                              :readonly="readonly"
                              size="mini"
                              style="background: #d7d7d7"
                            ></el-input>
                          </el-tooltip>
                        </div>
                      </el-col>
                      <el-col :span="6" :offset="2">
                        <div class="recovery">
                          <el-tooltip
                            class="item"
                            effect="dark"
                            content="治愈"
                            placement="bottom"
                          >
                            <el-input
                              v-model="selectVirus.recovery"
                              placeholder="治愈"
                              :readonly="readonly"
                              size="mini"
                              style="background: #d7d7d7"
                            ></el-input>
                          </el-tooltip>
                        </div>
                      </el-col>
                      <el-col :span="6" :offset="2">
                        <div class="deaths">
                          <el-tooltip
                            class="item"
                            effect="dark"
                            content="死亡"
                            placement="right"
                          >
                            <el-input
                              v-model="selectVirus.deaths"
                              placeholder="死亡"
                              :readonly="readonly"
                              size="mini"
                              style="background: #d7d7d7"
                            ></el-input>
                          </el-tooltip>
                        </div>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </div>
              <div class="virus-description">
                <div class="virus-description-title">
                  <i class="el-icon-postcard"></i>
                  <label>描述</label>
                </div>
                <div class="virus-description-content">
                  <el-input
                    v-model="selectVirus.description"
                    placeholder="描述"
                    :readonly="readonly"
                    size="mini"
                    style="background: #d7d7d7"
                  ></el-input>
                </div>
              </div>
              <div class="modify">
                <el-button
                  type="primary"
                  icon="el-icon-edit"
                  circle
                  @click="editVirus"
                  v-show="!addMode"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  circle
                  @click="deleteVirus"
                  v-show="!addMode"
                ></el-button>
                <el-button
                  size="mini"
                  round
                  v-show="!readonly && !addMode"
                  @click="updateVirus"
                  >提交</el-button
                >
                <el-button size="mini" round v-show="addMode" @click="addVirus"
                  >添加</el-button
                >
              </div>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import virus from "../network/common/api/virus";
import "ol/ol.css";
import Map from "ol/Map";
import View from "ol/View";
import Overlay from "ol/Overlay";
import TileLayer from "ol/layer/Tile";
import XYZSource from "ol/source/XYZ";
import Icon from "ol/style/Icon";
import Style from "ol/style/Style";
import Feature from "ol/Feature";
import Point from "ol/geom/Point";
import VectorSource from "ol/source/Vector";
import VectorLayer from "ol/layer/Vector";
import GeoJSON from "ol/format/GeoJSON";
import { fromLonLat, toLonLat } from "ol/proj";
import { defaults as defaultControls, OverviewMap } from "ol/control";
import china from "../assets/basemap/china.json";
import Fill from "ol/style/Fill";
import Stroke from "ol/style/Stroke";
import icon from "@/assets/images/icon.png";
import icon2 from "@/assets/images/icon2.png";
import { DateStringToTimestamp } from "../assets/js/DateUtil";

export default {
  data() {
    return {
      map: null,
      iconStyle: null,
      iconStyle2: null,
      vectorSource: null,
      vectorLayer: null,
      overlay: null,
      menuColor: "#2a2d2e",
      showIndex: "1",
      datatime: "2020-01-11",
      imageUrl: "https://w.wallhaven.cc/full/ey/wallhaven-eymzjk.jpg",
      pickerOptions: {
        disabledDate(time) {
          var date1 = new Date(2020, 0, 22);
          var date2 = new Date(2021, 0, 12);
          return (
            time.getTime() > date2.getTime() || time.getTime() < date1.getTime()
          );
        },
      },
      searchContent: "",
      ptfeatures: [],
      selectVirus: {
        id: 1,
        time: "2020-01-22",
        lat: 0,
        lng: 0,
        date_timestamp: 0,
        country: "",
        province: "",
        city: "",
        confirmed: 0,
        recovery: 0,
        deaths: 0,
        description: "数据来源：JHU CSSE COVID-19 Data",
      },
      readonly: true,
      editMode: false,
      addFeature: null,
      addAction: false, // 控制添加点行为
      addMode: false, // 控制按钮显示
    };
  },
  methods: {
    addVirus() {
      this.$confirm("添加数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.selectVirus.date_timestamp = DateStringToTimestamp(
            this.selectVirus.time
          );
          virus.addVirus(this.selectVirus).then((res) => {
            console.log(res.data);
            alert("添加成功!");
            // 重新加载数据
            this.readonly = true;
            this.addMode = false;
            if (this.addFeature) {
              this.vectorSource.removeFeature(this.addFeature);
              this.addFeature = null;
            }
            this.search();
          });
        })
        .catch(() => {
          if (this.addFeature) {
            this.vectorSource.removeFeature(this.addFeature);
            this.addFeature = null;
          }
          alert("取消添加!");
        });
    },
    editVirus() {
      this.readonly = false;
      this.editMode = true;
    },
    updateVirus() {
      this.$confirm("更新数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          virus.updateVirus(this.selectVirus).then((res) => {
            console.log(res.data);
            //this.overlay.setPosition(undefined);
            alert("更新成功!");
            // 重新加载数据
            this.readonly = true;
            this.search();
          });
        })
        .catch(() => {
          alert("取消更新!");
        });
    },
    deleteVirus() {
      this.$confirm("此操作将永久删除该数据点, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          virus.deleteVirus(this.selectVirus.id).then((res) => {
            console.log(res.data);
            this.overlay.setPosition(undefined);
            alert("删除成功!!");
            // 重新加载数据
            this.readonly = true;
            this.search();
          });
        })
        .catch(() => {
          alert("取消删除!");
        });
    },
    popupClose() {
      if (this.addFeature) {
        this.vectorSource.removeFeature(this.addFeature);
        this.addFeature = null;
      }
      this.overlay.setPosition(undefined);
      this.readonly = true;
      this.editMode = false;
      return false;
    },
    styleFunction(feature) {
      var styles = {
        MultiPolygon: new Style({
          stroke: new Stroke({
            color: "#cc3e3e",
            width: 1.2,
          }),
          fill: new Fill({
            color: "rgba(255, 255, 255, 0.1)",
          }),
        }),
        Polygon: new Style({
          stroke: new Stroke({
            color: "#cc3e3e",
            width: 1.2,
          }),
          fill: new Fill({
            color: "rgba(255, 255, 255, 0.1)",
          }),
        }),
      };
      return styles[feature.getGeometry().getType()];
    },
    init() {
      var container = document.getElementById("popup");
      // overlay
      this.overlay = new Overlay({
        element: container,
        positioning: "bottom-center",
        autoPan: true,
        autoPanAnimation: {
          duration: 250,
        },
        stopEvent: false,
        offset: [0, -30],
      });

      /**
       * Add a click handler to hide the popup.
       * @return {boolean} Don't follow the href.
       */
      var overviewMapControl = new OverviewMap({
        layers: [
          new TileLayer({
            source: new XYZSource({
              url:
                "https://api.mapbox.com/styles/v1/mapbox/dark-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiY2l2aXRhc3YiLCJhIjoiY2s3YXBvdDU1MTZpdDNlcDVhb3FrbjdtaiJ9.kLk_w4wIjIQ6dunGULViqw",
            }),
            /*
            source: new TileJSON({
              url: "https://a.tiles.mapbox.com/v3/aj.1x1-degrees.json"
            })
            */
          }),
          new VectorLayer({
            source: new VectorSource({
              features: new GeoJSON().readFeatures(china, {
                featureProjection: "EPSG:3857",
              }),
            }),
            style: this.styleFunction,
          }),
        ],
      });
      this.map = new Map({
        controls: defaultControls().extend([overviewMapControl]),
        layers: [
          new TileLayer({
            source: new XYZSource({
              url:
                "https://api.mapbox.com/styles/v1/mapbox/dark-v9/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiY2l2aXRhc3YiLCJhIjoiY2s3YXBvdDU1MTZpdDNlcDVhb3FrbjdtaiJ9.kLk_w4wIjIQ6dunGULViqw",
            }),
            /*
            source: new TileJSON({
              url: "https://a.tiles.mapbox.com/v3/aj.1x1-degrees.json"
            })
            */
          }),
          new VectorLayer({
            source: new VectorSource({
              features: new GeoJSON().readFeatures(china, {
                featureProjection: "EPSG:3857",
              }),
            }),
            style: this.styleFunction,
          }),
        ],
        overlays: [this.overlay],
        target: "map",
        view: new View({
          center: [12723812.23169663, 3580328.695683322],
          zoom: 4,
        }),
      });
      this.iconStyle2 = new Style({
        image: new Icon({
          anchor: [0.5, 400],
          anchorXUnits: "fraction",
          anchorYUnits: "pixels",
          opacity: 0.75,
          scale: 0.1,
          src: icon2,
        }),
      });
      this.iconStyle = new Style({
        image: new Icon({
          anchor: [0.5, 30],
          anchorXUnits: "fraction",
          anchorYUnits: "pixels",
          opacity: 0.75,
          src: icon,
        }),
      });
      // display popup on click
      /*
      var selectClick = new Select({
        condition: click
      });
      */
      /*
      this.map.addInteraction(selectClick);
      // 被选中时触发该selectClick
      selectClick.on("select", e => {
        var _features = e.target.getFeatures().getArray();
        if (_features.length > 0) {
          var feature = _features[0];
          if (feature) {
            var coordinates = feature.getGeometry().getCoordinates();
            var attr = feature.getProperties();
            content.innerHTML = "<p>You clicked here:</p><code>ss</code>";
            if (attr.country) this.overlay.setPosition(coordinates);
          }
        }
      });
      */
      // display popup on click
      this.map.on("click", (evt) => {
        if (this.editMode) return;
        if (this.addAction) {
          // 在第二次添加时，删除第一次
          if (this.addFeature) {
            this.vectorSource.removeFeature(this.addFeature);
            this.addFeature = null;
          }
          var coordinate = evt.coordinate;
          this.addFeature = new Feature({
            geometry: new Point(coordinate),
          });
          this.addFeature.setStyle(this.iconStyle2);
          this.vectorSource.addFeature(this.addFeature);
          this.selectVirus.time = "2020-01-11";
          this.selectVirus.lat = toLonLat(coordinate)[1];
          this.selectVirus.lng = toLonLat(coordinate)[0];
          this.selectVirus.country = "";
          this.selectVirus.province = "";
          this.selectVirus.city = "";
          this.selectVirus.confirmed = 0;
          this.selectVirus.recovery = 0;
          this.selectVirus.deaths = 0;
          this.selectVirus.description = "数据来源：JHU CSSE COVID-19 Data";
          this.readonly = false;
          this.addMode = true;

          this.overlay.setPosition(coordinate);
          this.addAction = false;

          return;
        }
        var feature = this.map.forEachFeatureAtPixel(
          evt.pixel,
          function (feature) {
            return feature;
          }
        );
        if (feature) {
          this.readonly = true;
          this.addMode = false;
          var coordinates = feature.getGeometry().getCoordinates();
          var attr = feature.getProperties();
          if ("country" in attr) {
            this.selectVirus.time = this.datatime;
            this.selectVirus.date_timestamp = attr.date_timestamp;
            this.selectVirus.country = attr.country;
            this.selectVirus.province = attr.province;
            this.selectVirus.city = attr.city;
            this.selectVirus.confirmed = attr.new_confirmed;
            this.selectVirus.recovery = attr.new_recovered;
            this.selectVirus.deaths = attr.new_deaths;
            if (attr.description)
              this.selectVirus.description = attr.description;
            this.selectVirus.id = attr.id;
            this.overlay.setPosition(coordinates);
          }
        }
      });
      this.map.on("pointermove", (e) => {
        if (e.dragging) {
          if (this.addFeature) {
            this.vectorSource.removeFeature(this.addFeature);
            this.addFeature = null;
          }
          this.readonly = true;
          this.editMode = false;

          this.overlay.setPosition(undefined);
          return;
        }
        if (!this.addAction) {
          var pixel = this.map.getEventPixel(e.originalEvent);
          var hit = this.map.hasFeatureAtPixel(pixel);
          this.map.getTargetElement().style.cursor = hit ? "pointer" : "";
        }
      });
    },
    toggleMenu(index) {
      this.showIndex = index;
    },
    loadData() {
      // 发送请求获得数据
      virus.getVirusByTime(this.datatime).then((res) => {
        if (this.vectorLayer) {
          this.map.removeLayer(this.vectorLayer);
          this.vectorSource.clear();
          this.ptfeatures.splice(0, this.ptfeatures.length);
        }
        var _this = this;
        // 绘制
        res.data.map(function (item) {
          var feature = new Feature({
            geometry: new Point(fromLonLat([item.lng, item.lat])),
            country: item.country,
            province: item.province,
            city: item.city,
            new_confirmed: item.new_confirmed,
            new_recovered: item.new_recovered,
            new_deaths: item.new_deaths,
            description: item.description,
            date_timestamp: item.date_timestamp,
            id: item.id,
          });
          feature.setStyle(_this.iconStyle);
          _this.ptfeatures.push(feature);
        });
        this.vectorSource = new VectorSource({
          features: this.ptfeatures,
        });
        this.vectorLayer = new VectorLayer({
          source: this.vectorSource,
        });

        this.map.addLayer(this.vectorLayer);
      });
    },
    changeTime() {
      if (!this.datatime) return;
      this.overlay.setPosition(undefined);
      this.search();
    },
    search() {
      this.overlay.setPosition(undefined);

      if (this.searchContent == "") {
        this.loadData();
        return;
      }
      virus
        .getVirusByAddressAndTime(this.datatime, this.searchContent)
        .then((res) => {
          if (this.vectorLayer) {
            this.map.removeLayer(this.vectorLayer);
            this.vectorSource.clear();
            this.ptfeatures.splice(0, this.ptfeatures.length);
          }
          var _this = this;
          // 绘制
          res.data.map(function (item) {
            var feature = new Feature({
              geometry: new Point(fromLonLat([item.lng, item.lat])),
              country: item.country,
              province: item.province,
              city: item.city,
              new_confirmed: item.new_confirmed,
              new_recovered: item.new_recovered,
              new_deaths: item.new_deaths,
              description: item.description,
              date_timestamp: item.date_timestamp,
              id: item.id,
            });
            feature.setStyle(_this.iconStyle);
            _this.ptfeatures.push(feature);
          });
          this.vectorSource = new VectorSource({
            features: this.ptfeatures,
          });
          this.vectorLayer = new VectorLayer({
            source: this.vectorSource,
          });

          this.map.addLayer(this.vectorLayer);
        });
    },
    handleCommand(command) {
      if (command == "quit") {
        window.localStorage.removeItem("JWT_TOKEN");
        this.$router.push({
          path: "login",
        });
      }
    },
  },
  mounted() {
    this.init();
    this.search();
  },
  computed: {},
  components: {},
};
</script>

<style scoped>
.time,
.search,
.add-feature {
  color: whitesmoke;
  margin-top: 15px;
  display: inline-block;
}
.country,
.province,
.city {
  display: inline-block;
}
.user {
  margin-top: 15px;
  float: right;
}
.search {
  position: absolute;
  right: 80px;
}
.add-feature {
  position: absolute;
  right: 500px;
}
.map {
  width: 100%;
  height: 100%;
}
.ol-popup {
  position: absolute;
  background-color: white;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
  padding: 15px;
  border: 1px solid #cccccc;
  bottom: 12px;
  left: -50px;
  min-width: 300px;
}
.ol-popup:after,
.ol-popup:before {
  top: 100%;
  border: solid transparent;
  content: " ";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
}
.ol-popup:after {
  border-top-color: white;
  border-width: 10px;
  left: 48px;
  margin-left: -10px;
}
.ol-popup:before {
  border-top-color: #cccccc;
  border-width: 11px;
  left: 48px;
  margin-left: -11px;
}
.ol-popup-closer {
  text-decoration: none;
  position: absolute;
  top: 2px;
  right: 8px;
}
.ol-popup-closer:after {
  content: "✖";
}
.virus-time-title,
.virus-address-title,
.virus-info-title,
.virus-description-title,
.virus-image-description-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
  margin-top: 5px;
}

.choose {
  cursor: crosshair;
}

.inputfile {
  width: 0.1px;
  height: 0.1px;
  opacity: 0;
  overflow: hidden;
  position: absolute;
  z-index: -1;
}

.inputfile + label {
  font-size: 15px;
  font-weight: 700;
  color: white;
  background-color: black;
  display: inline-block;
  padding: 5px;
  margin-left: 10px;
}
.inputfile:focus + label,
.inputfile + label:hover {
  background-color: #bc2c2c;
  cursor: pointer;
}
.inputfile:focus + label {
  outline: 1px dotted #000;
  outline: -webkit-focus-ring-color auto 5px;
}
.modify {
  margin-top: 5px;
}
</style>