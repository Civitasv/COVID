<template>
  <div style="height:100%;width:100%;">
    <div class="descrip">
      <div class="box">
        <div class="redbox">
          <p class="text">
            <span>确诊(World)</span>
          </p>
          <p class="diagnosis">
            <span>{{diagnosis}}</span>
          </p>
        </div>
        <div class="greenbox">
          <p class="text">
            <span>治愈(World)</span>
          </p>
          <p class="recovery">
            <span>{{recovery}}</span>
          </p>
        </div>
        <div class="graybox">
          <p class="text">
            <span>死亡(World)</span>
          </p>
          <p class="death">
            <span>{{death}}</span>
          </p>
        </div>
      </div>
    </div>

    <div ref="basicMapbox" :style="mapSize"></div>
    <div class="manage">
      <div class="check">
        <Row graphType="热力图" :legend="heatLegend" @changeVisualData="changeVisualData"></Row>
      </div>
      <div class="check2">
        <Row
          graphType="集合"
          :legend="clusterLegend"
          @changeClusterVisualData="changeClusterVisualData"
        ></Row>
      </div>
    </div>
  </div>
</template>

<style>
</style>

<script>
import mapboxgl from "mapbox-gl";
import MapboxLanguage from "@mapbox/mapbox-gl-language";
const Row = () => import("../layer/Row");

import { mapState, mapActions } from "vuex";

mapboxgl.accessToken =
  "pk.eyJ1IjoiY2l2aXRhc3YiLCJhIjoiY2s3YXBvdDU1MTZpdDNlcDVhb3FrbjdtaiJ9.kLk_w4wIjIQ6dunGULViqw";
