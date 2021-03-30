function GeoJsonStringToUrl(geojson) {
    const blob = new Blob([JSON.stringify(geojson)], {
        type: "application/json",
    });
    const url = URL.createObjectURL(blob);
    return url;
}
export { GeoJsonStringToUrl }