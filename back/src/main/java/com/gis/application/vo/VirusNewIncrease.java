package com.gis.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VirusNewIncrease {
    private int timestamp;
    private int newConfirmed;
    private int newDeaths;
    private int newRecovered;
}
