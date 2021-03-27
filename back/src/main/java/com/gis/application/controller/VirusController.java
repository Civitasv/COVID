package com.gis.application.controller;

import com.gis.application.model.Constants;
import com.gis.application.model.Feature;
import com.gis.application.model.GeoJSON;
import com.gis.application.service.VirusService;
import com.gis.application.vo.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * restful style
 */
@RestController
@RequestMapping("/virus")
public class VirusController {

    private VirusService service;

    @Autowired
    public void setService(VirusService service) {
        this.service = service;
    }

    @GetMapping(value = {"/confirmed", "/confirmed/{timestamp}"})
    public String getAllConfirmedVirusByTimestamp(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        List<VirusConfirmed> virusList;
        if (timestamp == null)
            virusList = service.getAllConfirmedVirusByTimestamp(Constants.END.val);
        else virusList = service.getAllConfirmedVirusByTimestamp(timestamp);
        List<Feature> features = new ArrayList<>();
        for (VirusConfirmed virus : virusList) {
            Feature feature = new Feature(virus.getLocation());
            feature.addProperty("confirmed", String.valueOf(virus.getConfirmed()));
            feature.addProperty("combined_key", virus.getCombinedKey());
            features.add(feature);
        }
        GeoJSON geoJSON = new GeoJSON(features);
        return geoJSON.toString();
    }

    @GetMapping(value = {"/recovered", "/recovered/{timestamp}"})
    public String getAllRecoveredVirusByTimestamp(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        List<VirusRecovered> virusList;
        if (timestamp == null)
            virusList = service.getAllRecoveredVirusByTimestamp(Constants.END.val);
        else virusList = service.getAllRecoveredVirusByTimestamp(timestamp);
        List<Feature> features = new ArrayList<>();
        for (VirusRecovered virus : virusList) {
            Feature feature = new Feature(virus.getLocation());
            feature.addProperty("recovered", String.valueOf(virus.getRecovered()));
            feature.addProperty("combined_key", virus.getCombinedKey());
            features.add(feature);
        }
        GeoJSON geoJSON = new GeoJSON(features);
        return geoJSON.toString();
    }

    @GetMapping({"/deaths", "/deaths/{timestamp}"})
    public String getAllDeathsVirusByTimestamp(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        List<VirusDeaths> virusList;
        if (timestamp == null)
            virusList = service.getAllDeathsVirusByTimestamp(Constants.END.val);
        else virusList = service.getAllDeathsVirusByTimestamp(timestamp);
        List<Feature> features = new ArrayList<>();
        for (VirusDeaths virus : virusList) {
            Feature feature = new Feature(virus.getLocation());
            feature.addProperty("deaths", String.valueOf(virus.getDeaths()));
            feature.addProperty("combined_key", virus.getCombinedKey());
            features.add(feature);
        }
        GeoJSON geoJSON = new GeoJSON(features);
        return geoJSON.toString();
    }

    @GetMapping({"/active", "/active/{timestamp}"})
    public String getAllActiveVirusByTimestamp(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        List<VirusActive> virusList;
        if (timestamp == null)
            virusList = service.getAllActiveVirusByTimestamp(Constants.END.val);
        else virusList = service.getAllActiveVirusByTimestamp(timestamp);
        List<Feature> features = new ArrayList<>();
        for (VirusActive virus : virusList) {
            Feature feature = new Feature(virus.getLocation());
            feature.addProperty("active", String.valueOf(virus.getActive()));
            feature.addProperty("combined_key", virus.getCombinedKey());
            features.add(feature);
        }
        GeoJSON geoJSON = new GeoJSON(features);
        return geoJSON.toString();
    }

    @GetMapping({"/deathsRatio", "/deathsRatio/{timestamp}"})
    public String getAllDeathsRatioByTimestamp(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        List<VirusDeathsRatio> virusList;
        if (timestamp == null)
            virusList = service.getAllDeathsRatioByTimestamp(Constants.END.val);
        else virusList = service.getAllDeathsRatioByTimestamp(timestamp);
        List<Feature> features = new ArrayList<>();
        for (VirusDeathsRatio virus : virusList) {
            Feature feature = new Feature(virus.getLocation());
            feature.addProperty("ratio", String.valueOf(virus.getRatio()));
            feature.addProperty("combined_key", virus.getCombinedKey());
            features.add(feature);
        }
        GeoJSON geoJSON = new GeoJSON(features);
        return geoJSON.toString();
    }

