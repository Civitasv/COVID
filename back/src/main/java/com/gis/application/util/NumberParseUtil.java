package com.gis.application.util;

public class NumberParseUtil {
    public static int parseInt(String val) {
        int res;
        try {
            res = Integer.parseInt(val);
        } catch (Exception e) {
            res = 0;
        }
        return res;
    }
}
