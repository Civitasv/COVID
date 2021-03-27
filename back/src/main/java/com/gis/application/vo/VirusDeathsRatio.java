package com.gis.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VirusDeathsRatio {
    private int timestamp;
    private double ratio;  // 死亡率
    private String combinedKey;
    private String location;
}
