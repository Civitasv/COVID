<template>
  <div id="map" class="map"></div>
</template>

<script>
import "ol/ol.css";
import Map from "ol/Map";
import View from "ol/View";
import { createEmpty, getWidth, getHeight, extend } from "ol/extent";
import KML from "ol/format/KML";
import { defaults as defaultInteractions, Select } from "ol/interaction";
import { Tile as TileLayer, Vector as VectorLayer } from "ol/layer";
import { Cluster, Stamen, Vector as VectorSource } from "ol/source";
import {
  Circle as CircleStyle,
  Fill,
  RegularShape,
  Stroke,
  Style,
  Text
} from "ol/style";

export default {
  data() {
    return {
      fillStyle: new Fill({
        color: "rgba(255, 153, 0, 0.8)"
      }),
      strokeStyle: new Stroke({
        color: "rgba(255, 204, 0, 0.2)",
        width: 1
      }),
      textFill: new Fill({
        color: "#fff"
      }),
      textStroke: new Stroke({
        color: "rgba(0, 0, 0, 0.6)",
        width: 3
      }),
      invisibleFill: new Fill({
        color: "rgba(255, 255, 255, 0.01)"
      }),
      maxFeatureCount: 0,
      vector: null,
      currentResolution: null,
      map: null
    };
  },
  mounted() {
    this.init();
  },
  methods: {
    createStyle(feature) {
      var confirmed = feature.get("confirmed");
      //var magnitude = parseFloat(name.substr(2));
      var radius = 5 + 20 * (confirmed - 5);

      return new Style({
        geometry: feature.getGeometry(),
        image: new RegularShape({
          radius1: radius,
          radius2: 3,
          points: 5,
          angle: Math.PI,
          fill: this.fillStyle,
          stroke: this.strokeStyle
        })
      });
    },
    calculateClusterInfo(resolution) {
      this.maxFeatureCount = 0;
      var features = this.vector.getSource().getFeatures();
      var feature, radius;
      for (var i = features.length - 1; i >= 0; --i) {
        feature = features[i];
        var originalFeatures = feature.get("features");
        var extent = createEmpty();
        var j = void 0,
          jj = void 0;
        for (j = 0, jj = originalFeatures.length; j < jj; ++j) {
          extend(extent, originalFeatures[j].getGeometry().getExtent());
        }
        this.maxFeatureCount = Math.max(this.maxFeatureCount, jj);
        radius = (0.25 * (getWidth(extent) + getHeight(extent))) / resolution;
        feature.set("radius", radius);
      }
    },
    styleFunction(feature, resolution) {
      if (resolution != this.currentResolution) {
        this.calculateClusterInfo(resolution);
        this.currentResolution = resolution;
      }
      var style;
      var size = feature.get("features").length;
      if (size > 1) {
        style = new Style({
          image: new CircleStyle({
            radius: feature.get("radius"),
            fill: new Fill({
              color: [
                255,
                153,
                0,
                Math.min(0.8, 0.4 + size / this.maxFeatureCount)
              ]
            })
          }),
          text: new Text({
            text: size.toString(),
            fill: this.textFill,
            stroke: this.textStroke
          })
        });
      } else {
        var originalFeature = feature.get("features")[0];
        style = this.createStyle(originalFeature);
      }
      return style;
    },
    selectStyleFunction(feature) {
      var styles = [
        new Style({
          image: new CircleStyle({
            radius: feature.get("radius"),
            fill: this.invisibleFill
          })
        })
      ];
      var originalFeatures = feature.get("features");
      var originalFeature;
      for (var i = originalFeatures.length - 1; i >= 0; --i) {
        originalFeature = originalFeatures[i];
        styles.push(this.createStyle(originalFeature));
      }
      return styles;
    },
    init() {
      this.vector = new VectorLayer({
        source: new Cluster({
          distance: 40,
          source: new VectorSource({
            url: "http://47.100.65.60/virus/virus.kml",
            format: new KML({
              extractStyles: false
            })
          })
        }),
        style: this.styleFunction
      });
      var raster = new TileLayer({
        source: new Stamen({
          layer: "toner"
        })
      });

      this.map = new Map({
        layers: [raster, this.vector],
        interactions: defaultInteractions().extend([
          new Select({
            condition: function(evt) {
              return evt.type == "pointermove" || evt.type == "singleclick";
            },
            style: this.selectStyleFunction
          })
        ]),
        target: "map",
        view: new View({
          center: [0, 0],
          zoom: 2
        })
      });
    }
  }
};
</script>

<style scoped>
.map {
  height: 100%;
  width: 100%;
}
</style>