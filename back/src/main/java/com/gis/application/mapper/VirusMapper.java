package com.gis.application.mapper;

import com.gis.application.model.Virus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface VirusMapper {

    // 插入数据
    int insertVirus(Virus virus);

    // 查询经度或纬度为0的
    List<HashMap<String, Object>> selectLatLngEqualZero();

    // 按时间查询
    List<HashMap<String, Object>> getVirusByTime(int publicTime);

    // 得到所有
    List<HashMap<String, Object>> getAllVirus();

    // 得到中国所在区域数据
    List<HashMap<String, Object>> getAllChinaVirus();

    // 按国家分组查询
    List<HashMap<String, Object>> getVirusDivideByCountry();

    // 按日期分组查询
    List<HashMap<String, Integer>> getVirusDivideByTime();

    // 按日期分组查询中国
    List<HashMap<String, Integer>> getChinaVirusDivideByTime();

    // 按省份分组查询
    List<HashMap<String, Object>> getProvinceData();

    // 按国家更新数据
    int updateVirusByCountry(@Param("country") String country, @Param("lng") double lng, @Param("lat") double lat);

    // 按省份更新数据
    int updateVirusByProvince(@Param("province") String province, @Param("lng") double lng, @Param("lat") double lat);

    // 按ID更新数据
    int updateVirusByID(@Param("id") int id, @Param("lng") double lng, @Param("lat") double lat);

    // 更新ID
    int updateID(int id, int ids);

}
