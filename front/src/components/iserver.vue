<template>
  <sm-web-map :map-options="mapOptions"></sm-web-map>
</template>

<script>
export default {
  data() {
    return {
      mapOptions: {
        container: "map", // container id
        style: {
          version: 8,
          sources: {
            "raster-tiles": {
              type: "raster",
              tiles: [
                "http://localhost:8090/iserver/services/map-iservertest-2/rest/maps/res2_4m%40res2_4m.ol3"
              ],
              tileSize: 256
            }
          },
          layers: [
            {
              id: "simple-tiles",
              type: "raster",
              source: "raster-tiles",
              minzoom: 0,
              maxzoom: 22
            }
          ]
        },
        center: [120.143, 30.236], // starting position
        zoom: 3 // starting zoom
      }
    };
  }
};
</script>
