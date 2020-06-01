<template>
  <div style="height:100%;width:100%;">
    <div class="descrip">
      <div class="title">
        <span>新型冠状病毒疫情</span>
      </div>
      <div class="box">
        <div class="redbox">
          <p class="text">
            <span>确诊</span>
          </p>
          <p class="diagnosis">
            <span>{{diagnosis}}</span>
          </p>
        </div>
        <div class="greenbox">
          <p class="text">
            <span>治愈</span>
          </p>
          <p class="recovery">
            <span>{{recovery}}</span>
          </p>
        </div>
        <div class="graybox">
          <p class="text">
            <span>死亡</span>
          </p>
          <p class="death">
            <span>{{death}}</span>
          </p>
        </div>
      </div>
    </div>

    <div ref="basicMapbox" :style="mapSize"></div>
  </div>
</template>

<style>
</style>

<script>
import mapboxgl from "mapbox-gl";
import MapboxLanguage from "@mapbox/mapbox-gl-language";
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      map: null,
      heatID: "virus-heat",
      clusterID: "cluster-point",
      clusterCountID: "cluster-count",
      sourceID: "virus-data",
      visualData: "new_diagnosis",
      diagnosis: 0,
      recovery: 0,
      death: 0
    };
  },
  mounted() {
    this.init();
    this.loadData();
  },
  methods: {
    init() {
      mapboxgl.accessToken =
        "pk.eyJ1IjoiY2l2aXRhc3YiLCJhIjoiY2s3YXBvdDU1MTZpdDNlcDVhb3FrbjdtaiJ9.kLk_w4wIjIQ6dunGULViqw";
      this.map = new mapboxgl.Map({
        container: this.$refs.basicMapbox,
        style: "mapbox://styles/mapbox/light-v9",
        center: [114, 38.54],
        zoom: 3
      });
      mapboxgl.setRTLTextPlugin(
        "https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-rtl-text/v0.1.0/mapbox-gl-rtl-text.js"
      );
      this.map.addControl(
        new MapboxLanguage({
          defaultLanguage: "zh"
        })
      );
      // 地图导航
      //var nav = new mapboxgl.NavigationControl();
      //this.map.addControl(nav, "top-left");
      // 比例尺
      /*var scale = new mapboxgl.ScaleControl({
        maxWidth: 80,
        unit: "imperial"
      });
      this.map.addControl(scale);
      scale.setUnit("metric");
      // 全图
      this.map.addControl(new mapboxgl.FullscreenControl());
      // 定位
      this.map.addControl(
        new mapboxgl.GeolocateControl({
          positionOptions: {
            enableHighAccuracy: true
          },
          trackUserLocation: true
        })
      );
      */
      // console.log(map)
    },
    ...mapActions("virus", ["getAllVirus"]),
    async loadData() {
      // 发送请求获得数据
      await this.getAllVirus();
      this.heatMap();
      this.calData();
    },
    heatMap() {
      this.map.addSource(this.sourceID, {
        type: "geojson",
        data: this.geoJSON
      });

      // 添加热力图图层
      this.map.addLayer(
        {
          id: this.heatID,
          type: "heatmap",
          source: this.sourceID,
          maxzoom: 7,
          paint: {
            // 使用new_diagnosis属性
            "heatmap-weight": [
              "interpolate",
              ["linear"],
              ["get", this.visualData],
              0,
              0,
              1000,
              1
            ],
            "heatmap-intensity": [
              "interpolate",
              ["linear"],
              ["zoom"],
              0,
              3,
              9,
              5
            ],
            // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
            // Begin color ramp at 0-stop with a 0-transparancy color
            // to create a blur-like effect.
            "heatmap-color": [
              "interpolate",
              ["linear"],
              ["heatmap-density"],
              0,
              "rgba(0, 0, 0, 0)",
              0.1,
              "#927903",
              0.15,
              "#ffd403",
              1,
              "red"
            ],
            // Adjust the heatmap radius by zoom level
            "heatmap-radius": [
              "interpolate",
              ["linear"],
              ["zoom"],
              0,
              2,
              1,
              4,
              2,
              8,
              3,
              16,
              4,
              32,
              5,
              64,
              6,
              128,
              7,
              256,
              8,
              512,
              9,
              1024
            ],
            // Transition from heatmap to circle layer by zoom level
            "heatmap-opacity": [
              "interpolate",
              ["linear"],
              ["zoom"],
              5,
              0.95,
              6,
              0
            ]
          }
        },
        "waterway-label"
      );
      /*
        // 添加圆圈图层
        this.map.addLayer({
          id: this.clusterID,
          type: "circle",
          source: this.sourceID,
          filter: ["has", this.visualData],
          minzoom: 5,
          paint: {
            "circle-radius": [
              "step",
              ["get", this.visualData],
              20,
              300,
              30,
              1000,
              40
            ],
            "circle-color": [
              "step",
              ["get", this.visualData],
              "#51bbd6",
              20,
              "#f1f075",
              50,
              "#f28cb1"
            ],
            "circle-opacity": [
              "interpolate",
              ["linear"],
              ["zoom"],
              5,
              0,
              6,
              0.6
            ]
          }
        });
        // 添加计数
        this.map.addLayer({
          id: this.clusterCountID,
          type: "symbol",
          source: this.sourceID,
          minzoom: 5,
          filter: ["has", this.visualData],
          layout: {
            "text-field": "{" + this.visualData + "}",
            "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
            "text-size": 15
          }
        });
        */
    },
    calData() {
      for (var i = 0; i < this.geoJSON.features.length; i++) {
        this.diagnosis += this.geoJSON.features[i]["properties"][
          "new_diagnosis"
        ];
        this.recovery += this.geoJSON.features[i]["properties"]["new_recovery"];
        this.death += this.geoJSON.features[i]["properties"]["new_death"];
      }
    }
  },
  computed: {
    ...mapState({
      geoJSON: state => state.virus.geoJSON
    }),
    mapSize() {
      let styleObj = {
        width: "100%",
        height: "100%"
      };
      return styleObj;
    }
  }
};
</script>

