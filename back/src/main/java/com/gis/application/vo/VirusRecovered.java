package com.gis.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VirusRecovered {
    private int timestamp;
    private int recovered;
    private String combinedKey;
    private String location;
}
