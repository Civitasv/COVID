<template>
  <div id="map" ref="rootMap" class="map"></div>
</template>

<script>
import "ol/ol.css";
import Map from "ol/Map";
import View from "ol/View";
import TileLayer from "ol/layer/Tile";
import KML from "ol/format/KML";
import { Heatmap as HeatmapLayer } from "ol/layer";
import Stamen from "ol/source/Stamen";
import VectorSource from "ol/source/Vector";

export default {
  data() {
    return {
      map: null
    };
  },
  methods: {
    init() {
      var layers = [
        new TileLayer({
          source: new Stamen({
            layer: "toner"
          })
        }),
        new HeatmapLayer({
          source: new VectorSource({
            url: "http://47.100.65.60/virus/virus.kml",
            format: new KML({
              extractStyles: false
            })
          }),
          blur: parseInt(15, 10),
          radius: parseInt(14, 10)
        })
      ];

      this.map = new Map({
        layers: layers,
        target: "map",
        view: new View({
          center: [0, 0],
          zoom: 2
        })
      });
    }
  },
  mounted() {
    this.init();
  }
};
</script>

<style scoped>
.map {
  height: 100%;
}
</style>