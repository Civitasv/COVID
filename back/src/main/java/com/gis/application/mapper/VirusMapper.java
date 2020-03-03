package com.gis.application.mapper;

import com.gis.application.model.Virus;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface VirusMapper {

    int insertHistoryVirus(Virus virus);

    int insertRecentVirus(Virus virus);

    int updateVirusOriginByID(String origin,int id);

    List<HashMap<String, Object>> getAllOrigin() ;

    List<HashMap<String, Object>> getVirusByTime(int publicTime);

    List<HashMap<String, Object>> getAllVirus();

}
