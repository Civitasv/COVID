package com.gis.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VirusDensity {
    private int timestamp;
    private int active;
    private int recovered;
    private int deaths;
    private String combinedKey;
    private String location;
}
