package com.gis.application.controller;

import com.gis.application.model.Constants;
import com.gis.application.model.Feature;
import com.gis.application.model.GeoJSON;
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

    @GetMapping({"/newIncrease", "/newIncrease/{country}", "/newIncrease/{country}/{province}", "/newIncrease/{country}/{province}/{city}"})
    public String getWorldNewIncreaseVirusData(@PathVariable(value = "country", required = false) String country,
                                               @PathVariable(value = "province", required = false) String province,
                                               @PathVariable(value = "city", required = false) String city) {
        Gson gson = new Gson();
        if (country == null || country.isEmpty()) {
            return gson.toJson(service.getWorldNewIncreaseVirusData());
        } else {
            if (province == null || province.isEmpty()) {
                return gson.toJson(service.getCountryNewIncreaseVirusData(country));
            } else {
                if (city == null || city.isEmpty()) {
                    return gson.toJson(service.getProvinceNewIncreaseVirusData(country, province));
                } else {
                    return gson.toJson(service.getCityNewIncreaseVirusData(country, province, city));
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
            return gson.toJson(service.getWorldConfirmedRecoveredVirusData());
        } else {
            if (province == null || province.isEmpty()) {
                return gson.toJson(service.getCountryConfirmedRecoveredVirusData(country));
            } else {
                if (city == null || city.isEmpty()) {
                    return gson.toJson(service.getProvinceConfirmedRecoveredVirusData(country, province));
                } else {
                    return gson.toJson(service.getCityConfirmedRecoveredVirusData(country, province, city));
                }
            }
        }
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

    @GetMapping("/china/confirmed/{timestamp}")
    public String getChinaProvinceConfirmedVirusByTimestamp(@PathVariable("timestamp") int timestamp) {
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
        return geoJSON.toString();
    }

    @GetMapping("/china/confirmed/{province}/{timestamp}")
    public String getChinaCityConfirmedVirusByTimestamp(@PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
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
        return geoJSON.toString();
    }

    @GetMapping("/china/density/{timestamp}")
    public String getChinaProvinceDensityVirusByTimestamp(@PathVariable("timestamp") int timestamp) {
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
        return geoJSON.toString();
    }

    @GetMapping("/china/density/{province}/{timestamp}")
    public String getChinaCityDensityVirusByTimestamp(@PathVariable("province") String province, @PathVariable("timestamp") int timestamp) {
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
        return geoJSON.toString();
    }
}
