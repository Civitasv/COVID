package com.gis.application.service;

import com.gis.application.model.Virus;

import java.util.HashMap;
import java.util.List;

public interface VirusService {
    int insertVirus(Virus virus);

    List<HashMap<String, Object>> getVirusByTime(int publicTime);

    List<HashMap<String, Object>> getAllVirus();

    int updateData();
}
