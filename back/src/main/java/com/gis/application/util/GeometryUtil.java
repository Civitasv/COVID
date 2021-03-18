package com.gis.application.util;

import com.gis.application.model.Point;

public class GeometryUtil {
    public static String transformPointToGeometry(Point point) {
        return "POINT" + "(" + point.getLon() + " " + point.getLat() + ")";
    }
}