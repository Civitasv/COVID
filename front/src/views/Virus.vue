<template>
  <div style="height:100%;width:100%;">
    <div class="data">
      <Button type="success" class="btn" ghost @click="loadData">数据获得</Button>
      <Button type="primary" class="btn" ghost @click="heatMap">绘制热力图</Button>
      <Button type="warning" class="btn" ghost @click="removeHeat">清除热力图</Button>
    </div>
    <div id="menu">
      <input
        id="streets-v11"
        type="radio"
        name="rtoggle"
        value="streets"
        checked="checked"
        @click="swichLayer"
      />
      <label for="streets">streets</label>
      <input id="light-v10" type="radio" name="rtoggle" value="light" @click="swichLayer" />
      <label for="light">light</label>
      <input id="dark-v10" type="radio" name="rtoggle" value="dark" @click="swichLayer" />
      <label for="dark">dark</label>
      <input id="outdoors-v11" type="radio" name="rtoggle" value="outdoors" @click="swichLayer" />
      <label for="outdoors">outdoors</label>
      <input id="satellite-v9" type="radio" name="rtoggle" value="satellite" @click="swichLayer" />
      <label for="satellite">satellite</label>
    </div>
    <div ref="basicMapbox" :style="mapSize"></div>
  </div>
</template>

<style>
</style>

<script>
import mapboxgl from "mapbox-gl";
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      map: null,
      loaded: false,
      heatID: "virus-heat",
      pointID: "virus-point",
      sourceID: "virus-data"
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      mapboxgl.accessToken =
        "pk.eyJ1IjoiY2l2aXRhc3YiLCJhIjoiY2s3YXByZWltMDBydjNubjJnbXZkM3o0YyJ9.npjfP2uX8YHUtd2uOm6cJg";
      this.map = new mapboxgl.Map({
        container: this.$refs.basicMapbox,
        style: "mapbox://styles/mapbox/streets-v11",
        center: [114, 38.54],
        zoom: 6
      });
      // 地图导航
      var nav = new mapboxgl.NavigationControl();
      this.map.addControl(nav, "top-left");
      // 比例尺
      var scale = new mapboxgl.ScaleControl({
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
      // console.log(map)
    },
    ...mapActions("virus", ["getAllVirus"]),
    loadData() {
      // 发送请求获得数据
      this.getAllVirus();
    },
    heatMap() {
      // 添加geojson格式数据
      if (this.geoJSON == "") {
        alert("请先加载数据");
        return;
      }
      if (!this.loaded) {
        this.map.addSource(this.sourceID, {
          type: "geojson",
          data: this.geoJSON
        });
        this.loaded = true;

        // 添加热力图图层
        this.map.addLayer(
          {
            id: this.heatID,
            type: "heatmap",
            source: this.sourceID,
            maxzoom: 9,
            paint: {
              // 使用new_diagnosis属性
              "heatmap-weight": [
                "interpolate",
                ["linear"],
                ["get", "new_diagnosis"],
                0,
                0,
                6,
                1
              ],
              "heatmap-intensity": [
                "interpolate",
                ["linear"],
                ["zoom"],
                0,
                1,
                9,
                3
              ],
              // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
              // Begin color ramp at 0-stop with a 0-transparancy color
              // to create a blur-like effect.
              "heatmap-color": [
                "interpolate",
                ["linear"],
                ["heatmap-density"],
                0,
                "rgba(33,102,172,0)",
                0.2,
                "rgb(103,169,207)",
                0.4,
                "rgb(209,229,240)",
                0.6,
                "rgb(253,219,199)",
                0.8,
                "rgb(239,138,98)",
                1,
                "rgb(178,24,43)"
              ],
              // Adjust the heatmap radius by zoom level
              "heatmap-radius": [
                "interpolate",
                ["linear"],
                ["zoom"],
                0,
                2,
                9,
                20
              ],
              // Transition from heatmap to circle layer by zoom level
              "heatmap-opacity": [
                "interpolate",
                ["linear"],
                ["zoom"],
                7,
                1,
                9,
                0
              ]
            }
          },
          "waterway-label"
        );
        // 添加圆圈图层
        this.map.addLayer(
          {
            id: this.pointID,
            type: "circle",
            source: this.sourceID,
            minzoom: 7,
            paint: {
              "circle-radius": [
                "interpolate",
                ["linear"],
                ["zoom"],
                7,
                [
                  "interpolate",
                  ["linear"],
                  ["get", "new_diagnosis"],
                  1,
                  1,
                  6,
                  4
                ],
                16,
                [
                  "interpolate",
                  ["linear"],
                  ["get", "new_diagnosis"],
                  1,
                  5,
                  6,
                  50
                ]
              ],
              "circle-color": [
                "interpolate",
                ["linear"],
                ["get", "new_diagnosis"],
                1,
                "rgba(33,102,172,0)",
                2,
                "rgb(103,169,207)",
                3,
                "rgb(209,229,240)",
                4,
                "rgb(253,219,199)",
                5,
                "rgb(239,138,98)",
                6,
                "rgb(178,24,43)"
              ],
              "circle-stroke-color": "white",
              "circle-stroke-width": 1,
              "circle-opacity": [
                "interpolate",
                ["linear"],
                ["zoom"],
                7,
                0,
                8,
                1
              ]
            }
          },
          "waterway-label"
        );
      }
    },
    removeHeat() {
      if (this.loaded) {
        this.map.removeLayer(this.heatID);
        this.map.removeLayer(this.pointID);
        this.map.removeSource(this.sourceID);
        this.loaded = false;
      }
    },
    swichLayer(layer) {
      this.removeHeat();
      var layerId = layer.target.id;
      this.map.setStyle("mapbox://styles/mapbox/" + layerId);
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
.data {
  position: absolute;
  z-index: 1;
  padding-left: 30px;
  font-family: "Open Sans", sans-serif;
}

.data .btn {
  margin: 20px;
}
#menu {
  z-index: 1;
  position: absolute;
  right: 0px;
  bottom: 30px;
  background: #fff;
  padding: 10px;
  font-family: "Open Sans", sans-serif;
}
#menu label {
  padding: 5px;
}
</style>