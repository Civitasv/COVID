package com.gis.application.service;

import com.gis.application.model.Virus;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface VirusService {
    // 插入数据
    int insertVirus(Virus virus);

    // 查询经度或纬度为0的
    List<HashMap<String, Object>> selectLatLngEqualZero();

    // 按时间查询
    List<HashMap<String, Object>> getVirusByTime(int publicTime);

    // 按特定时间，对地点模糊查询
    List<HashMap<String, Object>> getVirusByAddressAndTime(int publicTime,String address);

    // 得到所有
    List<HashMap<String, Object>> getAllVirus();

    // 得到中国所有
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
    int updateLngLatByCountry(String country, double lng, double lat);

    // 按省份更新数据
    int updateLngLatByProvince(String province, double lng, double lat);

    // 按ID更新数据
    int updateLngLatByID(int id, double lng, double lat);

    // 更新ID
    int updateID(int id, int ids);

    // 按ID更新疫情数据
    int updateVirus(Virus virus);

    // 按ID删除疫情数据
    int deleteVirus(int id);

    // 按ID添加BASE64图片
    int addImage(int id,String base64);
}
