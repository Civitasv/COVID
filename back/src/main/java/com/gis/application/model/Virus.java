package com.gis.application.model;

import com.gis.application.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Virus {
    private int id;
    private long timestamp;
    private double lat;
    private double lon;
    private String location;
    private int confirmed;
    private int deaths;
    private int recovered;
    private int suspect;
    private int currentConfirmed;
    private int newConfirmed;
    private int newDeaths;
    private int newRecovered;
    private int newSuspect;
    private String country = "";
    private String province = "";
    private String city = "";
    private String combinedKey = "";
    private double caseFatalityRatio;

    public Virus(long timestamp, double lat, double lon, String location, int confirmed, int deaths, int recovered, int suspect, int currentConfirmed, int newConfirmed, int newDeaths, int newRecovered, int newSuspect, String country, String province, String city, String combinedKey, double caseFatalityRatio) {
        this.timestamp = timestamp;
        this.lat = lat;
        this.lon = lon;
        this.location = location;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
        this.suspect = suspect;
        this.currentConfirmed = currentConfirmed;
        this.newConfirmed = newConfirmed;
        this.newDeaths = newDeaths;
        this.newRecovered = newRecovered;
        this.newSuspect = newSuspect;
        this.country = country;
        this.province = province;
        this.city = city;
        this.combinedKey = combinedKey;
        this.caseFatalityRatio = caseFatalityRatio;
    }
}
