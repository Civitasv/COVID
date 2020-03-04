package com.gis.application.mapper;

import com.gis.application.model.Virus;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface VirusMapper {

    int insertVirus(Virus virus);

    List<HashMap<String, Object>> getVirusByTime(int publicTime);

    List<HashMap<String, Object>> getAllVirus();

    int updateData();
}
