package com.gis.application.util;

public class GISUtil {
    public static String geometryToString(double longitude, double latitude){
        String geoStr = "POINT" + "(" + longitude + " " + latitude + ")";
        return geoStr;
    }
}

