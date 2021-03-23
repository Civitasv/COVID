<template>
  <div id="mapview"></div>
</template>

<style scoped>
#viewDiv {
  padding: 0;
  margin: 0;
  height: 100%;
}
</style>>

<script>
import { loadModules } from "esri-loader";

export default {
  data() {
    return {
      api_key:
        "AAPKe4ed3d3d31ef429e8e9df534b754fa3ecQg4gIV41QyDnluBCNlOOTdBkoKXMcjVfYBkh9cyz3oJ03jtS8aZMFO9C05pjNwe",
    };
  },
  methods: {
    initMap() {
      const options = {
        url: "https://js.arcgis.com/4.18/",
        css: "https://js.arcgis.com/4.18/esri/themes/light/main.css",
      };
      loadModules(["esri/Map", "esri/views/MapView"], options)
        .then(([Map, MapView]) => {
          //业务代码在此处编写
          const map = new Map({
            //实例化地图
            basemap: "streets",
          });

          new MapView({
            //实例化地图视图
            container: "mapview",
            map: map,
            zoom: 11,
            center: [104.072044, 30.663776],
          });
        })
        .catch((err) => {
          console.log("地图创建失败" + err);
        });
    },
  },
  mounted() {
    this.initMap();
  },
};
</script>