export default {
  data() {
    return {
      map: null,
      // 集合图
      clusterID: "cluster-point",
      clusterCountID: "cluster-count",
      unClusterCountID: "unclustered-point",
      clusterSourceID: "cluster-virus-data",
      clusterVisualData: "new_diagnosis",

      // 热力图
      sourceID: "virus-data",
      heatID: "virus-heat",
      visualData: "new_diagnosis",
      diagnosis: 0,
      recovery: 0,
      death: 0,
      heatLegend: [
        { color: "rgba(0, 0, 0, 0)", value: 0 },
        { color: "#927903", value: 100 },
        { color: "rgb(255, 212, 3)", value: 150 },
        { color: "red", value: 1000 }
      ],
      clusterLegend: [
        { color: "rgb(154, 213, 255)", value: 0 },
        { color: "#9af6ff", value: 20 },
        { color: "#00ffff", value: 200 },
        { color: "#ff0", value: 400 },
        { color: "#f1f075", value: 800 },
        { color: "#f9b196", value: 1000 },
        { color: "#f28cb1", value: 2000 }
      ]
    };
  },
  mounted() {
    this.init();
    this.loadData();
  },
  components: {
    Row
  },
  methods: {
    changeVisualData(visualData) {
      this.visualData = visualData;
      if (this.visualData == "") {
        // 如果是空，清除热力图
        this.map.removeLayer(this.heatID);
        this.map.removeSource(this.sourceID);
      } else {
        if (this.map.getLayer(this.heatID)) this.map.removeLayer(this.heatID);
        if (this.map.getSource(this.sourceID))
          this.map.removeSource(this.sourceID);
        this.heatMap();
      }
    },
    changeClusterVisualData(clusterVisualData) {
      this.clusterVisualData = clusterVisualData;
      console.log(clusterVisualData);
      if (this.clusterVisualData == "") {
        // 如果是空，清除集合
        this.map.removeLayer(this.clusterCountID);
        this.map.removeLayer(this.unClusterCountID);
        this.map.removeLayer(this.clusterID);
        this.map.removeSource(this.clusterSourceID);
      } else {
        if (this.map.getLayer(this.clusterCountID))
          this.map.removeLayer(this.clusterCountID);
        if (this.map.getLayer(this.unClusterCountID))
          this.map.removeLayer(this.unClusterCountID);
        if (this.map.getLayer(this.clusterID))
          this.map.removeLayer(this.clusterID);
        if (this.map.getSource(this.clusterSourceID))
          this.map.removeSource(this.clusterSourceID);
        this.clusterMap();
      }
    },
    checkIfMapboxStyleIsLoaded() {
      if (this.map.isStyleLoaded()) {
        return true; // When it is safe to manipulate layers
      } else {
        return false; // When it is not safe to manipulate layers
      }
    },
    init() {
      this.map = new mapboxgl.Map({
        container: this.$refs.basicMapbox,
        style: "mapbox://styles/mapbox/dark-v9",
        center: [114, 38.54],
        zoom: 3
      });

      this.map.addControl(
        new MapboxLanguage({
          defaultLanguage: "zh"
        })
      );
    },
    ...mapActions("virus", ["getAllVirus"]),
    async loadData() {
      // 发送请求获得数据
      if (this.allVirus == "") await this.getAllVirus();
      this.heatMap();
      this.clusterMap();
      this.calData();
    },
    clusterMap() {
      var check = this.checkIfMapboxStyleIsLoaded();
      if (!check) {
        // It's not safe to manipulate layers yet, so wait 200ms and then check again
        var _this = this;
        setTimeout(function() {
          _this.clusterMap();
        }, 200);
        return;
      }
      this.map.addSource(this.clusterSourceID, {
        type: "geojson",
        data: this.allVirus,
        cluster: true,
        clusterMaxZoom: 14,
        clusterRadius: 70,
        clusterProperties: {
          clusterData: ["+", ["get", this.clusterVisualData]]
        }
      });
      this.map.addLayer({
        id: this.clusterID,
        type: "circle",
        source: this.clusterSourceID,
        filter: [">=", ["get", "clusterData"], 1],
        paint: {
          "circle-color": [
            "step",
            ["get", "clusterData"],
            "rgb(154, 213, 255)",
            20,
            "#9af6ff",
            200,
            "#00ffff",
            400,
            "#ff0",
            800,
            "#f1f075",
            1000,
            "#f9b196",
            2000,
            "#f28cb1"
          ],
          "circle-opacity": 0.8,
          "circle-radius": [
            "step",
            ["get", "clusterData"],
            20,
            100,
            25,
            750,
            26,
            1000,
            30,
            2000,
            35,
            3000,
            40,
            10000,
            50,
            20000,
            55,
            40000,
            60,
            100000,
            65,
            200000,
            70,
            300000,
            80
          ]
        }
      });
      this.map.addLayer({
        id: this.clusterCountID,
        type: "symbol",
        source: this.clusterSourceID,
        filter: [">=", ["get", "clusterData"], 1],
        layout: {
          "text-field": ["get", "clusterData"],
          "text-font": ["DIN Offc Pro Medium", "Arial Unicode MS Bold"],
          "text-size": 12
        }
      });
    },
    heatMap() {
      var check = this.checkIfMapboxStyleIsLoaded();
      if (!check) {
        // It's not safe to manipulate layers yet, so wait 200ms and then check again
        var _this = this;
        setTimeout(function() {
          _this.heatMap();
        }, 200);
        return;
      }
      this.map.addSource(this.sourceID, {
        type: "geojson",
        data: this.allVirus
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
    },
    calData() {
      for (var i = 0; i < this.allVirus.features.length; i++) {
        this.diagnosis += this.allVirus.features[i]["properties"][
          "new_diagnosis"
        ];
        this.recovery += this.allVirus.features[i]["properties"][
          "new_recovery"
        ];
        this.death += this.allVirus.features[i]["properties"]["new_death"];
      }
    }
  },
  computed: {
    ...mapState({
      allVirus: state => state.virus.allVirus
    }),
    mapSize() {
      let styleObj = {
        width: "100%",
        height: "100%"
      };
      return styleObj;
    }
  },
  beforeDestroy() {
    this.map.remove();
  }
};
</script>

<style scoped>
@import url("https://api.tiles.mapbox.com/mapbox-gl-js/v0.44.2/mapbox-gl.css");

.box {
  text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.5), -1px -1px 0 rgba(0, 0, 0, 0.5),
    -1px 1px 0 rgba(0, 0, 0, 0.5), 1px -1px 0 rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 5vh;
  left: 20px;
  width: 16%;
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
.check {
  position: absolute;
  bottom: 70px;
  right: 20px;
  width: 300px;
  z-index: 99;
  background-color: #2f3233c7;
  height: 40px;
}
.check2 {
  position: absolute;
  bottom: 30px;
  right: 20px;
  width: 300px;
  z-index: 99;
  background-color: #2f3233c7;
  height: 40px;
}
.manage {
  background-color: #2f3233c7;
}
</style>