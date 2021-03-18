package com.gis.application.model;

import lombok.*;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Point {
    private double lon; // 经度 x
    private double lat;  // 纬度 y
}