    @GetMapping("/newIncrease")
    public String getWorldNewIncreaseVirusData() {
        Gson gson = new Gson();
        return gson.toJson(service.getWorldNewIncreaseVirusData());
    }

    @GetMapping("/confirmedRecovered")
    public String getConfirmedRecoveredVirusData() {
        Gson gson = new Gson();
        return gson.toJson(service.getConfirmedRecoveredVirusData());
    }

    @GetMapping(value = {"/confirmed/table", "/confirmed/table/{timestamp}"})
    public String getWorldConfirmedVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null)
            return gson.toJson(service.getWorldConfirmedVirusData(Constants.END.val));
        else return gson.toJson(service.getWorldConfirmedVirusData(timestamp));
    }

    @GetMapping(value = {"/recovered/table", "/recovered/table/{timestamp}"})
    public String getWorldRecoveredVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null)
            return gson.toJson(service.getWorldRecoveredVirusData(Constants.END.val));
        else return gson.toJson(service.getWorldRecoveredVirusData(timestamp));
    }

    @GetMapping(value = {"/deaths/table", "/deaths/table/{timestamp}"})
    public String getWorldDeathsVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null)
            return gson.toJson(service.getWorldDeathsVirusData(Constants.END.val));
        else return gson.toJson(service.getWorldDeathsVirusData(timestamp));
    }

    @GetMapping(value = {"/active/table", "/active/table/{timestamp}"})
    public String getWorldActiveVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null)
            return gson.toJson(service.getWorldActiveVirusData(Constants.END.val));
        else return gson.toJson(service.getWorldActiveVirusData(timestamp));
    }

    @GetMapping(value = {"/deathsRatio/table", "/deathsRatio/table/{timestamp}"})
    public String getWorldDeathsRatioData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null)
            return gson.toJson(service.getWorldDeathsRatioData(Constants.END.val));
        else return gson.toJson(service.getWorldDeathsRatioData(timestamp));
    }

    @GetMapping(value = "/confirmed/table/{country}/{timestamp}")
    public String getCountryConfirmedVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getCountryConfirmedVirusData(country, timestamp));
    }

    @GetMapping(value = "/recovered/table/{country}/{timestamp}")
    public String getCountryRecoveredVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getCountryRecoveredVirusData(country, timestamp));
    }

    @GetMapping(value = "/deaths/table/{country}/{timestamp}")
    public String getCountryDeathsVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getCountryDeathsVirusData(country, timestamp));
    }

    @GetMapping(value = "/active/table/{country}/{timestamp}")
    public String getCountryActiveVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getCountryActiveVirusData(country, timestamp));
    }

    @GetMapping(value = "/deathsRatio/table/{country}/{timestamp}")
    public String getCountryDeathsRatioData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getCountryDeathsRatioData(country, timestamp));
    }

    @GetMapping(value = "/confirmed/table/{country}/{province}/{timestamp}")
    public String getProvinceConfirmedVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getProvinceConfirmedVirusData(country, province, timestamp));
    }

    @GetMapping(value = "/recovered/table/{country}/{province}/{timestamp}")
    public String getProvinceRecoveredVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getProvinceRecoveredVirusData(country, province, timestamp));
    }

    @GetMapping(value = "/deaths/table/{country}/{province}/{timestamp}")
    public String getProvinceDeathsVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getProvinceDeathsVirusData(country, province, timestamp));
    }

    @GetMapping(value = "/active/table/{country}/{province}/{timestamp}")
    public String getProvinceActiveVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getProvinceActiveVirusData(country, province, timestamp));
    }

    @GetMapping(value = "/deathsRatio/table/{country}/{province}/{timestamp}")
    public String getProvinceDeathsRatioData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        Gson gson = new Gson();
        return gson.toJson(service.getProvinceDeathsRatioData(country, province, timestamp));
    }
}
