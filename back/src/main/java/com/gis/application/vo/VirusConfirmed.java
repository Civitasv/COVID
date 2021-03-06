package com.gis.application.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VirusConfirmed {
    private int timestamp;
    private int confirmed;
    private String combinedKey;
    private String location;
}
