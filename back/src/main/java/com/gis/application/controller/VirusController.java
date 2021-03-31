package com.gis.application.controller;

import com.gis.application.model.Constants;
import com.gis.application.model.Feature;
import com.gis.application.model.GeoJSON;
import com.gis.application.redis.RedisCacheManager;
import com.gis.application.service.VirusService;
import com.gis.application.vo.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * restful style
 */
@RestController
@RequestMapping("/virus")
public class VirusController {

    private VirusService service;
    private RedisCacheManager redisCacheManager;

    @Autowired
    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        this.redisCacheManager = redisCacheManager;
    }

    @Autowired
    public void setService(VirusService service) {
        this.service = service;
    }

    @GetMapping("/confirmed/{timestamp}")
    public String getAllConfirmedVirusByTimestamp(@PathVariable("timestamp") Integer timestamp) {
        List<VirusConfirmed> virusList;
        // 如果redis缓存中已经存在
        if (redisCacheManager.hasKey("confirmed" + timestamp)) {
            return redisCacheManager.get("confirmed" + timestamp).toString();
        }
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
        // 存入缓存
        redisCacheManager.set("confirmed" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping("/recovered/{timestamp}")
    public String getAllRecoveredVirusByTimestamp(@PathVariable("timestamp") Integer timestamp) {
        List<VirusRecovered> virusList;
        // 如果redis缓存中已经存在
        if (redisCacheManager.hasKey("recovered" + timestamp)) {
            return redisCacheManager.get("recovered" + timestamp).toString();
        }
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
        // 存入缓存
        redisCacheManager.set("recovered" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping("/deaths/{timestamp}")
    public String getAllDeathsVirusByTimestamp(@PathVariable("timestamp") Integer timestamp) {
        List<VirusDeaths> virusList;
        // 如果redis缓存中已经存在
        if (redisCacheManager.hasKey("deaths" + timestamp)) {
            return redisCacheManager.get("deaths" + timestamp).toString();
        }
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
        // 存入缓存
        redisCacheManager.set("deaths" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping("/active/{timestamp}")
    public String getAllActiveVirusByTimestamp(@PathVariable("timestamp") Integer timestamp) {
        List<VirusActive> virusList;
        // 如果redis缓存中已经存在
        if (redisCacheManager.hasKey("active" + timestamp)) {
            return redisCacheManager.get("active" + timestamp).toString();
        }
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
        // 存入缓存
        redisCacheManager.set("active" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping("/deathsRatio/{timestamp}")
    public String getAllDeathsRatioByTimestamp(@PathVariable("timestamp") Integer timestamp) {
        List<VirusDeathsRatio> virusList;
        // 如果redis缓存中已经存在
        if (redisCacheManager.hasKey("deathsRatio" + timestamp)) {
            return redisCacheManager.get("deathsRatio" + timestamp).toString();
        }
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
        // 存入缓存
        redisCacheManager.set("deathsRatio" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping({"/newIncrease", "/newIncrease/{country}", "/newIncrease/{country}/{province}", "/newIncrease/{country}/{province}/{city}"})
    public String getWorldNewIncreaseVirusData(@PathVariable(value = "country", required = false) String country,
                                               @PathVariable(value = "province", required = false) String province,
                                               @PathVariable(value = "city", required = false) String city) {
        Gson gson = new Gson();
        if (country == null || country.isEmpty()) {
            // 如果redis缓存中已经存在
            if (redisCacheManager.hasKey("worldIncrease")) {
                return redisCacheManager.get("worldIncrease").toString();
            }
            String data = gson.toJson(service.getWorldNewIncreaseVirusData());
            redisCacheManager.set("worldIncrease", data);
            return data;
        } else {
            if (province == null || province.isEmpty()) {
                if (redisCacheManager.hasKey(country + "Increase")) {
                    return redisCacheManager.get(country + "Increase").toString();
                }
                String data = gson.toJson(service.getCountryNewIncreaseVirusData(country));
                redisCacheManager.set(country + "Increase", data);
                return data;
            } else {
                if (city == null || city.isEmpty()) {
                    if (redisCacheManager.hasKey(country + province + "Increase")) {
                        return redisCacheManager.get(country + province + "Increase").toString();
                    }
                    String data = gson.toJson(service.getProvinceNewIncreaseVirusData(country, province));
                    redisCacheManager.set(country + province + "Increase", data);
                    return data;
                } else {
                    if (redisCacheManager.hasKey(country + province + city + "Increase")) {
                        return redisCacheManager.get(country + province + city + "Increase").toString();
                    }
                    String data = gson.toJson(service.getCityNewIncreaseVirusData(country, province, city));
                    redisCacheManager.set(country + province + city + "Increase", data);
                    return data;
                }
            }
        }
    }

    @GetMapping({"/confirmedRecovered", "/confirmedRecovered/{country}", "/confirmedRecovered/{country}/{province}", "/confirmedRecovered/{country}/{province}/{city}"})
    public String getConfirmedRecoveredVirusData(@PathVariable(value = "country", required = false) String country,
                                                 @PathVariable(value = "province", required = false) String province,
                                                 @PathVariable(value = "city", required = false) String city) {
        Gson gson = new Gson();
        if (country == null || country.isEmpty()) {
            // 如果redis缓存中已经存在
            if (redisCacheManager.hasKey("worldConfirmedRecovered")) {
                return redisCacheManager.get("worldConfirmedRecovered").toString();
            }
            String data = gson.toJson(service.getWorldConfirmedRecoveredVirusData());
            redisCacheManager.set("worldConfirmedRecovered", data);
            return data;
        } else {
            if (province == null || province.isEmpty()) {
                if (redisCacheManager.hasKey(country + "ConfirmedRecovered")) {
                    return redisCacheManager.get(country + "ConfirmedRecovered").toString();
                }
                String data = gson.toJson(service.getCountryConfirmedRecoveredVirusData(country));
                redisCacheManager.set(country + "ConfirmedRecovered", data);
                return data;
            } else {
                if (city == null || city.isEmpty()) {
                    if (redisCacheManager.hasKey(country + province + "ConfirmedRecovered")) {
                        return redisCacheManager.get(country + province + "ConfirmedRecovered").toString();
                    }
                    String data = gson.toJson(service.getProvinceConfirmedRecoveredVirusData(country, province));
                    redisCacheManager.set(country + province + "ConfirmedRecovered", data);
                    return data;
                } else {
                    if (redisCacheManager.hasKey(country + province + city + "ConfirmedRecovered")) {
                        return redisCacheManager.get(country + province + city + "ConfirmedRecovered").toString();
                    }
                    String data = gson.toJson(service.getCityConfirmedRecoveredVirusData(country, province, city));
                    redisCacheManager.set(country + province + city + "ConfirmedRecovered", data);
                    return data;
                }
            }
        }
    }

    @GetMapping(value = {"/confirmed/table", "/confirmed/table/{timestamp}"})
    public String getWorldConfirmedVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null) {
            if (redisCacheManager.hasKey("confirmedTable" + Constants.END.val)) {
                return redisCacheManager.get("confirmedTable" + Constants.END.val).toString();
            }
            String data = gson.toJson(service.getWorldConfirmedVirusData(Constants.END.val));
            // 存入缓存
            redisCacheManager.set("confirmedTable" + Constants.END.val, data);
            return data;
        } else {
            if (redisCacheManager.hasKey("confirmedTable" + timestamp)) {
                return redisCacheManager.get("confirmedTable" + timestamp).toString();
            }
            String data = gson.toJson(service.getWorldConfirmedVirusData(timestamp));
            // 存入缓存
            redisCacheManager.set("confirmedTable" + timestamp, data);
            return data;
        }
    }

    @GetMapping(value = {"/recovered/table", "/recovered/table/{timestamp}"})
    public String getWorldRecoveredVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null) {
            if (redisCacheManager.hasKey("recoveredTable" + Constants.END.val)) {
                return redisCacheManager.get("recoveredTable" + Constants.END.val).toString();
            }
            String data = gson.toJson(service.getWorldRecoveredVirusData(Constants.END.val));
            // 存入缓存
            redisCacheManager.set("recoveredTable" + Constants.END.val, data);
            return data;
        } else {
            if (redisCacheManager.hasKey("recoveredTable" + timestamp)) {
                return redisCacheManager.get("recoveredTable" + timestamp).toString();
            }
            String data = gson.toJson(service.getWorldRecoveredVirusData(timestamp));
            // 存入缓存
            redisCacheManager.set("recoveredTable" + timestamp, data);
            return data;
        }
    }

    @GetMapping(value = {"/deaths/table", "/deaths/table/{timestamp}"})
    public String getWorldDeathsVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null) {
            if (redisCacheManager.hasKey("deathsTable" + Constants.END.val)) {
                return redisCacheManager.get("deathsTable" + Constants.END.val).toString();
            }
            String data = gson.toJson(service.getWorldDeathsVirusData(Constants.END.val));
            // 存入缓存
            redisCacheManager.set("deathsTable" + Constants.END.val, data);
            return data;
        } else {
            if (redisCacheManager.hasKey("deathsTable" + timestamp)) {
                return redisCacheManager.get("deathsTable" + timestamp).toString();
            }
            String data = gson.toJson(service.getWorldDeathsVirusData(Constants.END.val));
            // 存入缓存
            redisCacheManager.set("deathsTable" + timestamp, data);
            return data;
        }
    }

    @GetMapping(value = {"/active/table", "/active/table/{timestamp}"})
    public String getWorldActiveVirusData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null) {
            if (redisCacheManager.hasKey("activeTable" + Constants.END.val)) {
                return redisCacheManager.get("activeTable" + Constants.END.val).toString();
            }
            String data = gson.toJson(service.getWorldActiveVirusData(Constants.END.val));
            // 存入缓存
            redisCacheManager.set("activeTable" + Constants.END.val, data);
            return data;
        } else {
            if (redisCacheManager.hasKey("activeTable" + timestamp)) {
                return redisCacheManager.get("activeTable" + timestamp).toString();
            }
            String data = gson.toJson(service.getWorldActiveVirusData(timestamp));
            // 存入缓存
            redisCacheManager.set("activeTable" + timestamp, data);
            return data;
        }
    }

    @GetMapping(value = {"/deathsRatio/table", "/deathsRatio/table/{timestamp}"})
    public String getWorldDeathsRatioData(@PathVariable(value = "timestamp", required = false) Integer timestamp) {
        Gson gson = new Gson();
        if (timestamp == null) {
            if (redisCacheManager.hasKey("deathsRatioTable" + Constants.END.val)) {
                return redisCacheManager.get("deathsRatioTable" + Constants.END.val).toString();
            }
            String data = gson.toJson(service.getWorldDeathsRatioData(Constants.END.val));
            // 存入缓存
            redisCacheManager.set("deathsRatioTable" + Constants.END.val, data);
            return data;
        } else {
            if (redisCacheManager.hasKey("deathsRatioTable" + timestamp)) {
                return redisCacheManager.get("deathsRatioTable" + timestamp).toString();
            }
            String data = gson.toJson(service.getWorldDeathsRatioData(timestamp));
            // 存入缓存
            redisCacheManager.set("deathsRatioTable" + timestamp, data);
            return data;
        }
    }

    @GetMapping(value = "/confirmed/table/{country}/{timestamp}")
    public String getCountryConfirmedVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + "confirmedTable" + timestamp)) {
            return redisCacheManager.get(country + "confirmedTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getCountryConfirmedVirusData(country, timestamp));
        // 存入缓存
        redisCacheManager.set(country + "confirmedTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/recovered/table/{country}/{timestamp}")
    public String getCountryRecoveredVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + "recoveredTable" + timestamp)) {
            return redisCacheManager.get(country + "recoveredTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getCountryRecoveredVirusData(country, timestamp));
        // 存入缓存
        redisCacheManager.set(country + "recoveredTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/deaths/table/{country}/{timestamp}")
    public String getCountryDeathsVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + "deathsTable" + timestamp)) {
            return redisCacheManager.get(country + "deathsTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getCountryDeathsVirusData(country, timestamp));
        // 存入缓存
        redisCacheManager.set(country + "deathsTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/active/table/{country}/{timestamp}")
    public String getCountryActiveVirusData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + "activeTable" + timestamp)) {
            return redisCacheManager.get(country + "activeTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getCountryActiveVirusData(country, timestamp));
        // 存入缓存
        redisCacheManager.set(country + "activeTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/deathsRatio/table/{country}/{timestamp}")
    public String getCountryDeathsRatioData(@PathVariable("country") String country, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + "deathsRatioTable" + timestamp)) {
            return redisCacheManager.get(country + "deathsRatioTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getCountryDeathsRatioData(country, timestamp));
        // 存入缓存
        redisCacheManager.set(country + "deathsRatioTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/confirmed/table/{country}/{province}/{timestamp}")
    public String getProvinceConfirmedVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + province + "confirmedTable" + timestamp)) {
            return redisCacheManager.get(country + province + "confirmedTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getProvinceConfirmedVirusData(country, province, timestamp));
        // 存入缓存
        redisCacheManager.set(country + province + "confirmedTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/recovered/table/{country}/{province}/{timestamp}")
    public String getProvinceRecoveredVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + province + "recoveredTable" + timestamp)) {
            return redisCacheManager.get(country + province + "recoveredTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getProvinceRecoveredVirusData(country, province, timestamp));
        // 存入缓存
        redisCacheManager.set(country + province + "recoveredTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/deaths/table/{country}/{province}/{timestamp}")
    public String getProvinceDeathsVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + province + "deathsTable" + timestamp)) {
            return redisCacheManager.get(country + province + "deathsTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getProvinceDeathsVirusData(country, province, timestamp));
        // 存入缓存
        redisCacheManager.set(country + province + "deathsTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/active/table/{country}/{province}/{timestamp}")
    public String getProvinceActiveVirusData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + province + "activeTable" + timestamp)) {
            return redisCacheManager.get(country + province + "activeTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getProvinceActiveVirusData(country, province, timestamp));
        // 存入缓存
        redisCacheManager.set(country + province + "activeTable" + timestamp, data);
        return data;
    }

    @GetMapping(value = "/deathsRatio/table/{country}/{province}/{timestamp}")
    public String getProvinceDeathsRatioData(@PathVariable("country") String country, @PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(country + province + "deathsRatioTable" + timestamp)) {
            return redisCacheManager.get(country + province + "deathsRatioTable" + timestamp).toString();
        }
        Gson gson = new Gson();
        String data = gson.toJson(service.getProvinceDeathsRatioData(country, province, timestamp));
        // 存入缓存
        redisCacheManager.set(country + province + "deathsRatioTable" + timestamp, data);
        return data;
    }

    @GetMapping("/china/confirmed/{timestamp}")
    public String getChinaProvinceConfirmedVirusByTimestamp(@PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey("chinaProvinceConfirmed" + timestamp)) {
            return redisCacheManager.get("chinaProvinceConfirmed" + timestamp).toString();
        }
        List<VirusConfirmed> virusList = service.getChinaProvinceConfirmedVirusByTimestamp(timestamp);
        List<Feature> features = new ArrayList<>();
        for (VirusConfirmed virus : virusList) {
            String code = virus.getLocation();
            try {
                String geojson = Files.lines(Paths.get("src", "main", "resources", "static", "province", code + ".json")).collect(Collectors.joining("\n"));
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(geojson, JsonObject.class);
                JsonArray array = jsonObject.getAsJsonArray("features");
                JsonObject geometry = array.get(0).getAsJsonObject().getAsJsonObject("geometry");
                Feature feature = new Feature(geometry.toString());
                feature.addProperty("confirmed", String.valueOf(virus.getConfirmed()));
                feature.addProperty("combined_key", virus.getCombinedKey());
                features.add(feature);
            } catch (IOException e) {
                return "";
            }
        }
        GeoJSON geoJSON = new GeoJSON(features);
        // 存入缓存
        redisCacheManager.set("chinaProvinceConfirmed" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping("/china/confirmed/{province}/{timestamp}")
    public String getChinaCityConfirmedVirusByTimestamp(@PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(province + "ChinaCityConfirmed" + timestamp)) {
            return redisCacheManager.get(province + "ChinaCityConfirmed" + timestamp).toString();
        }
        List<VirusConfirmed> virusList = service.getChinaCityConfirmedVirusByTimestamp(province, timestamp);
        List<Feature> features = new ArrayList<>();
        if (virusList.size() == 0) {
            virusList = service.getChinaProvinceConfirmedVirusByTimestamp(province, timestamp);
            for (VirusConfirmed virus : virusList) {
                String code = virus.getLocation();
                try {
                    String geojson = Files.lines(Paths.get("src", "main", "resources", "static", "province", code + ".json")).collect(Collectors.joining("\n"));
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(geojson, JsonObject.class);
                    JsonArray array = jsonObject.getAsJsonArray("features");
                    JsonObject geometry = array.get(0).getAsJsonObject().getAsJsonObject("geometry");
                    Feature feature = new Feature(geometry.toString());
                    feature.addProperty("confirmed", String.valueOf(virus.getConfirmed()));
                    feature.addProperty("combined_key", virus.getCombinedKey());
                    features.add(feature);
                } catch (IOException e) {
                    return "";
                }
            }
        } else {
            for (VirusConfirmed virus : virusList) {
                String code = virus.getLocation();
                try {
                    String geojson = Files.lines(Paths.get("src", "main", "resources", "static", "city", code + ".json")).collect(Collectors.joining("\n"));
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(geojson, JsonObject.class);
                    JsonArray array = jsonObject.getAsJsonArray("features");
                    JsonObject geometry = array.get(0).getAsJsonObject().getAsJsonObject("geometry");
                    Feature feature = new Feature(geometry.toString());
                    feature.addProperty("confirmed", String.valueOf(virus.getConfirmed()));
                    feature.addProperty("combined_key", virus.getCombinedKey());
                    features.add(feature);
                } catch (IOException e) {
                    return "";
                }
            }
        }
        GeoJSON geoJSON = new GeoJSON(features);
        // 存入缓存
        redisCacheManager.set(province + "ChinaCityConfirmed" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping("/china/density/{timestamp}")
    public String getChinaProvinceDensityVirusByTimestamp(@PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey("chinaProvinceDensity" + timestamp)) {
            return redisCacheManager.get("chinaProvinceDensity" + timestamp).toString();
        }
        List<VirusDensity> virusList = service.getChinaProvinceDensityVirusByTimestamp(timestamp);
        List<Feature> features = new ArrayList<>();
        for (VirusDensity virus : virusList) {
            String code = virus.getLocation();
            try {
                String geojson = Files.lines(Paths.get("src", "main", "resources", "static", "province", code + ".json")).collect(Collectors.joining("\n"));
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(geojson, JsonObject.class);
                JsonArray array = jsonObject.getAsJsonArray("features");
                JsonObject geometry = array.get(0).getAsJsonObject().getAsJsonObject("geometry");
                Feature feature = new Feature(geometry.toString());
                feature.addProperty("active", String.valueOf(virus.getActive()));
                feature.addProperty("recovered", String.valueOf(virus.getRecovered()));
                feature.addProperty("deaths", String.valueOf(virus.getDeaths()));
                feature.addProperty("combined_key", virus.getCombinedKey());
                features.add(feature);
            } catch (IOException e) {
                return "";
            }
        }
        GeoJSON geoJSON = new GeoJSON(features);
        // 存入缓存
        redisCacheManager.set("chinaProvinceDensity" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }

    @GetMapping("/china/density/{province}/{timestamp}")
    public String getChinaCityDensityVirusByTimestamp(@PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
        if (redisCacheManager.hasKey(province + "chinaCityDensity" + timestamp)) {
            return redisCacheManager.get(province + "chinaCityDensity" + timestamp).toString();
        }
        List<VirusDensity> virusList = service.getChinaCityDensityVirusByTimestamp(province, timestamp);
        List<Feature> features = new ArrayList<>();
        if (virusList.size() == 0) {
            virusList = service.getChinaProvinceDensityVirusByTimestamp(province, timestamp);
            for (VirusDensity virus : virusList) {
                String code = virus.getLocation();
                try {
                    String geojson = Files.lines(Paths.get("src", "main", "resources", "static", "province", code + ".json")).collect(Collectors.joining("\n"));
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(geojson, JsonObject.class);
                    JsonArray array = jsonObject.getAsJsonArray("features");
                    JsonObject geometry = array.get(0).getAsJsonObject().getAsJsonObject("geometry");
                    Feature feature = new Feature(geometry.toString());
                    feature.addProperty("active", String.valueOf(virus.getActive()));
                    feature.addProperty("recovered", String.valueOf(virus.getRecovered()));
                    feature.addProperty("deaths", String.valueOf(virus.getDeaths()));
                    feature.addProperty("combined_key", virus.getCombinedKey());
                    features.add(feature);
                } catch (IOException e) {
                    return "";
                }
            }
        } else {
            for (VirusDensity virus : virusList) {
                String code = virus.getLocation();
                try {
                    String geojson = Files.lines(Paths.get("src", "main", "resources", "static", "city", code + ".json")).collect(Collectors.joining("\n"));
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(geojson, JsonObject.class);
                    JsonArray array = jsonObject.getAsJsonArray("features");
                    JsonObject geometry = array.get(0).getAsJsonObject().getAsJsonObject("geometry");
                    Feature feature = new Feature(geometry.toString());
                    feature.addProperty("active", String.valueOf(virus.getActive()));
                    feature.addProperty("recovered", String.valueOf(virus.getRecovered()));
                    feature.addProperty("deaths", String.valueOf(virus.getDeaths()));
                    features.add(feature);
                } catch (IOException e) {
                    return "";
                }
            }
        }
        GeoJSON geoJSON = new GeoJSON(features);
        // 存入缓存
        redisCacheManager.set(province + "chinaCityDensity" + timestamp, geoJSON.toString());
        return geoJSON.toString();
    }
}
