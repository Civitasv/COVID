package com.gis.application.service;

import com.gis.application.model.Virus;

import java.util.HashMap;
import java.util.List;

public interface VirusService {
    int insertHistoryVirus(Virus virus);

    int insertRecentVirus(Virus virus);

    int updateVirusOriginByID(String origin,int id);

    List<HashMap<String, Object>> getAllOrigin() ;

    List<HashMap<String, Object>> getVirusByTime(int publicTime);

    List<HashMap<String, Object>> getAllVirus();

}