<style scoped>
@import url("https://api.tiles.mapbox.com/mapbox-gl-js/v0.44.2/mapbox-gl.css");
.descrip .title {
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8);
  color: #fff;
  font-size: 38px;
  position: absolute;
  left: 20px;
  top: 20px;
  z-index: 99;
}
.box {
  text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.5), -1px -1px 0 rgba(0, 0, 0, 0.5),
    -1px 1px 0 rgba(0, 0, 0, 0.5), 1px -1px 0 rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 65px;
  left: 20px;
  width: 12%;
  z-index: 99;
}

.redbox .text,
.greenbox .text,
.graybox .text {
  text-align: left;
  color: #fff;
  margin: 10px 10px 0 0;
  position: relative;
  padding: 5px 0 5px 10px;
  font-size: 16px;
  line-height: 16px;
  font-weight: 700;
  text-shadow: 1px 1px 1px #000, 1px 1px 1px #fff;
}
.redbox .text:before,
.greenbox .text:before,
.graybox .text:before {
  position: absolute;
  right: -10px;
  bottom: 0;
  content: "";

  border-style: solid;
  border-width: 5px;
  width: 0;
  height: 0;
  pointer-events: none;
}
.redbox .text:before {
  border-bottom: 5px solid rgba(251, 103, 103, 0.5);
  border-right: 5px solid rgba(251, 103, 103, 0.5);
  border-color: rgba(251, 103, 103, 0.5) transparent transparent
    rgba(251, 103, 103, 0.5);
}
.greenbox .text:before {
  border-bottom: 5px solid rgba(40, 218, 111, 0.5);
  border-right: 5px solid rgba(40, 218, 111, 0.5);
  border-color: rgba(40, 218, 111, 0.5) transparent transparent
    rgba(40, 218, 111, 0.5);
}

.graybox .text:before {
  border-bottom: 5px solid rgba(148, 159, 165, 0.5);
  border-right: 5px solid rgba(148, 159, 165, 0.5);
  border-color: rgba(148, 159, 165, 0.5) transparent transparent
    rgba(148, 159, 165, 0.5);
}
.redbox .text:after,
.greenbox .text:after,
.graybox .text:after {
  position: absolute;
  right: -10px;
  bottom: 10px;
  top: 0;
  content: "";
  width: 10px;
  pointer-events: none;
}
.redbox .text:after {
  background: rgba(251, 103, 103, 0.5);
}
.greenbox .text:after {
  background: rgba(40, 218, 111, 0.5);
}
.graybox .text:after {
  background: rgba(148, 159, 165, 0.5);
}
.redbox .text {
  background: rgba(251, 103, 103, 0.5);
  border-left: 5px solid #fb6767;
}
.greenbox .text {
  background: rgba(40, 218, 111, 0.5);
  border-left: 5px solid #28da6f;
}
.graybox .text {
  background: rgba(148, 159, 165, 0.5);
  border-left: 5px solid #949fa5;
}
.redbox .diagnosis,
.greenbox .recovery,
.graybox .death {
  font-size: 30px;
  margin: 5px 0;
}
.redbox .diagnosis {
  color: #f99;
}
.greenbox .recovery {
  color: #28ca68;
}
.graybox .death {
  color: #dadada;
}
</style>