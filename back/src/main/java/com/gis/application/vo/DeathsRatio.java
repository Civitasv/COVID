package com.gis.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeathsRatio {
    private int timestamp;
    private double ratio;
    private String combinedKey;
}
