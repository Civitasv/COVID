package com.gis.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VirusConfirmedRecovered {
    private int timestamp;
    private int currentConfirmed;
    private int deaths;
    private int recovered;
}
