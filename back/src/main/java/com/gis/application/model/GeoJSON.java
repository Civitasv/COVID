package com.gis.application.model;

import java.util.List;

public class GeoJSON {
    private final String type;
    private final List<Feature> features;

    public GeoJSON(String type, List<Feature> features) {
        this.type = type;
        this.features = features;
    }

    public GeoJSON(List<Feature> features) {
        this.type = "FeatureCollection";
        this.features = features;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + type +"\"" +
                ", \"features\":" + features +
                '}';
    }
}
